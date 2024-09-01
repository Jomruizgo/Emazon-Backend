package com.emazon.msvc_stock.adapters.driving.http.mapper.response;

import com.emazon.msvc_stock.adapters.driving.http.dto.response.BrandResponse;
import com.emazon.msvc_stock.domain.model.Brand;

import java.util.Collections;
import java.util.List;

public class BrandResponseMapperImpl implements IBrandResponseMapper{
    @Override
    public BrandResponse toBrandResponse(Brand brand) {
        if (brand == null){
            return null;
        }

        return new BrandResponse(brand.getId(), brand.getName(), brand.getDescription());
    }

    @Override
    public List<BrandResponse> toBrandResponseList(List<Brand> brands) {
        if (brands == null || brands.isEmpty()){
            return Collections.emptyList();
        }

        return brands.stream()
                .map(this::toBrandResponse)
                .toList();
    }
}
