package com.emazon.msvc_stock.adapters.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddCategoryRequestDto {
    private final String name;
    private final String description;
}
