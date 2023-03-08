package com.collapse.search.util.query.log;

import com.collapse.search.config.IoStudioConfig;
import com.collapse.search.dto.SearchRequestDto;
import com.collapse.search.dto.SearchResponseDto;
import com.collapse.search.vo.SearchLogVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.elasticsearch.action.index.IndexRequest;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@UtilityClass
@SuppressWarnings("unchecked")
public class LogQueryBuilder {

    public IndexRequest buildIndexRequest(SearchRequestDto searchRequestDto, HttpServletRequest httpServletRequest,
                                          SearchResponseDto searchResponseDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SearchLogVo searchLogVo = new SearchLogVo(searchRequestDto,httpServletRequest,searchResponseDto);
        Map<String,Object> logMap = objectMapper.convertValue(searchLogVo,Map.class);
        IndexRequest indexRequest = new IndexRequest("post").source(logMap).index(IoStudioConfig.SEARCH_LOG_INDEX);
        return indexRequest;
    }
}
