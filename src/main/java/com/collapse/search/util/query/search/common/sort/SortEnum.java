package com.collapse.search.util.query.search.common.sort;

import com.collapse.search.util.query.search.common.sort.sortOrders.*;
import com.collapse.search.util.query.search.common.sort.sortOrders.Short;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.Arrays;

public enum SortEnum {

    NEWEST(new Newest()),
    OLDEST(new Oldest()),
    NONE(new None()),
    SHORT(new Short()),
    DEFAULT(new Default());

    private final AbstractSort abstractSort;

    SortEnum(AbstractSort abstractSort) {
        this.abstractSort = abstractSort;
    }

    public static SearchSourceBuilder buildSortOrderBySort(String sort, SearchSourceBuilder searchSourceBuilder) {
        return find(sort).abstractSort.buildSort(searchSourceBuilder);
    }

    public static SortEnum find(String sort) {
        return Arrays.stream(values())
                .filter(sortEnum -> sortEnum.abstractSort.sort.equals(sort))
                .findAny()
                .orElse(DEFAULT);
    }
}
