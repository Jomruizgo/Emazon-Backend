package com.emazon.msvc_stock.adapters.driving.http.mapper.request;

import com.emazon.msvc_stock.adapters.driving.http.dto.request.AddArticleRequestDto;
import com.emazon.msvc_stock.domain.model.Article;

import java.util.HashSet;

public class ArticleRequestMapperImpl implements IArticleRequestMapper{
    @Override
    public Article addRecuestToArticle(AddArticleRequestDto addArticleRequestDto) {
        if (addArticleRequestDto == null) {
            return null;
        }

        Article article = new Article();

        article.setName(addArticleRequestDto.getName());
        article.setDescription(addArticleRequestDto.getDescription());
        article.setQuantity(addArticleRequestDto.getQuantity());
        article.setPrice(addArticleRequestDto.getPrice());
        article.setBrand(addArticleRequestDto.getBrand());

        article.setCategories(
                new HashSet<>(addArticleRequestDto.getCategories()));


        return article;
    }
}
