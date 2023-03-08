package com.collapse.search.vo;


import com.collapse.search.config.IoStudioConfig;
import com.collapse.search.config.SearchConfig;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class AutoCompleteVo {

    private String query;

    private String field;

    private int from;

    private int size;

    public AutoCompleteVo(String query) {
        this.query = query;
        this.field = IoStudioConfig.SEARCH_AUTOCOMPLETE_SEARCH_FIELD;
        this.from = 0;
        this.size = 9999;
    }

}
