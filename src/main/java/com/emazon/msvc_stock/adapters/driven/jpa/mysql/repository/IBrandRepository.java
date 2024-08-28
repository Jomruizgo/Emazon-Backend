package com.emazon.msvc_stock.adapters.driven.jpa.mysql.repository;

import com.emazon.msvc_stock.adapters.driven.jpa.mysql.entity.BrandEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBrandRepository extends JpaRepository<BrandEntity, Long> {
    boolean existsByName(String name);
    Page<BrandEntity> findAll(Pageable pageable);

}
