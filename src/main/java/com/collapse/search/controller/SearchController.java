package com.collapse.search.controller;

import com.collapse.search.dto.SearchRequestDto;
import com.collapse.search.dto.SearchResponseDto;
import com.collapse.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    @GetMapping(value = "/auto", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String,Object>> autocomplete(@RequestBody SearchRequestDto searchRequestDto) throws IOException {
        List<Map<String,Object>> results = searchService.getAutoComplete(searchRequestDto);
        return results;
    }

}
