package com.collapse.search.vo;


import com.collapse.search.dto.SearchRequestDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class NormalSearchVo {

    private String query;

    private String oQuery;


    private List<String> oldQueries;

    private int currentPage;

    private boolean reSearch;

    public NormalSearchVo(SearchRequestDto searchRequestDto) {
        this.query = searchRequestDto.getQuery();
        this.oQuery = searchRequestDto.getOQuery();
        this.oldQueries = setOldQueries(searchRequestDto.getQuery(),searchRequestDto.getOldQueries(),
                searchRequestDto.isResearch());
        this.currentPage = searchRequestDto.getCurrentPage();
        this.reSearch = searchRequestDto.isResearch();
    }

    private List<String> setOldQueries(String query, List<String> oldQueries, boolean research) {
        if(research) {
            oldQueries.add(query);
        }
        return oldQueries;
    }
}
