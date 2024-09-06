package com.emazon.msvc_stock.domain.spi;

import com.emazon.msvc_stock.domain.model.Category;

import java.util.List;

public interface ICategoryPersistencePort {
    void saveCategory(Category category);
    boolean categoryNameExists(String name);

    Category findCategoryByName(String name);
    Category findCategoryById(Long id);

    List<Category> findAllOrderedByName(boolean ascending, int page, int size);
}
