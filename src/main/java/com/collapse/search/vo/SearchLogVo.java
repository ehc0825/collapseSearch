package com.collapse.search.vo;


import com.collapse.search.config.IoStudioConfig;
import com.collapse.search.dto.SearchRequestDto;
import com.collapse.search.dto.SearchResponseDto;
import com.collapse.search.util.common.DateUtil;
import com.collapse.search.util.common.HttpServletRequestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class SearchLogVo {

    private String query;

    private String oquery;

    private String domain;

    private String category;

    private String param;

    private String ip;

    private String referer;

    private boolean re;

    private String sort;

    private boolean asc;

    private String user;

    private long took;

    private long total;

    private String createDate;

    public SearchLogVo(SearchRequestDto searchRequestDto, HttpServletRequest httpServletRequest, SearchResponseDto searchResponseDto)
    throws JsonProcessingException {
        this.query = searchRequestDto.getQuery();
        this.oquery = searchRequestDto.getOQuery();
        this.domain = IoStudioConfig.SEARCH_DOMAIN;
        this.category = searchRequestDto.getCategory();
        this.param = HttpServletRequestUtil.getParam(httpServletRequest);
        this.ip = HttpServletRequestUtil.getIp(httpServletRequest);
        this.referer = HttpServletRequestUtil.getReferer(httpServletRequest);
        this.re = searchRequestDto.isResearch();
        this.sort = "default";
        this.asc = false;
        this.user = searchRequestDto.getUserName();
        this.took = searchResponseDto.getTook();
        this.total = searchResponseDto.getTotal();
        this.createDate = DateUtil.getNowDate();

    }

}
