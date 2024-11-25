package com.emazon.msvc_stock.domain.spi;

import com.emazon.msvc_stock.domain.model.Category;
import com.emazon.msvc_stock.domain.model.PaginationModel;

import java.util.List;

public interface ICategoryPersistencePort {
    void saveCategory(Category category);
    boolean categoryNameExists(String name);

    Category findCategoryByName(String name);
    Category findCategoryById(Long id);

    PaginationModel findAllOrderedByName(boolean ascending, int page, int size);
}
