package com.collapse.search.util.query.search.common.sort;

import com.collapse.search.dto.SearchSourceDto;
import lombok.experimental.UtilityClass;
import org.elasticsearch.search.builder.SearchSourceBuilder;

@UtilityClass
public class SortOrderBuilder {

    public SearchSourceDto buildSortOrder(SearchSourceDto searchSourceDto, String sort) {
        SearchSourceBuilder searchSourceBuilder =
                SortEnum.buildSortOrderBySort(sort,searchSourceDto.getSearchSourceBuilder());
        searchSourceDto.setSearchSourceBuilder(searchSourceBuilder);
        return searchSourceDto;
    }

}
