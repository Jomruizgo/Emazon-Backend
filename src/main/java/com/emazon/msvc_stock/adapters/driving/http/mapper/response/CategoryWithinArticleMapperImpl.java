package com.emazon.msvc_stock.adapters.driving.http.mapper.response;

import com.emazon.msvc_stock.adapters.driving.http.dto.response.CategoryWithinArticleDto;
import com.emazon.msvc_stock.domain.model.Category;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
@Component
public class CategoryWithinArticleMapperImpl implements ICategoryWithinArticleMapper{
    @Override
    public CategoryWithinArticleDto toCategoryWithinArticleDto(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryWithinArticleDto(category.getId(), category.getName());
    }

    @Override
    public List<CategoryWithinArticleDto> toCategoryWithinArticleDtoList(Set<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            return Collections.emptyList();
        }

        return categories.stream()
                .sorted(Comparator.comparing(Category::getName, Comparator.naturalOrder()))
                .map(this::toCategoryWithinArticleDto)
                .toList();
    }
}
