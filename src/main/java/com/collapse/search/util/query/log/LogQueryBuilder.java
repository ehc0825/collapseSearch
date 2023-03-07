package com.collapse.search.util.query.log;

import com.collapse.search.dto.SearchRequestDto;
import com.collapse.search.dto.SearchResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.elasticsearch.action.index.IndexRequest;


import javax.servlet.http.HttpServletRequest;

@UtilityClass
@SuppressWarnings("unchecked")
public class LogQueryBuilder {

    public IndexRequest buildIndexRequest(SearchRequestDto searchRequestDto, HttpServletRequest httpServletRequest,
                                          SearchResponseDto searchResponseDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();


    }
}
