package com.collapse.search.util.query.search.common.category.categories;

import com.collapse.search.util.query.search.common.category.AbstractCategory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class SteelPipe extends AbstractCategory {

    public SteelPipe() {
        this.category = "31";
        this.categoryName = "steelPipe";
    }
    @Override
    public BoolQueryBuilder buildCategoryQuery(BoolQueryBuilder boolQueryBuilder) {
        return boolQueryBuilder.must(QueryBuilders.termQuery(categoryField,category));
    }
}
