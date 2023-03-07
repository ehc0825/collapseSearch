package com.collapse.search.controller;

import com.collapse.search.dto.SearchRequestDto;
import com.collapse.search.dto.SearchResponseDto;
import com.collapse.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchService searchService;


    @GetMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SearchResponseDto search(@RequestBody SearchRequestDto searchRequestDto, HttpServletRequest httpServletRequest)
            throws IOException {
        SearchResponseDto searchResponseDto = searchService.getAllCategorySearchResponse(searchRequestDto);
        searchService.insertSearchLog(searchRequestDto,searchResponseDto,httpServletRequest);
        return searchResponseDto;
    }

}
