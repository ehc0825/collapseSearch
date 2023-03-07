package com.collapse.search.util.query.search.common.category;

import com.collapse.search.util.query.search.common.category.categories.All;
import com.collapse.search.util.query.search.common.category.categories.Rebar;
import com.collapse.search.util.query.search.common.category.categories.SectionSteel;
import com.collapse.search.util.query.search.common.category.categories.SteelPipe;
import org.elasticsearch.index.query.BoolQueryBuilder;

import java.util.Arrays;

public enum CategoryEnum {

    REBAR(new Rebar()),
    SECTION_STEEL(new SectionSteel()),
    STEEL_PIPE(new SteelPipe()),
    ALL(new All()),
    OTHER(new Rebar());



    public final AbstractCategory abstractCategory;

    CategoryEnum(AbstractCategory abstractCategory) {
        this.abstractCategory = abstractCategory;
    }

    public static CategoryEnum find(String category) {
        return Arrays.stream(values())
                .filter(categoryEnum -> categoryEnum.abstractCategory.category.equals(category))
                .findAny()
                .orElse(OTHER);
    }

    public static String findCategoryNameByCategory(String category) {
        return find(category).abstractCategory.categoryName;
    }

    public static BoolQueryBuilder buildCategoryQueryByCategory(String category, BoolQueryBuilder boolQueryBuilder) {
        return find(category).abstractCategory.buildCategoryQuery(boolQueryBuilder);
    }
}
