package com.emazon.msvc_stock.adapters.driving.http.dto.request;


import com.emazon.msvc_stock.domain.util.DomainConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Getter
public class AddArticleRequest {
    @NotBlank(message = DomainConstants.FIELD_NAME_OR_DESCRIPTION_EMPTY_MESSAGE)
    private String name;

    @NotBlank(message = DomainConstants.FIELD_NAME_OR_DESCRIPTION_EMPTY_MESSAGE)
    private String description;

    @NotNull(message = "Quantity is mandatory")
    @Positive(message = "Quantity must be greater than zero")
    private Integer quantity;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "At least one category name is required")
    @NotEmpty(message = "At least one category name is required")
    private List<String> categories;
}
