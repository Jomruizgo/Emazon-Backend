package com.emazon.msvc_stock.domain.util;

import java.util.Map;

public final class TestConstants {
    private TestConstants() {
        throw new IllegalStateException("Utility class");
    }


    //Controllers Path
    public static final String API_ARTICLE_PATH = "api/article";
    public static final String API_CATEGORY_PATH = "api/category";
    public static final String API_BRAND_PATH = "api/brand";
    public static final String ARTICLE_SUPPY_SEMIPATH = "/supply";


    public static final int MAX_CATEGORY_NAME_LENGTH=50;
    public static final int MAX_CATEGORY_DESCRIPTION_LENGTH=90;
    public static final String DEFAULT_CATEGORY_NAME_ORDER="asc";

    public static final int MAX_BRAND_NAME_LENGTH=50;
    public static final int MAX_BRAND_DESCRIPTION_LENGTH=120;
    public static final String DEFAULT_BRAND_NAME_ORDER="asc";


    public static final int MAX_CATEGORY_ASSOCIATED_TO_ARTICLE = 3;


    public static final String DEFAULT_ARTICLE_SORT_BY = "name";
    public static final String DEFAULT_ARTICLE_SORTING_ORDER = "asc";
    public static final String ARTICLE_SORT_BY_CATEGORY_NAME = "c.name";
    public static final Map<String, String> SORT_ARTICLE_FIELDS = Map.of(
            "name", "name",
            "brand", "brand.name",
            "category", ARTICLE_SORT_BY_CATEGORY_NAME
    );

    public static final String INVALID_SORT_ARTICLE_FIELD_MESSAGE = "Invalid sorting criteria: ";


    public static final String FIELD_NAME_OR_DESCRIPTION_EMPTY_MESSAGE = "Name and description must not be empty";
    public static final String FIELD_NAME_TOO_LARGE_MESSAGE = "Field 'name' cannot be longer than ";
    public static final String FIELD_DESCRIPTION_TOO_LARGE_MESSAGE = "Field 'name' cannot be longer than ";
    public static final String DUPLICATED_CATEGORY_NAME_MESSAGE = "Category name already exists";
    public static final String INVALID_QUANTITY_MESSAGE = "Invalid quantity";

    public static final String DUPLICATED_BRAND_NAME_MESSAGE = "Brand name already exists";
    public static final String INVALID_BRAND_MESSAGE = "Invalid brand";
    public static final String LIMIT_CATEGORIES_TO_ARTICLE_MESSAGE = "An article should has minimum 1 category and maximum "+ MAX_CATEGORY_ASSOCIATED_TO_ARTICLE +" categories associated.";

    public static final String CATEGORY_NAME_DOES_NOT_EXIST_MESSAGE = "Category with follow name does not exist: ";
    public static final String CATEGORY_ID_DOES_NOT_EXIST_MESSAGE = "Category with follow id does not exist: ";
    public static final String CATEGORY_ID_OR_NAME_MANDATORY_MESSAGE="Category must have either an ID or a name";
}
