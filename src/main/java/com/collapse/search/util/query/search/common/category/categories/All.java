package com.collapse.search.util.query.search.common.category.categories;

import com.collapse.search.util.query.search.common.category.AbstractCategory;
import org.elasticsearch.index.query.BoolQueryBuilder;

public class All extends AbstractCategory {

    public All() {
        this.category = "all";
        this.categoryName = "all";
    }
    @Override
    public BoolQueryBuilder buildCategoryQuery(BoolQueryBuilder boolQueryBuilder) {
        return boolQueryBuilder;
    }
}
