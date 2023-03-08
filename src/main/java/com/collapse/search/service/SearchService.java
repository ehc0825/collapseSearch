package com.collapse.search.service;

import com.collapse.search.dto.SearchRequestDto;
import com.collapse.search.dto.SearchResponseDto;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SearchService {
    SearchResponseDto getAllCategorySearchResponse(SearchRequestDto searchRequestDto) throws IOException;

    void insertSearchLog(SearchRequestDto searchRequestDto, SearchResponseDto searchResponseDto, HttpServletRequest httpServletRequest) throws IOException;

    List<Map<String, Object>> getAutoComplete(SearchRequestDto searchRequestDto) throws IOException;

    List<Map<String, Object>> getSearchHistory(SearchRequestDto searchRequestDto, HttpServletRequest httpServletRequest) throws IOException;
}
