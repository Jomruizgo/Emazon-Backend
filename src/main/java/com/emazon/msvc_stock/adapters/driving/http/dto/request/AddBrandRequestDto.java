package com.emazon.msvc_stock.adapters.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddBrandRequestDto {
    private final String name;
    private final String description;
}
