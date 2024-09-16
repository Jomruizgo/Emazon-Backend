package com.emazon.msvc_stock.adapters.driving.http.dto.request;


import com.emazon.msvc_stock.domain.model.Brand;
import com.emazon.msvc_stock.domain.model.Category;
import com.emazon.msvc_stock.domain.util.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Getter
public class AddArticleRequestDto {
    @NotBlank(message = Constants.FIELD_NAME_OR_DESCRIPTION_EMPTY_MESSAGE)
    private String name;

    @NotBlank(message = Constants.FIELD_NAME_OR_DESCRIPTION_EMPTY_MESSAGE)
    private String description;

    @NotNull(message = "Quantity is mandatory")
    @Positive(message = "Quantity must be greater than zero")
    private Integer quantity;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "At least one category name is required")
    private List<Category> categories;

    @NotNull(message = "Brand is mandatory")
    private Brand brand;
}
