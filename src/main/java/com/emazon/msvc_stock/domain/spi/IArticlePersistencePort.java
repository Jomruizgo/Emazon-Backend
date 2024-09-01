package com.emazon.msvc_stock.domain.spi;

import com.emazon.msvc_stock.domain.model.Article;

public interface IArticlePersistencePort {
    void saveArticle(Article article);
}
