package com.emazon.msvc_stock.domain.spi;

import com.emazon.msvc_stock.domain.model.Brand;

public interface IBrandPersistencePort {
    void saveBrand(Brand brand);
    boolean brandNameExists(String name);
}
