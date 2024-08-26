package com.emazon.msvc_stock.domain.api.usecase;

import com.emazon.msvc_stock.domain.api.ICategoryServicePort;
import com.emazon.msvc_stock.domain.exception.DuplicateCategoryNameException;
import com.emazon.msvc_stock.domain.model.Category;
import com.emazon.msvc_stock.domain.spi.ICategoryPersistencePort;
import com.emazon.msvc_stock.domain.util.DomainConstants;

import java.util.List;

public class CategoryUseCase implements ICategoryServicePort {
    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort){
        this.categoryPersistencePort = categoryPersistencePort;
    }
    @Override
    public void saveCategory(Category category) {
        if (category.getName() == null || category.getName().trim().isEmpty() || category.getDescription() == null || category.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException(DomainConstants.FIELD_NAME_OR_DESCRIPTION_EMPTY_MESSAGE);
        }
        if (category.getName().length() > DomainConstants.MAX_CATEGORY_NAME_LENGTH) {
            throw new IllegalArgumentException(DomainConstants.FIELD_NAME_TOO_LARGE_MESSAGE);
        }
        if (category.getDescription().length() > DomainConstants.MAX_CATEGORY_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException(DomainConstants.FIELD_DESCRIPTION_TOO_LARGE_MESSAGE);
        }
        if (categoryPersistencePort.categoryNameExists(category.getName())) {
            throw new DuplicateCategoryNameException(DomainConstants.DUPLICATED_CATEGORY_NAME_MESSAGE);
        }
        categoryPersistencePort.saveCategory(category);
    }

    @Override
    public List<Category> listCategories(String order, int page, int size) {
        // Si el parámetro 'orden' es nulo o vacío, asignar un valor por defecto (e.g. ascendente)
        if (order == null || order.isBlank()) {
            order = "asc";  // Valor por defecto
        }
        boolean ascending = order.equalsIgnoreCase("asc");

        // Delegar la consulta al repositorio
        return categoryPersistencePort.findAllOrderedByName(ascending, page, size);
    }

}
