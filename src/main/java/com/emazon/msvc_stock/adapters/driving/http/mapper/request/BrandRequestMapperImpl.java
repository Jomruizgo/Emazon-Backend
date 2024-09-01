package com.emazon.msvc_stock.adapters.driving.http.mapper.request;

import com.emazon.msvc_stock.adapters.driving.http.dto.request.AddBrandRequest;
import com.emazon.msvc_stock.domain.model.Brand;

public class BrandRequestMapperImpl implements IBrandRequestMapper {
    @Override
    public Brand addRequestToBrand(AddBrandRequest addBrandRequest) {
        if (addBrandRequest == null) {
            return null;
        }

        Brand brand = new Brand();
        brand.setName(addBrandRequest.getName());
        brand.setDescription(addBrandRequest.getDescription());

        return brand;
    }
}
