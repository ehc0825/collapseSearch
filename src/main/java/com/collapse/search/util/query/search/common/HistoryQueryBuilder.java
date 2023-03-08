package com.collapse.search.util.query.search.common;

import com.collapse.search.config.IoStudioConfig;
import com.collapse.search.config.SearchConfig;
import com.collapse.search.dto.SearchSourceDto;
import com.collapse.search.util.common.DateUtil;
import com.collapse.search.util.query.search.common.sort.SortOrderBuilder;
import com.collapse.search.vo.HistoryVo;
import lombok.experimental.UtilityClass;

import javax.swing.*;

@UtilityClass
public class HistoryQueryBuilder {

    public SearchSourceDto buildHistoryQuery(HistoryVo historyVo,SearchSourceDto searchSourceDto) {
        searchSourceDto = TermQueryBuilder.buildTermQuery(IoStudioConfig.SEARCH_HISTORY_USER_FIELD,historyVo.getUser()
                ,searchSourceDto);
        searchSourceDto = RangeQueryBuilder.buildDateRangeQuery(SearchConfig.DATE_FIELD, DateUtil.getWeekPost(),DateUtil.getNowDate(),
                IoStudioConfig.SEARCH_LOG_DATE_FORMAT,searchSourceDto);
        searchSourceDto = SizeQueryBuilder.buildCustomSizeQuery(searchSourceDto,historyVo.getSize());
        searchSourceDto = SortOrderBuilder.buildSortOrder(searchSourceDto,"newest");
        return searchSourceDto;
    }
}
