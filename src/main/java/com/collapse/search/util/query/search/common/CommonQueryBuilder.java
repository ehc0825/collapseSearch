package com.collapse.search.util.query.search.common;

import com.collapse.search.dto.SearchSourceDto;
import com.collapse.search.util.query.search.common.sort.SortOrderBuilder;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonQueryBuilder {

    public SearchSourceDto buildCommonQuery(SearchSourceDto searchSourceDto) {
        searchSourceDto = SortOrderBuilder.buildSortOrder(searchSourceDto,"default");
        searchSourceDto = HighlightQueryBuilder.buildQuery(searchSourceDto);
        return searchSourceDto;
    }
}
