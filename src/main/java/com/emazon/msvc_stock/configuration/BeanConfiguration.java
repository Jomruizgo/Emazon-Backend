package com.emazon.msvc_stock.configuration;

import com.emazon.msvc_stock.adapters.driven.jpa.mysql.adapter.ArticleAdapter;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.adapter.BrandAdapter;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.adapter.CategoryAdapter;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.mapper.ArticleEntityMapperImpl;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.mapper.IArticleEntityMapper;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.repository.IArticleRepository;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.emazon.msvc_stock.adapters.driving.http.mapper.request.*;
import com.emazon.msvc_stock.adapters.driving.http.mapper.response.*;
import com.emazon.msvc_stock.domain.api.IArticleServicePort;
import com.emazon.msvc_stock.domain.api.IBrandServicePort;
import com.emazon.msvc_stock.domain.api.ICategoryServicePort;
import com.emazon.msvc_stock.domain.api.usecase.ArticleUseCase;
import com.emazon.msvc_stock.domain.api.usecase.BrandUseCase;
import com.emazon.msvc_stock.domain.api.usecase.CategoryUseCase;
import com.emazon.msvc_stock.domain.spi.IArticlePersistencePort;
import com.emazon.msvc_stock.domain.spi.IBrandPersistencePort;
import com.emazon.msvc_stock.domain.spi.ICategoryPersistencePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    private final ICategoryRepository categoryRepository;
    private final IBrandRepository brandRepository;
    private final IArticleRepository articleRepository;

    private final ICategoryEntityMapper categoryEntityMapper;
    private final IBrandEntityMapper brandEntityMapper;
    private final IArticleEntityMapper articleEntityMapper;
    private final ICategoryWithinArticleMapper categoryWithinArticleMapper;



    public BeanConfiguration(ICategoryRepository categoryRepository, ICategoryEntityMapper categoryEntityMapper, IBrandRepository brandRepository, IArticleRepository articleRepository, IBrandEntityMapper brandEntityMapper, IArticleEntityMapper articleEntityMapper, ICategoryWithinArticleMapper categoryWithinArticleMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryEntityMapper = categoryEntityMapper;
        this.brandRepository = brandRepository;
        this.articleRepository = articleRepository;
        this.brandEntityMapper = brandEntityMapper;
        this.articleEntityMapper = articleEntityMapper;
        this.categoryWithinArticleMapper = categoryWithinArticleMapper;
    }

    //Pesistence Ports
    @Bean
    public ICategoryPersistencePort categoryPersistencePort(){ return new CategoryAdapter(categoryRepository, categoryEntityMapper); }
    @Bean
    public IBrandPersistencePort brandPersistencePort(){ return new BrandAdapter(brandRepository, brandEntityMapper);
    }
    @Bean
    public IArticlePersistencePort articlePersistencePort(){ return new ArticleAdapter(articleRepository, articleEntityMapper);}


    //Dto Mappers
    @Bean
    public ICategoryRequestMapper categoryRequestMapper() {
        return new CategoryRequestMapperImpl();
    }
    @Bean
    public ICategoryResponseMapper categoryResponseMapper() { return new CategoryResponseMapperImpl(); }
    @Bean
    public IBrandResponseMapper brandResponseMapper() {
        return new BrandResponseMapperImpl();
    }
    @Bean
    public IBrandRequestMapper brandRequestMapper() {
        return new BrandRequestMapperImpl();
    }
    @Bean
    public IArticleRequestMapper articleRequestMapper(){return new ArticleRequestMapperImpl();}
    @Bean
    public IArticleResponseMapper articleResponseMapper(){return new ArticleResponseMapperImpl(categoryWithinArticleMapper);}




    //Driven Manual Mappers
    @Bean
    public IArticleEntityMapper articleEntityMapper(ICategoryEntityMapper categoryEntityMapper) {
        return new ArticleEntityMapperImpl(categoryEntityMapper, brandEntityMapper);
    }

    //Service Ports
    @Bean
    public ICategoryServicePort categoryServicePort(){
        return new CategoryUseCase(categoryPersistencePort());
    }
    @Bean
    public IBrandServicePort brandServicePort(){
        return new BrandUseCase(brandPersistencePort());
    }
    @Bean
    public IArticleServicePort articleServicePort(){ return new ArticleUseCase(articlePersistencePort(), categoryPersistencePort(), brandPersistencePort()); }



}
