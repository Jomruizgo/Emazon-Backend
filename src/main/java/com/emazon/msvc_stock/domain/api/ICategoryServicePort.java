package com.emazon.msvc_stock.domain.api;

import com.emazon.msvc_stock.domain.model.Category;

import java.util.List;

public interface ICategoryServicePort {
    void saveCategory(Category category);

}
