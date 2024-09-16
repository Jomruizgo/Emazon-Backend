package com.emazon.msvc_stock.adapters.driving.http.mapper.response;

import com.emazon.msvc_stock.adapters.driving.http.dto.response.CategoryResponseDto;
import com.emazon.msvc_stock.domain.model.Category;

import java.util.Collections;
import java.util.List;

public class CategoryResponseMapperImpl implements ICategoryResponseMapper {

    @Override
    public CategoryResponseDto toCategoryResponse(Category category) {
        if (category == null) {
            return null;
        }

        return new CategoryResponseDto(category.getId(),category.getName(),category.getDescription() );
    }

    @Override
    public List<CategoryResponseDto> toCategoryResponseList(List<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            return Collections.emptyList();
        }

        return categories.stream()
                .map(this::toCategoryResponse)  // es equivalente a .map(category -> toCategoryResponse(category))
                .toList();
        //TODO Add sorting metadata
    }
}
