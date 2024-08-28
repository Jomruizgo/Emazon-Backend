package com.emazon.msvc_stock.configuration;

import com.emazon.msvc_stock.adapters.driven.jpa.mysql.adapter.BrandAdapter;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.adapter.CategoryAdapter;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.emazon.msvc_stock.adapters.driving.http.mapper.*;
import com.emazon.msvc_stock.domain.api.IBrandServicePort;
import com.emazon.msvc_stock.domain.api.ICategoryServicePort;
import com.emazon.msvc_stock.domain.api.usecase.BrandUseCase;
import com.emazon.msvc_stock.domain.api.usecase.CategoryUseCase;
import com.emazon.msvc_stock.domain.spi.IBrandPersistencePort;
import com.emazon.msvc_stock.domain.spi.ICategoryPersistencePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;

    public BeanConfiguration(ICategoryRepository categoryRepository, ICategoryEntityMapper categoryEntityMapper, IBrandRepository brandRepository, IBrandEntityMapper brandEntityMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryEntityMapper = categoryEntityMapper;
        this.brandRepository = brandRepository;
        this.brandEntityMapper = brandEntityMapper;
    }
    @Bean
    public ICategoryPersistencePort categoryPersistencePort(){
        return new CategoryAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public IBrandPersistencePort brandPersistencePort(){
        return new BrandAdapter(brandRepository, brandEntityMapper);
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

    @Bean
    public IBrandRequestMapper brandRequestMapper() {
        return new BrandRequestMapperImpl();
    }
    @Bean
    public IBrandServicePort brandServicePort(){
        return new BrandUseCase(brandPersistencePort());
    }
}
