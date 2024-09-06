package com.emazon.msvc_stock.adapters.driving.http.mapper.request;

import com.emazon.msvc_stock.adapters.driving.http.dto.request.AddBrandRequestDto;
import com.emazon.msvc_stock.domain.model.Brand;

public class BrandRequestMapperImpl implements IBrandRequestMapper {
    @Override
    public Brand addRequestToBrand(AddBrandRequestDto addBrandRequestDto) {
        if (addBrandRequestDto == null) {
            return null;
        }

        Brand brand = new Brand();
        brand.setName(addBrandRequestDto.getName());
        brand.setDescription(addBrandRequestDto.getDescription());

        return brand;
    }
}
