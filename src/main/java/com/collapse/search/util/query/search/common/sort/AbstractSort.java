package com.collapse.search.util.query.search.common.sort;

import org.elasticsearch.search.builder.SearchSourceBuilder;

public abstract class AbstractSort {

    protected String sort;

    public abstract SearchSourceBuilder buildSort(SearchSourceBuilder searchSourceBuilder);
}
