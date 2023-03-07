package com.collapse.search.service;

import com.collapse.search.dto.SearchRequestDto;
import com.collapse.search.dto.SearchResponseDto;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface SearchService {
    SearchResponseDto getAllCategorySearchResponse(SearchRequestDto searchRequestDto) throws IOException;

    void insertSearchLog(SearchRequestDto searchRequestDto, SearchResponseDto searchResponseDto, HttpServletRequest httpServletRequest);
}
