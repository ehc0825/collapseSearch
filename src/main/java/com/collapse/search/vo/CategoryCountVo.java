package com.collapse.search.vo;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class CategoryCountVo {

    private String category;
    private String categoryCount;

    public CategoryCountVo(String category, String categoryCount) {
        this.category = category;
        this.categoryCount = categoryCount;
    }
}
