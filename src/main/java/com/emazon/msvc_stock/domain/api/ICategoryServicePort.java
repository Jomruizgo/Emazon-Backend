package com.emazon.msvc_stock.domain.api;

import com.emazon.msvc_stock.domain.model.Category;
import com.emazon.msvc_stock.domain.model.PaginationModel;

import java.util.List;

public interface ICategoryServicePort {
    void saveCategory(Category category);

    PaginationModel<Category> listCategories(String order, int page, int size);
}
