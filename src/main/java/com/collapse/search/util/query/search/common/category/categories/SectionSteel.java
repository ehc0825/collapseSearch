package com.collapse.search.util.query.search.common.category.categories;

import com.collapse.search.util.query.search.common.category.AbstractCategory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class SectionSteel extends AbstractCategory {

    public SectionSteel() {
        this.category = "21";
        this.categoryName = "sectionSteel";
    }

    @Override
    public BoolQueryBuilder buildCategoryQuery(BoolQueryBuilder boolQueryBuilder) {
        return boolQueryBuilder.must(QueryBuilders.termQuery(categoryField,category));
    }
}
