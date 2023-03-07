package com.collapse.search.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class CategoryVo {
    private String category;
    public CategoryVo (String category) {
        this.category = category;
    }
}
