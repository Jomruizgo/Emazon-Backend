package com.emazon.msvc_stock.adapters.driving.http.mapper.response;

import com.emazon.msvc_stock.adapters.driving.http.dto.response.ArticleResponseDto;
import com.emazon.msvc_stock.domain.model.Article;

import java.util.List;

public interface IArticleResponseMapper {
    ArticleResponseDto toArticleResponseDto(Article article);

    List<ArticleResponseDto> toArticleResponseList(List<Article> articles);
}
