package com.collapse.search.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class CollapseVo {

    private String[] collapsedFieldValues;

    public CollapseVo(String[] collapsedFieldValues) {
        this.collapsedFieldValues = collapsedFieldValues;
    }
}
