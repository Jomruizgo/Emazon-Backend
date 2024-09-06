package com.emazon.msvc_stock.adapters.driven.jpa.mysql.mapper;


import com.emazon.msvc_stock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.emazon.msvc_stock.domain.model.Article;

import java.util.List;


public interface IArticleEntityMapper {
    Article toModel(ArticleEntity articleEntity);

    ArticleEntity toEntity(Article article);

    List<Article> toModelList(List<ArticleEntity> articles);
}
