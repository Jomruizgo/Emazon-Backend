package com.emazon.msvc_stock.adapters.driving.http.mapper.response;

import com.emazon.msvc_stock.adapters.driving.http.dto.response.CategoryResponse;
import com.emazon.msvc_stock.domain.model.Category;

import java.util.List;

public interface ICategoryResponseMapper {
    CategoryResponse toCategoryResponse(Category category);
    List<CategoryResponse> toCategoryResponseList(List<Category> categories);
}

