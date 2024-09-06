package com.emazon.msvc_stock.adapters.driving.http.mapper.response;

import com.emazon.msvc_stock.adapters.driving.http.dto.response.BrandResponseDto;
import com.emazon.msvc_stock.domain.model.Brand;

import java.util.List;

public interface IBrandResponseMapper {
    BrandResponseDto toBrandResponse(Brand brand);
    List<BrandResponseDto> toBrandResponseList(List<Brand> brands);
}
