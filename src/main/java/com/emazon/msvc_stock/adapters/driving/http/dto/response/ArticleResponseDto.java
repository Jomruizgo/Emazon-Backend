package com.emazon.msvc_stock.adapters.driving.http.dto.response;

import com.emazon.msvc_stock.domain.model.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class ArticleResponseDto {
    private Long id;
    private String name;
    private String description;
    private int quantity;
    private BigDecimal price;
    private Brand brand;
    private List<CategoryWithinArticleDto> categories;
}
