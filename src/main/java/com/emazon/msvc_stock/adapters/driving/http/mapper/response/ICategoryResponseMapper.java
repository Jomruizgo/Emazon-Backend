package com.emazon.msvc_stock.adapters.driving.http.mapper.response;

import com.emazon.msvc_stock.adapters.driving.http.dto.response.CategoryResponseDto;
import com.emazon.msvc_stock.domain.model.Category;

import java.util.List;

public interface ICategoryResponseMapper {
    CategoryResponseDto toCategoryResponse(Category category);
    List<CategoryResponseDto> toCategoryResponseList(List<Category> categories);

    CategoryResponseDto toResponse(Category category);
}

