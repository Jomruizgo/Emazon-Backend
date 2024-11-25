package com.emazon.msvc_stock.domain.spi;

import com.emazon.msvc_stock.domain.model.Article;

import java.util.List;

public interface IArticlePersistencePort {
    void saveArticle(Article article);

    List<Article> findArticlesSortedByField(String sortBy, boolean ascending, int page, int size);

    Article findArticleByArticleId(Long articleId);
}
