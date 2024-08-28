package com.emazon.msvc_stock.adapters.driven.jpa.mysql.mapper;

import com.emazon.msvc_stock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.emazon.msvc_stock.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IBrandEntityMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Brand toModel(BrandEntity brandEntity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    BrandEntity toEntity(Brand brand);


}
