package com.collapse.search.service;

import com.collapse.search.config.IoStudioConfig;
import com.collapse.search.dao.SearchDao;
import com.collapse.search.dto.SearchRequestDto;
import com.collapse.search.dto.SearchResponseDto;
import com.collapse.search.util.common.parse.ParseSearchResultUtil;
import com.collapse.search.util.query.MultiSearchBuilder;
import com.collapse.search.util.query.log.LogQueryBuilder;
import com.collapse.search.util.query.search.SourceBuilder;
import com.collapse.search.util.query.search.common.category.CategoryEnum;
import com.collapse.search.vo.CollapseVo;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@Service
public class SearchServiceImpl implements SearchService{

    @Autowired
    SearchDao searchDao;

    @Override
    public SearchResponseDto getAllCategorySearchResponse(SearchRequestDto searchRequestDto) throws IOException {
        SearchResponseDto searchResponseDto = getSearchResponse(searchRequestDto);
        if(isType(searchResponseDto)) {
            searchResponseDto = getSearchResponseForTypeCorrect(searchRequestDto);
        }
        if(isCurrentCategoryResultNotZeroAndCollapse(searchRequestDto, searchResponseDto)) {
            searchResponseDto = setStateValueToResults(searchResponseDto,
                    new CollapseVo(ParseSearchResultUtil.getCollapsedFields(searchResponseDto.getResults())));
        }
        return searchResponseDto;
    }

    @Override
    public void insertSearchLog(SearchRequestDto searchRequestDto, SearchResponseDto searchResponseDto,
                                HttpServletRequest httpServletRequest) throws IOException {
        if(isWorth(searchRequestDto)) {
            IndexRequest indexRequest = LogQueryBuilder.buildIndexRequest(searchRequestDto,httpServletRequest,searchResponseDto);
            searchDao.insertSearchLog(indexRequest);
        }
    }

    @Override
    public List<Map<String, Object>> getAutoComplete(SearchRequestDto searchRequestDto) throws IOException {
        SearchRequest autoCompleteRequest = new SearchRequest(IoStudioConfig.SEARCH_AUTOCOMPLETE_INDEX);
        autoCompleteRequest.source(SourceBuilder.buildAutocompleteSource(searchRequestDto.getQuery()));
        SearchResponse searchResponse = searchDao.getSearchResponse(autoCompleteRequest);

        return null;
    }

    @Override
    public List<Map<String, Object>> getSearchHistory(SearchRequestDto searchRequestDto, HttpServletRequest httpServletRequest)
            throws IOException {
        SearchRequest historyRequest = new SearchRequest(IoStudioConfig.SEARCH_LOG_INDEX);
        historyRequest.source(SourceBuilder.buildHistorySource(searchRequestDto,httpServletRequest));
        SearchResponse searchResponse = searchDao.getSearchResponse(historyRequest);
        List<Map<String, Object>> results = ParseSearchResultUtil.getHistoryResults(searchResponse);
        return results;
    }

    private boolean isWorth(SearchRequestDto searchRequestDto) {
        return isFirstPage(searchRequestDto) && isQueryNotEmpty(searchRequestDto);
    }

    private boolean isQueryNotEmpty(SearchRequestDto searchRequestDto) {
        return !searchRequestDto.getQuery().isEmpty() && isQueryNotOnlySpace(searchRequestDto.getQuery());
    }

    private boolean isQueryNotOnlySpace(String query) {
        return !query.trim().isEmpty();
    }

    private boolean isFirstPage(SearchRequestDto searchRequestDto) {
        return searchRequestDto.getCurrentPage() == 1;
    }

    private SearchResponseDto setStateValueToResults(SearchResponseDto searchResponseDto,CollapseVo collapseVo) throws IOException {
        MultiSearchRequest multiSearchRequest = MultiSearchBuilder.buildAllStateRequest(collapseVo);
        MultiSearchResponse multiSearchResponse = searchDao.getMultiSearchResponse(multiSearchRequest);
        searchResponseDto = ParseSearchResultUtil.setStateValue(searchResponseDto,multiSearchResponse);
        return searchResponseDto;
    }

    private boolean isCurrentCategoryResultNotZeroAndCollapse(SearchRequestDto searchRequestDto, SearchResponseDto searchResponseDto) {
        return !searchResponseDto.getCategoryCountMap().get(CategoryEnum.findCategoryNameByCategory(searchRequestDto.getCategory()))
                .getCategoryCount().equals("0") && searchRequestDto.isCollapse();
    }

    private SearchResponseDto getSearchResponseForTypeCorrect(SearchRequestDto searchRequestDto) throws IOException {
        String replacedQuery = searchRequestDto.getQuery();
        searchRequestDto.setQuery(getSuggestText(searchRequestDto));
        SearchResponseDto searchResponseDto = getSearchResponse(searchRequestDto);
        searchResponseDto.setReplacedQuery(replacedQuery);
        return searchResponseDto;
    }

    private String getSuggestText(SearchRequestDto searchRequestDto) throws IOException {
        SearchRequest searchRequest = new SearchRequest(IoStudioConfig.TYPO_CORRECT_INDEX);
        searchRequest.source(SourceBuilder.buildTypoCorrectSource(searchRequestDto.getQuery()));
        SearchResponse searchResponse = searchDao.getSearchResponse(searchRequest);
        return ParseSearchResultUtil.getTypoCorrectQuery(searchResponse);
    }

    private boolean isType(SearchResponseDto searchResponseDto) {
        return searchResponseDto.getTotal() == 0 && null != searchResponseDto.getQuery();
    }


    private SearchResponseDto getSearchResponse(SearchRequestDto searchRequestDto) throws IOException {
        MultiSearchRequest multiSearchRequest = MultiSearchBuilder.buildAllCategoryQuery(searchRequestDto);
        MultiSearchResponse multiSearchResponse = searchDao.getMultiSearchResponse(multiSearchRequest);
        SearchResponseDto searchResponseDto = ParseSearchResultUtil.getMultiSearchResults(multiSearchResponse, searchRequestDto);
        return searchResponseDto;
    }
}
