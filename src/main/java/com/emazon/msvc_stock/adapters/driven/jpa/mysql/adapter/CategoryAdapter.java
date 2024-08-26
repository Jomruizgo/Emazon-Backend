package com.emazon.msvc_stock.adapters.driven.jpa.mysql.adapter;


import com.emazon.msvc_stock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.emazon.msvc_stock.domain.model.Category;
import com.emazon.msvc_stock.domain.spi.ICategoryPersistencePort;

public class CategoryAdapter implements ICategoryPersistencePort {
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    public CategoryAdapter(ICategoryRepository categoryRepository, ICategoryEntityMapper categoryEntityMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryEntityMapper = categoryEntityMapper;
    }

    @Override
    public void saveCategory(Category category) {
        //En el dominio se maneja la duplicidad del nombre de la categoria
        categoryRepository.save(categoryEntityMapper.toEntity(category));

    }

    @Override
    public boolean categoryNameExists(String name) {
        return categoryRepository.existsByName(name);
    }

}
