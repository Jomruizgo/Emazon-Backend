package com.emazon.msvc_stock.adapters.driving.http.mapper.request;

import com.emazon.msvc_stock.adapters.driving.http.dto.request.AddArticleRequest;
import com.emazon.msvc_stock.domain.model.Article;

public interface IArticleRequestMapper {
    Article addRecuestToArticle(AddArticleRequest addArticleRequest);
}
