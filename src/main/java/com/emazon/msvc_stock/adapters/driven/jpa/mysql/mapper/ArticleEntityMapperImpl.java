package com.emazon.msvc_stock.adapters.driven.jpa.mysql.mapper;

import com.emazon.msvc_stock.adapters.driven.jpa.mysql.entity.ArticleCategoryEntity;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.emazon.msvc_stock.domain.model.Article;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
@Component
public class ArticleEntityMapperImpl implements IArticleEntityMapper{
    private final ICategoryEntityMapper categoryEntityMapper;

    public ArticleEntityMapperImpl(ICategoryEntityMapper categoryEntityMapper) {
        this.categoryEntityMapper = categoryEntityMapper;
    }

    @Override
    public Article toModel(ArticleEntity articleEntity) {
        Article article = new Article();
        article.setId(articleEntity.getId());
        article.setName(articleEntity.getName());
        article.setDescription(articleEntity.getDescription());
        article.setQuantity(articleEntity.getQuantity());
        article.setPrice(articleEntity.getPrice());

        // Map ArticleCategoryEntity to categories
        article.setCategories(
                articleEntity.getArticleCategories().stream()
                        .map(articleCategory -> categoryEntityMapper.toModel(articleCategory.getCategory()))
                        .collect(Collectors.toSet())
        );

        return article;
    }

    @Override
    public ArticleEntity toEntity(Article article) {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setId(article.getId());
        articleEntity.setName(article.getName());
        articleEntity.setDescription(article.getDescription());
        articleEntity.setQuantity(article.getQuantity());
        articleEntity.setPrice(article.getPrice());

        // Map categories to ArticleCategoryEntity
        articleEntity.setArticleCategories(
                article.getCategories().stream()
                        .map(category -> new ArticleCategoryEntity(null, articleEntity, categoryEntityMapper.toEntity(category)))
                        .collect(Collectors.toSet())
        );

        return articleEntity;
    }
}
