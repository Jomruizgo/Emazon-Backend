package com.emazon.msvc_stock.adapters.driving.http.mapper.response;

import com.emazon.msvc_stock.adapters.driving.http.dto.response.BrandResponse;
import com.emazon.msvc_stock.domain.model.Brand;

import java.util.List;

public interface IBrandResponseMapper {
    BrandResponse toBrandResponse(Brand brand);
    List<BrandResponse> toBrandResponseList(List<Brand> brands);
}
