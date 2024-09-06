package com.emazon.msvc_stock.adapters.driving.http.mapper.response;

import com.emazon.msvc_stock.adapters.driving.http.dto.response.CategoryWithinArticleDto;
import com.emazon.msvc_stock.domain.model.Category;

import java.util.List;
import java.util.Set;

public interface ICategoryWithinArticleMapper {
    CategoryWithinArticleDto toCategoryWithinArticleDto(Category category);
    List<CategoryWithinArticleDto> toCategoryWithinArticleDtoList(Set<Category> categories);
}
