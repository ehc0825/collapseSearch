package com.collapse.search.util.query.search.common;

import com.collapse.search.config.SearchConfig;
import com.collapse.search.dto.SearchSourceDto;
import com.collapse.search.vo.NormalSearchVo;
import lombok.experimental.UtilityClass;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

@UtilityClass
public class MainQueryBuilder {

    public SearchSourceDto buildMainQuery(NormalSearchVo normalSearchVo, SearchSourceDto searchSourceDto) {
        if(normalSearchVo.isReSearch()) {
            buildResearchQuery(normalSearchVo, searchSourceDto);
        }
        else {
            buildNormalQuery(normalSearchVo, searchSourceDto);
        }
        return searchSourceDto;
    }

    private static void buildNormalQuery(NormalSearchVo normalSearchVo, SearchSourceDto searchSourceDto) {
        searchSourceDto.setBoolQueryBuilder(buildSearchQuery(normalSearchVo.getQuery(),
                searchSourceDto.getBoolQueryBuilder()));
    }

    private static BoolQueryBuilder buildSearchQuery(String query, BoolQueryBuilder boolQueryBuilder) {
        boolQueryBuilder.must(QueryBuilders.multiMatchQuery(query, SearchConfig.DEFAULT_SEARCH_FIELD));
        return boolQueryBuilder;
    }

    private static void buildResearchQuery(NormalSearchVo normalSearchVo, SearchSourceDto searchSourceDto) {
        BoolQueryBuilder boolQueryBuilder = searchSourceDto.getBoolQueryBuilder();
        for(String query : normalSearchVo.getOldQueries()) {
            boolQueryBuilder = buildSearchQuery(query,boolQueryBuilder);
        }
        searchSourceDto.setBoolQueryBuilder(boolQueryBuilder);
    }

}
