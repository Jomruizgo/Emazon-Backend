package com.emazon.msvc_stock.adapters.driven.jpa.mysql.adapter;


import com.emazon.msvc_stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.mapper.PaginationMapper;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.emazon.msvc_stock.domain.model.Category;
import com.emazon.msvc_stock.domain.model.PaginationModel;
import com.emazon.msvc_stock.domain.spi.ICategoryPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public class CategoryAdapter implements ICategoryPersistencePort {
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    public CategoryAdapter(ICategoryRepository categoryRepository, ICategoryEntityMapper categoryEntityMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryEntityMapper = categoryEntityMapper;
    }

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(categoryEntityMapper.toEntity(category));
    }

    @Override
    public boolean categoryNameExists(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryEntityMapper.toModel(categoryRepository.findByName(name).orElse(null));
    }

    @Override
    public Category findCategoryById(Long id) {

        return categoryEntityMapper.toModel(categoryRepository.findById(id).orElse(null));

    }

    @Override
    public PaginationModel findAllOrderedByName(boolean ascending, int page, int size) {

        Sort sort = Sort.by(ascending ? Sort.Direction.ASC : Sort.Direction.DESC, "name");

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<CategoryEntity> categoryPage = categoryRepository.findAll(pageRequest);

        return PaginationMapper.toCategoryPaginationModel(categoryPage, categoryEntityMapper);

    }

}
