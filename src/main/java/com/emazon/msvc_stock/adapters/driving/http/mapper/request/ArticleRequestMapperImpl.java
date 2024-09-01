package com.emazon.msvc_stock.adapters.driving.http.mapper.request;

import com.emazon.msvc_stock.adapters.driving.http.dto.request.AddArticleRequest;
import com.emazon.msvc_stock.domain.model.Article;
import com.emazon.msvc_stock.domain.model.Category;

import java.util.stream.Collectors;

public class ArticleRequestMapperImpl implements IArticleRequestMapper{
    @Override
    public Article addRecuestToArticle(AddArticleRequest addArticleRequest) {
        if (addArticleRequest == null) {
            return null;
        }

        Article article = new Article();

        article.setName(addArticleRequest.getName());
        article.setDescription(addArticleRequest.getDescription());
        article.setQuantity(addArticleRequest.getQuantity());
        article.setPrice(addArticleRequest.getPrice());
        article.setCategories(
                addArticleRequest.getCategories().stream() //Recorremos la lista
                .map(name -> { //Hacemos mapeo de los elementos de la lista
                    Category category = new Category();
                    category.setName(name);
                    return category;
                }).collect(Collectors.toSet())); //Convertimos el mapeo a un set

        return article;
    }
}
