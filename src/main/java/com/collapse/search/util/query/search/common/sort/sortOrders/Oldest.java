package com.collapse.search.util.query.search.common.sort.sortOrders;

import com.collapse.search.config.SearchConfig;
import com.collapse.search.util.query.search.common.sort.AbstractSort;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

public class Oldest extends AbstractSort {

    public Oldest() {
        this.sort = "oldest";
    }
    @Override
    public SearchSourceBuilder buildSort(SearchSourceBuilder searchSourceBuilder) {
        return searchSourceBuilder.sort(SearchConfig.DATE_FIELD, SortOrder.ASC);
    }
}
