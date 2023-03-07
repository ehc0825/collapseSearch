package com.collapse.search.util.query.search.common;

import com.collapse.search.dto.SearchSourceDto;
import lombok.experimental.UtilityClass;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.util.StringUtils;

@UtilityClass
public class TermQueryBuilder {

    public SearchSourceDto buildTermQuery(String field, String query, SearchSourceDto searchSourceDto) {
        if(isQueryNotEmpty(query)) {
            searchSourceDto.setBoolQueryBuilder(searchSourceDto.getBoolQueryBuilder()
                    .must(QueryBuilders.termQuery(field,query)));
        }
        return searchSourceDto;
    }

    private static boolean isQueryNotEmpty(String query) {
        return !StringUtils.isEmpty(query);
    }
}
