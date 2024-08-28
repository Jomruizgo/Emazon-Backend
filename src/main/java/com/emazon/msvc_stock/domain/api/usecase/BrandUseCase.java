package com.emazon.msvc_stock.domain.api.usecase;

import com.emazon.msvc_stock.domain.api.IBrandServicePort;
import com.emazon.msvc_stock.domain.exception.DuplicateNameException;
import com.emazon.msvc_stock.domain.model.Brand;
import com.emazon.msvc_stock.domain.spi.IBrandPersistencePort;
import com.emazon.msvc_stock.domain.util.DomainConstants;

public class BrandUseCase implements IBrandServicePort {
    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }
    @Override
    public void saveBrand(Brand brand) {
        if (brand.getName() == null || brand.getName().trim().isEmpty() || brand.getDescription() == null || brand.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException(DomainConstants.FIELD_NAME_OR_DESCRIPTION_EMPTY_MESSAGE);
        }
        if (brand.getName().length() > DomainConstants.MAX_BRAND_NAME_LENGTH) {
            throw new IllegalArgumentException(DomainConstants.FIELD_NAME_TOO_LARGE_MESSAGE + DomainConstants.MAX_BRAND_NAME_LENGTH);
        }
        if (brand.getDescription().length() > DomainConstants.MAX_BRAND_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException(DomainConstants.FIELD_DESCRIPTION_TOO_LARGE_MESSAGE + DomainConstants.MAX_BRAND_DESCRIPTION_LENGTH);
        }
        if (brandPersistencePort.brandNameExists(brand.getName())) {
            throw new DuplicateNameException(DomainConstants.DUPLICATED_BRAND_NAME_MESSAGE);
        }
        brandPersistencePort.saveBrand(brand);
    }
}
