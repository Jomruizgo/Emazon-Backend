package com.emazon.msvc_stock.adapters.driving.http.mapper.request;

import com.emazon.msvc_stock.adapters.driving.http.dto.request.AddCategoryRequestDto;
import com.emazon.msvc_stock.domain.model.Category;

public interface ICategoryRequestMapper {
    Category addRequestToCategory(AddCategoryRequestDto addCategoryRequest);
}
