package com.collapse.search.util.query.search;

import com.collapse.search.config.SearchConfig;
import com.collapse.search.dto.SearchRequestDto;
import com.collapse.search.dto.SearchSourceDto;
import com.collapse.search.util.query.aggregation.CollapseCountAggBuilder;
import com.collapse.search.util.query.aggregation.StatsAggBuilder;
import com.collapse.search.util.query.search.common.CommonQueryBuilder;
import com.collapse.search.util.query.search.common.NormalQueryBuilder;
import com.collapse.search.util.query.search.common.SizeQueryBuilder;
import com.collapse.search.util.query.search.common.TermQueryBuilder;
import com.collapse.search.util.query.search.common.category.CategoryQueryBuilder;
import com.collapse.search.util.query.search.typoCorrect.SuggestQueryBuilder;
import com.collapse.search.vo.CategoryVo;
import com.collapse.search.vo.CollapseVo;
import com.collapse.search.vo.NormalSearchVo;
import lombok.experimental.UtilityClass;
import org.elasticsearch.search.builder.SearchSourceBuilder;

@UtilityClass
public class SourceBuilder {
    public static SearchSourceBuilder buildSearchSourceByCategory(SearchRequestDto searchRequestDto, String searchCategory) {
        SearchSourceDto searchSourceDto = createSearchSource(SearchConfig.DEFAULT_SEARCH_FIELD);
        CategoryVo categoryVo = new CategoryVo(searchCategory);
        searchSourceDto = CategoryQueryBuilder.buildCategoryQuery(searchSourceDto,categoryVo);
        searchSourceDto = buildSearchSource(searchRequestDto,searchSourceDto);
        return searchSourceDto.getSearchSourceBuilder();
    }

    private static SearchSourceDto buildSearchSource(SearchRequestDto searchRequestDto, SearchSourceDto searchSourceDto) {
        searchSourceDto = buildNormalSearchQuery(searchRequestDto,searchSourceDto);
        searchSourceDto = buildCommonQuery(searchSourceDto);
        searchSourceDto = SizeQueryBuilder.buildSizeQuery(searchSourceDto, searchRequestDto);
        if(searchRequestDto.isCollapse()) {
            searchSourceDto = CollapseCountAggBuilder.buildCollapseCountAgg(searchSourceDto);
        }
        searchSourceDto = setBoolQueryToSearchSourceBuilder(searchSourceDto);
        return searchSourceDto;
    }

    private static SearchSourceDto setBoolQueryToSearchSourceBuilder(SearchSourceDto searchSourceDto) {
        searchSourceDto.setSearchSourceBuilder(searchSourceDto.getSearchSourceBuilder()
                .query(searchSourceDto.getBoolQueryBuilder()));
        return searchSourceDto;
    }

    private static SearchSourceDto buildCommonQuery(SearchSourceDto searchSourceDto) {
        searchSourceDto = CommonQueryBuilder.buildCommonQuery(searchSourceDto);
        return searchSourceDto;

    }

    private static SearchSourceDto buildNormalSearchQuery(SearchRequestDto searchRequestDto, SearchSourceDto searchSourceDto) {
        NormalSearchVo normalSearchVo = new NormalSearchVo(searchRequestDto);
        searchSourceDto = NormalQueryBuilder.buildNormalQuery(normalSearchVo,searchSourceDto);
        return searchSourceDto;
    }


    private static SearchSourceDto createSearchSource(String[] highlightFields) {
        SearchSourceDto searchSourceDto = new SearchSourceDto();
        searchSourceDto.setHighlightFields(highlightFields);
        return searchSourceDto;
    }

    public static SearchSourceBuilder buildTypoCorrectSource(String query) {
        SearchSourceDto searchSourceDto = new SearchSourceDto();
        searchSourceDto = SuggestQueryBuilder.buildSuggestQuery(query,searchSourceDto);
        return searchSourceDto.getSearchSourceBuilder();
    }

    public static SearchSourceBuilder buildSearchSourceForStatsAgg(String collapsedFieldValue, CollapseVo collapseVo) {
        String[] statsAggFields = SearchConfig.STATS_AGGREGATION_FIELDS;
        SearchSourceDto searchSourceDto = new SearchSourceDto();
        searchSourceDto = buildTermQueryForStatsAgg(collapsedFieldValue,collapseVo,searchSourceDto);
        searchSourceDto = SizeQueryBuilder.buildZeroSizeQuery(searchSourceDto);
        for(String statsAggField : statsAggFields) {
            searchSourceDto = StatsAggBuilder.buildStatsAgg(searchSourceDto,statsAggField);
        }
        searchSourceDto = setBoolQueryToSearchSourceBuilder(searchSourceDto);
        return searchSourceDto.getSearchSourceBuilder();
    }

    private static SearchSourceDto buildTermQueryForStatsAgg(String collapsedFieldValue, CollapseVo collapseVo, SearchSourceDto searchSourceDto) {
        searchSourceDto = TermQueryBuilder
                .buildTermQuery(SearchConfig.FIELD_FOR_COLLAPSE,collapsedFieldValue,searchSourceDto);
        return searchSourceDto;
    }
}
