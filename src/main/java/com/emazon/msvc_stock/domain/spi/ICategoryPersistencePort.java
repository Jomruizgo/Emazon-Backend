package com.emazon.msvc_stock.domain.spi;

import com.emazon.msvc_stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.msvc_stock.domain.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoryPersistencePort {
    void saveCategory(Category category);
    boolean categoryNameExists(String name);
}
