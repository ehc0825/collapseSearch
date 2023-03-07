package com.collapse.search.dto;


import com.collapse.search.vo.CategoryCountVo;
import com.collapse.search.vo.PageVo;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Data
public class SearchResponseDto {

    private String query;
    private String replacedQuery;


    private int total;
    private int took;

    private Map<String, CategoryCountVo> categoryCountMap;

    private PageVo pageVo;

    private String category;

    private List<Map<String,Object>> results;


}
