package com.emazon.msvc_stock.domain.api;

import com.emazon.msvc_stock.domain.model.Article;

public interface IArticleServicePort {
    void saveArticle(Article article);
}
