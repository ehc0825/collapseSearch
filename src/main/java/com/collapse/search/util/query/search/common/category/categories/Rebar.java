package com.collapse.search.util.query.search.common.category.categories;

import com.collapse.search.util.query.search.common.category.AbstractCategory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class Rebar extends AbstractCategory {

    public Rebar() {
        this.category = "20";
        this.categoryName = "rebar";
    }
    @Override
    public BoolQueryBuilder buildCategoryQuery(BoolQueryBuilder boolQueryBuilder) {
        return boolQueryBuilder.must(QueryBuilders.termQuery(categoryField,category));
    }
}
