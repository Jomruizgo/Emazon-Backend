package com.emazon.msvc_stock.adapters.driven.jpa.mysql.mapper;

import com.emazon.msvc_stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.msvc_stock.domain.model.Category;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ICategoryEntityMapperTest {

    // Crear una instancia del mapper manualmente
    private final ICategoryEntityMapper mapper = Mappers.getMapper(ICategoryEntityMapper.class);

    @Test
    void testToModel() {
        // Preparar datos de prueba
        CategoryEntity categoryEntity = new CategoryEntity(1L,"Test Category","Test Description");


        // Ejecutar el mapeo usando el mapper
        Category category = mapper.toModel(categoryEntity);

        // Verificar que el ID en la entidad se mapea correctamente al modelo
        assertEquals(categoryEntity.getId(), category.getId());

        // Verificar que el nombre en la entidad se mapea correctamente al modelo
        assertEquals(categoryEntity.getName(), category.getName());

        // Verificar que la descripción en la entidad se mapea correctamente al modelo
        assertEquals(categoryEntity.getDescription(), category.getDescription());
    }

    @Test
    void testToEntity() {
        // Preparar datos de prueba
        Category category = new Category(1L,"Test Category","Test Description");

        // Ejecutar el mapeo usando el mapper
        CategoryEntity categoryEntity = mapper.toEntity(category);

        // Verificar que el ID en el modelo se mapea correctamente a la entidad
        assertEquals(category.getId(), categoryEntity.getId());

        // Verificar que el nombre en el modelo se mapea correctamente a la entidad
        assertEquals(category.getName(), categoryEntity.getName());

        // Verificar que la descripción en el modelo se mapea correctamente a la entidad
        assertEquals(category.getDescription(), categoryEntity.getDescription());
    }
}

