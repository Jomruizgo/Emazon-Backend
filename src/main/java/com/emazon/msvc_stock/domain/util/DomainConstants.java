package com.emazon.msvc_stock.domain.util;

public final class DomainConstants {
    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }


    public static final int MAX_CATEGORY_NAME_LENGTH=50;
    public static final int MAX_CATEGORY_DESCRIPTION_LENGTH=90;
    public static final String DEFAULT_CATEGORY_NAME_ORDER="asc";


    public static final String FIELD_NAME_OR_DESCRIPTION_EMPTY_MESSAGE = "Name and description must not be empty";
    public static final String FIELD_NAME_TOO_LARGE_MESSAGE = "Field 'name' cannot be longer than "+ MAX_CATEGORY_NAME_LENGTH;
    public static final String FIELD_DESCRIPTION_TOO_LARGE_MESSAGE = "Field 'name' cannot be longer than "+ MAX_CATEGORY_DESCRIPTION_LENGTH;
    public static final String DUPLICATED_CATEGORY_NAME_MESSAGE = "Category name already exists";

}
