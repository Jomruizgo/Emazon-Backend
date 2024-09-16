package com.emazon.msvc_stock.adapters.driving.http.mapper.response;

import com.emazon.msvc_stock.adapters.driving.http.dto.response.ArticleResponseDto;
import com.emazon.msvc_stock.domain.model.Article;

import java.util.Collections;
import java.util.List;

public class ArticleResponseMapperImpl implements IArticleResponseMapper{
    public final ICategoryWithinArticleMapper categoryWithinArticleMapper;

    public ArticleResponseMapperImpl(ICategoryWithinArticleMapper categoryWithinArticleMapper) {
        this.categoryWithinArticleMapper = categoryWithinArticleMapper;
    }

    @Override
    public ArticleResponseDto toArticleResponseDto(Article article) {
        if (article == null){
            return null;
        }
        return new ArticleResponseDto(
                article.getId(),
                article.getName(),
                article.getDescription(),
                article.getQuantity(),
                article.getPrice(),
                article.getBrand(),

                categoryWithinArticleMapper.
                        toCategoryWithinArticleDtoList(article.getCategories()));
    }

    @Override
    public List<ArticleResponseDto> toArticleResponseList(List<Article> articles) {
        if (articles == null || articles.isEmpty()){
            return Collections.emptyList();
        }

        return articles.stream().
                map(this::toArticleResponseDto).toList();
        //TODO Add sorting metadata
    }
}
