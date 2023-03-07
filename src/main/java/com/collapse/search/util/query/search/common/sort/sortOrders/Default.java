package com.collapse.search.util.query.search.common.sort.sortOrders;

import com.collapse.search.util.query.search.common.sort.AbstractSort;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class Default extends AbstractSort {

    public Default(){
        this.sort = "default";
    }
    @Override
    public SearchSourceBuilder buildSort(SearchSourceBuilder searchSourceBuilder) {
        return searchSourceBuilder;
    }
}
