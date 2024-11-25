package com.emazon.msvc_stock.adapters.driven.jpa.mysql.mapper;

import com.emazon.msvc_stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.msvc_stock.domain.model.Category;
import com.emazon.msvc_stock.domain.model.PaginationModel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class PaginationMapper {

    public static PaginationModel<Category> toCategoryPaginationModel(Page<CategoryEntity> page, ICategoryEntityMapper mapper) {
        List<Category> convertedContent = page.getContent().stream()
                .map(mapper::toModel)
                .collect(Collectors.toList());

        return new PaginationModel<>(
                convertedContent,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.isFirst()
        );
    }


}
