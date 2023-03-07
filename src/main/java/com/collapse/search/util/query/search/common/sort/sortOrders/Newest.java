package com.collapse.search.util.query.search.common.sort.sortOrders;

import com.collapse.search.config.SearchConfig;
import com.collapse.search.util.query.search.common.sort.AbstractSort;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

public class Newest extends AbstractSort {

    public Newest() {
        this.sort = "newest";
    }
    @Override
    public SearchSourceBuilder buildSort(SearchSourceBuilder searchSourceBuilder) {
        return searchSourceBuilder.sort(SearchConfig.DATE_FIELD, SortOrder.DESC);
    }
}
