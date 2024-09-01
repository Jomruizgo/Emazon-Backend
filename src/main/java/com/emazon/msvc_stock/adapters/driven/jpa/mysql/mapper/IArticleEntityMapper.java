package com.emazon.msvc_stock.adapters.driven.jpa.mysql.mapper;


import com.emazon.msvc_stock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.emazon.msvc_stock.domain.model.Article;


public interface IArticleEntityMapper {
    Article toModel(ArticleEntity articleEntity);

    ArticleEntity toEntity(Article article);
}
