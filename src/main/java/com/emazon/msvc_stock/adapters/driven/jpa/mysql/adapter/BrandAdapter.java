package com.emazon.msvc_stock.adapters.driven.jpa.mysql.adapter;

import com.emazon.msvc_stock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.emazon.msvc_stock.domain.model.Brand;
import com.emazon.msvc_stock.domain.spi.IBrandPersistencePort;


public class BrandAdapter implements IBrandPersistencePort {
    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;

    public BrandAdapter(IBrandRepository brandRepository, IBrandEntityMapper brandEntityMapper) {
        this.brandRepository = brandRepository;
        this.brandEntityMapper = brandEntityMapper;
    }

    @Override
    public void saveBrand(Brand brand) { brandRepository.save(brandEntityMapper.toEntity(brand)); }

    @Override
    public boolean brandNameExists(String name) {
        return brandRepository.existsByName(name);
    }


}
