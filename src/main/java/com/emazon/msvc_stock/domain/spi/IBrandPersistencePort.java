package com.emazon.msvc_stock.domain.spi;

import com.emazon.msvc_stock.domain.model.Brand;

import java.util.List;

public interface IBrandPersistencePort {
    void saveBrand(Brand brand);
    boolean brandNameExists(String name);

    List<Brand> findAllOrderedByName(boolean ascending, int page, int size);
}
