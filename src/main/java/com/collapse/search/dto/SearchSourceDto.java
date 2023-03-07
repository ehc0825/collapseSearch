package com.collapse.search.dto;

import lombok.Data;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

@Data
public class SearchSourceDto {

    private String[] highlightFields;

    private BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

    private SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
}
