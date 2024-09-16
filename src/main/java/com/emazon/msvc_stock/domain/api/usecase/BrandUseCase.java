package com.emazon.msvc_stock.domain.api.usecase;

import com.emazon.msvc_stock.domain.api.IBrandServicePort;
import com.emazon.msvc_stock.domain.exceptions.DuplicateNameException;
import com.emazon.msvc_stock.domain.model.Brand;
import com.emazon.msvc_stock.domain.spi.IBrandPersistencePort;
import com.emazon.msvc_stock.domain.util.Constants;

import java.util.List;

public class BrandUseCase implements IBrandServicePort {
    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }
    @Override
    public void saveBrand(Brand brand) {
        if (brand.getName() == null || brand.getName().trim().isEmpty() || brand.getDescription() == null || brand.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException(Constants.FIELD_NAME_OR_DESCRIPTION_EMPTY_MESSAGE);
        }
        if (brand.getName().length() > Constants.MAX_BRAND_NAME_LENGTH) {
            throw new IllegalArgumentException(Constants.FIELD_NAME_TOO_LARGE_MESSAGE + Constants.MAX_BRAND_NAME_LENGTH);
        }
        if (brand.getDescription().length() > Constants.MAX_BRAND_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException(Constants.FIELD_DESCRIPTION_TOO_LARGE_MESSAGE + Constants.MAX_BRAND_DESCRIPTION_LENGTH);
        }
        if (brandPersistencePort.brandNameExists(brand.getName())) {
            throw new DuplicateNameException(Constants.DUPLICATED_BRAND_NAME_MESSAGE);
        }
        brandPersistencePort.saveBrand(brand);
    }

    @Override
    public List<Brand> listBrands(String order, int page, int size) {
        if (order == null || order.isBlank()) {
            order = Constants.DEFAULT_BRAND_NAME_ORDER;
        }
        boolean ascending = order.equalsIgnoreCase(Constants.DEFAULT_BRAND_NAME_ORDER);

        return brandPersistencePort.findAllOrderedByName(ascending, page, size);
    }
}
