package com.collapse.search.vo;


import com.collapse.search.config.IoStudioConfig;
import com.collapse.search.config.SearchConfig;
import com.collapse.search.dto.SearchRequestDto;
import com.collapse.search.util.common.HttpServletRequestUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class HistoryVo {

    private String user;

    private int size;

    public HistoryVo(SearchRequestDto searchRequestDto, HttpServletRequest httpServletRequest) {
        this.user = getUserName(searchRequestDto,httpServletRequest);
        this.size = SearchConfig.SEARCH_HISTORY_SIZE;
    }

    private String getUserName(SearchRequestDto searchRequestDto, HttpServletRequest httpServletRequest) {
        if(StringUtils.isEmpty(searchRequestDto.getUserName()))
        {
            return HttpServletRequestUtil.getIp(httpServletRequest);
        }
        else {
            return searchRequestDto.getUserName();
        }
    }
}
