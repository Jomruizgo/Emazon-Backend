package com.emazon.msvc_stock.domain.api;

import com.emazon.msvc_stock.domain.model.Brand;

import java.util.List;

public interface IBrandServicePort {
    void saveBrand(Brand brand);
    List<Brand> listBrands(String order, int page, int size);
}
