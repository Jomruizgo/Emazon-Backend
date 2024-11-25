package com.emazon.msvc_stock.domain.api;

import com.emazon.msvc_stock.domain.model.Article;

import java.util.List;

public interface IArticleServicePort {
    void saveArticle(Article article);

    List<Article> listArticles(String sortBy,String order, int page, int size);

    boolean increaseArticleStocks(Long articleId, int quantity);
}
