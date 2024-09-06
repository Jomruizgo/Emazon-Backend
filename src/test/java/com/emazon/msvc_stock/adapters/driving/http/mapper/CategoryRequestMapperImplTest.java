package com.emazon.msvc_stock.adapters.driving.http.mapper;

import com.emazon.msvc_stock.adapters.driving.http.dto.request.AddCategoryRequestDto;
import com.emazon.msvc_stock.adapters.driving.http.mapper.request.CategoryRequestMapperImpl;
import com.emazon.msvc_stock.domain.model.Category;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CategoryRequestMapperImplTest {

    private final CategoryRequestMapperImpl mapper = new CategoryRequestMapperImpl();

    @Test
    void testAddRequestToCategory_withValidRequest() {
        // Preparar datos de prueba
        AddCategoryRequestDto addCategoryRequest = new AddCategoryRequestDto("Test Category","Test Description");


        // Ejecutar la conversión
        Category category = mapper.addRequestToCategory(addCategoryRequest);

        // Verificar resultados
        assertEquals("Test Category", category.getName());
        assertEquals("Test Description", category.getDescription());
    }

    @Test
    void testAddRequestToCategory_withNullRequest() {
        // Ejecutar la conversión con un valor nulo
        Category category = mapper.addRequestToCategory(null);

        // Verificar que el resultado es nulo
        assertNull(category);
    }
}
