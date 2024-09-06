package com.emazon.msvc_stock.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CategoryWithinArticleDto {
    private final Long id;
    private final String name;
}
