package com.collapse.search.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchRequestDto {

    private String query;

    private String oQuery;

    private List<String> oldQueries;

    private String category;

    private String userName;

    private boolean research;

    private boolean collapse;

    private int currentPage;
    private int searchSize;
}
