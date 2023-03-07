package com.collapse.search.util.query.search.common;

import com.collapse.search.dto.SearchSourceDto;
import com.collapse.search.vo.NormalSearchVo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class NormalQueryBuilder {


    public SearchSourceDto buildNormalQuery(NormalSearchVo normalSearchVo, SearchSourceDto searchSourceDto) {
        searchSourceDto = MainQueryBuilder.buildMainQuery(normalSearchVo,searchSourceDto);
        return searchSourceDto;
    }
}
