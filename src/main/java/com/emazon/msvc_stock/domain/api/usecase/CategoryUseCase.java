package com.emazon.msvc_stock.domain.api.usecase;

import com.emazon.msvc_stock.domain.api.ICategoryServicePort;
import com.emazon.msvc_stock.domain.exceptions.DuplicateNameException;
import com.emazon.msvc_stock.domain.model.Category;
import com.emazon.msvc_stock.domain.model.PaginationModel;
import com.emazon.msvc_stock.domain.spi.ICategoryPersistencePort;
import com.emazon.msvc_stock.domain.util.Constants;

import java.util.List;

public class CategoryUseCase implements ICategoryServicePort {
    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort){
        this.categoryPersistencePort = categoryPersistencePort;
    }
    @Override
    public void saveCategory(Category category) {
        if (category.getName() == null || category.getName().trim().isEmpty() || category.getDescription() == null || category.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException(Constants.FIELD_NAME_OR_DESCRIPTION_EMPTY_MESSAGE);
        }
        if (category.getName().length() > Constants.MAX_CATEGORY_NAME_LENGTH) {
            throw new IllegalArgumentException(Constants.FIELD_NAME_TOO_LARGE_MESSAGE + Constants.MAX_CATEGORY_NAME_LENGTH);
        }
        if (category.getDescription().length() > Constants.MAX_CATEGORY_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException(Constants.FIELD_DESCRIPTION_TOO_LARGE_MESSAGE + Constants.MAX_CATEGORY_DESCRIPTION_LENGTH);
        }
        if (categoryPersistencePort.categoryNameExists(category.getName())) {
            throw new DuplicateNameException(Constants.DUPLICATED_CATEGORY_NAME_MESSAGE);
        }
        categoryPersistencePort.saveCategory(category);
    }

    @Override
    public PaginationModel listCategories(String order, int page, int size) {
        // Si el parámetro 'orden' es nulo o vacío, asignar un valor por defecto (ascendente)
        if (order == null || order.isBlank()) {
            order = Constants.DEFAULT_CATEGORY_NAME_ORDER;  // Valor por defecto
        }
        boolean ascending = order.equalsIgnoreCase(Constants.DEFAULT_CATEGORY_NAME_ORDER);

        // Delegar la consulta al repositorio
        return categoryPersistencePort.findAllOrderedByName(ascending, page, size);
    }

}
