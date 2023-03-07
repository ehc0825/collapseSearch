package com.collapse.search.util.query.search.common.sort.sortOrders;

import com.collapse.search.config.IoStudioConfig;
import com.collapse.search.util.query.search.common.sort.AbstractSort;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

public class Short extends AbstractSort {

    public Short() {
        this.sort = "short";
    }

    @Override
    public SearchSourceBuilder buildSort(SearchSourceBuilder searchSourceBuilder) {
        return searchSourceBuilder.sort(IoStudioConfig.SEARCH_AUTOCOMPLETE_LENGTH_FIELD, SortOrder.ASC);
    }
}
