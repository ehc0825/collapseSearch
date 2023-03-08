package com.collapse.search.util.common.parse;

import com.collapse.search.config.IoStudioConfig;
import com.collapse.search.config.SearchConfig;
import com.collapse.search.dto.SearchRequestDto;
import com.collapse.search.dto.SearchResponseDto;
import com.collapse.search.util.query.search.common.category.CategoryEnum;
import com.collapse.search.vo.CategoryCountVo;
import com.collapse.search.vo.PageVo;
import lombok.experimental.UtilityClass;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.metrics.ScriptedMetric;
import org.elasticsearch.search.aggregations.metrics.Stats;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.term.TermSuggestion;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class ParseSearchResultUtil {

    public long getTotal(SearchResponse searchResponse, boolean collapse) {
        int totalCount = 0;
        if(collapse) {
            ScriptedMetric scriptedMetric = searchResponse.getAggregations().get(SearchConfig.COLLAPSE_COUNT_AGG_NAME);
            totalCount = (int) scriptedMetric.aggregation();
        }
        else {
            totalCount = (int) searchResponse.getHits().getTotalHits().value;
        }
        return  totalCount;
    }

    public long getTook(SearchResponse searchResponse) {
        TimeValue tookTimeValue = searchResponse.getTook();
        return tookTimeValue.getNanos();
    }


    public List<Map<String,Object>> getResults(SearchResponse searchResponse, String[] fields, boolean highlight) {
        List<Map<String,Object>> results = mappingSearchResult(searchResponse,fields,highlight);
        return results;
    }

    public String[] getCollapsedFields(List<Map<String,Object>> results) {
        String[] collapsedFieldValue = new String[results.size()];
        int counter = 0;
        for(Map<String,Object> result : results) {
            collapsedFieldValue[counter] = (String) result.get(SearchConfig.FIELD_FOR_COLLAPSE);
            counter ++;
        }
        return collapsedFieldValue;
    }

    public SearchResponseDto getMultiSearchResults(MultiSearchResponse multiSearchResponse, SearchRequestDto searchRequestDto) {
        SearchResponseDto searchResponseDto = new SearchResponseDto();
        searchResponseDto.setQuery(searchRequestDto.getQuery());
        String[] searchCategories = SearchConfig.DEFAULT_SEARCH_CATEGORIES;
        Map<String, CategoryCountVo> countMap = new HashMap<>();
        long took = 0;
        long total = 0;
        for(int i =0; i< searchCategories.length; i++) {
            SearchResponse searchResponse;
            if(IsCurrentCategory(searchCategories[i],searchRequestDto.getCategory())) {
                searchResponse = getSearchMultiSearchResponse(multiSearchResponse,i);
                setSearchResponseDto(searchResponseDto,searchRequestDto,searchResponse,searchCategories[i]);
            }
            else {
                searchResponse = getSearchMultiSearchResponse(multiSearchResponse, i);
            }
            CategoryCountVo categoryCountVo = new CategoryCountVo(searchCategories[i], String.valueOf(getTotal(searchResponse,
                    searchRequestDto.isCollapse())) );
            countMap.put(CategoryEnum.findCategoryNameByCategory(searchCategories[i]),categoryCountVo);
            total += getTotal(searchResponse, searchRequestDto.isCollapse());
            took += getTook(searchResponse);
        }
        searchResponseDto.setCategoryCountMap(countMap);
        searchResponseDto.setTotal((int)total);
        searchResponseDto.setTook((int)took);
        return searchResponseDto;
    }

    private static void setSearchResponseDto(SearchResponseDto searchResponseDto, SearchRequestDto searchRequestDto,
                                             SearchResponse searchResponse, String searchCategory) {
        searchResponseDto.setResults(mappingSearchResult(searchResponse,SearchConfig.DEFAULT_SEARCH_FIELD,true));
        searchResponseDto.setCategory(searchCategory);
        PageVo pageVo = new PageVo((int) getTotal(searchResponse, searchRequestDto.isCollapse()), searchRequestDto.getCurrentPage(),
                searchRequestDto.getSearchSize());
        searchResponseDto.setPageVo(pageVo);
    }

    private static SearchResponse getSearchMultiSearchResponse(MultiSearchResponse multiSearchResponse, int i) {
        MultiSearchResponse.Item tempResponse = multiSearchResponse.getResponses()[i];
        SearchResponse searchResponse = tempResponse.getResponse();
        return searchResponse;
    }

    private static boolean IsCurrentCategory(String category, String currentCategory) {
        return category.equals(currentCategory);
    }


    private static List<Map<String, Object>> mappingSearchResult(SearchResponse searchResponse, String[] fields, boolean highlight) {
        List<Map<String,Object>> resultList = new ArrayList<>();
        for(SearchHit hit : searchResponse.getHits()) {
            Map<String,Object> resultMap = getResultMap(hit);
            if(highlight) {
                resultMap = mappingHighlightToResult(fields,hit.getHighlightFields(),resultMap);
            }
            resultList.add(resultMap);
        }
        return resultList;
    }

    private static Map<String, Object> mappingHighlightToResult(String[] fields, Map<String, HighlightField> highlightFields,
                                                                Map<String, Object> resultMap) {
        for(String field : fields) {
            HighlightField highlightField = highlightFields.get(field);
            if(null != highlightField) {
                Text[] fragments = highlightField.getFragments();
                String fragment = fragments[0].string();
                field = field.replace(".","_");
                resultMap.put(field+SearchConfig.HIGHLIGHT_FIELD_TAIL, fragment);
            }
        }
        return resultMap;
    }

    private static Map<String, Object> getResultMap(SearchHit hit) {
        Map<String,Object> resultMap = hit.getSourceAsMap();
        return resultMap;
    }


    public static String getTypoCorrectQuery(SearchResponse searchResponse) {
        String suggestText = "";
        Suggest suggest = searchResponse.getSuggest();
        TermSuggestion termSuggestion = suggest.getSuggestion(IoStudioConfig.SUGGEST_NAME);
        for(TermSuggestion.Entry entry : termSuggestion.getEntries()) {
            int counter = 0;
            for(TermSuggestion.Entry.Option option : entry) {
                if(counter == 0) {
                    suggestText = option.getText().string();
                }
                counter++;
            }
        }
        return Normalizer.normalize(suggestText,Normalizer.Form.NFC);
    }

    public static SearchResponseDto setStateValue(SearchResponseDto searchResponseDto, MultiSearchResponse multiSearchResponse) {
        List<Map<String,Object>> results = searchResponseDto.getResults();
        String[] statsAggFields = SearchConfig.STATS_AGGREGATION_FIELDS;
        int counter = 0;
        for(Map<String,Object> result : results) {
            SearchResponse searchResponse = getSearchMultiSearchResponse(multiSearchResponse,counter);
            counter ++;
            for(String field : statsAggFields) {
                Stats agg = searchResponse.getAggregations().get(field);
                result.put(field+"_min",agg.getMin());
                result.put(field+"_max",agg.getMax());
                result.put(field+"_avg",agg.getAvg());
                result.put(field+"_sum",agg.getSum());
            }
        }
        searchResponseDto.setResults(results);
        return searchResponseDto;
    }

    public static List<Map<String, Object>> getHistoryResults(SearchResponse searchResponse) {
        List<Map<String,Object>> results = getResults(searchResponse,new String[]{IoStudioConfig.SEARCH_HISTORY_KEYWORD_FIELD},false);
        return results;
    }
}
