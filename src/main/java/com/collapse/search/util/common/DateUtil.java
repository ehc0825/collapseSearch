package com.collapse.search.util.common;


import com.collapse.search.config.IoStudioConfig;
import com.collapse.search.config.SearchConfig;
import com.collapse.search.util.common.parse.ParseSearchResultUtil;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateUtil {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(IoStudioConfig.SEARCH_LOG_DATE_FORMAT);

    public String getNowDate() {
        return LocalDateTime.now().format(formatter);
    }

    public String getWeekPost() {
        return LocalDateTime.now().format(formatter);
    }
}
