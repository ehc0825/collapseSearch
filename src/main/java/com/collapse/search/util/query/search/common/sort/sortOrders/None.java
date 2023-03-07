package com.collapse.search.util.query.search.common.sort.sortOrders;

import com.collapse.search.util.query.search.common.sort.AbstractSort;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class None extends AbstractSort {

    public None() {
        this.sort = "none";
    }
    @Override
    public SearchSourceBuilder buildSort(SearchSourceBuilder searchSourceBuilder) {
        return searchSourceBuilder;
    }
}
