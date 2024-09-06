package com.emazon.msvc_stock.adapters.driven.jpa.mysql.adapter;

import com.emazon.msvc_stock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.emazon.msvc_stock.domain.model.Brand;
import com.emazon.msvc_stock.domain.spi.IBrandPersistencePort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;


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

    @Override
    public Brand findBrandById(Long id) {
        return brandEntityMapper.toModel(brandRepository.findById(id).orElse(null));
    }

    @Override
    public List<Brand> findAllOrderedByName(boolean ascending, int page, int size) {
        Sort sort = Sort.by(ascending ? Sort.Direction.ASC : Sort.Direction.DESC, "name");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return brandEntityMapper.toModelList(brandRepository.findAll(pageRequest).getContent());
    }


}
