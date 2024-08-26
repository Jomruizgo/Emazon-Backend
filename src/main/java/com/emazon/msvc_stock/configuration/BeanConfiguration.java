package com.emazon.msvc_stock.configuration;

import com.emazon.msvc_stock.adapters.driven.jpa.mysql.adapter.CategoryAdapter;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.emazon.msvc_stock.adapters.driving.http.mapper.CategoryRequestMapperImpl;
import com.emazon.msvc_stock.adapters.driving.http.mapper.CategoryResponseMapperImpl;
import com.emazon.msvc_stock.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.emazon.msvc_stock.adapters.driving.http.mapper.ICategoryResponseMapper;
import com.emazon.msvc_stock.domain.api.ICategoryServicePort;
import com.emazon.msvc_stock.domain.api.usecase.CategoryUseCase;
import com.emazon.msvc_stock.domain.spi.ICategoryPersistencePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    public BeanConfiguration(ICategoryRepository categoryRepository, ICategoryEntityMapper categoryEntityMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryEntityMapper = categoryEntityMapper;
    }
    @Bean
    public ICategoryPersistencePort categoryPersistencePort(){
        return new CategoryAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryRequestMapper categoryRequestMapper() {
        return new CategoryRequestMapperImpl();
    }
    @Bean
    public ICategoryResponseMapper categoryResponseMapper() { return new CategoryResponseMapperImpl(); }
    @Bean
    public ICategoryServicePort categoryServicePort(){
        return new CategoryUseCase(categoryPersistencePort());
    }
}
