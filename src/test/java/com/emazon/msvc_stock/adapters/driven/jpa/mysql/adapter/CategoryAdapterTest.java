package com.emazon.msvc_stock.adapters.driven.jpa.mysql.adapter;

import com.emazon.msvc_stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.msvc_stock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.emazon.msvc_stock.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryAdapterTest {

    @Mock
    private ICategoryRepository categoryRepository;  // Declara un mock para la interfaz ICategoryRepository.

    @Mock
    private ICategoryEntityMapper categoryEntityMapper;  // Declara un mock para la interfaz ICategoryEntityMapper.

    @InjectMocks
    private CategoryAdapter categoryAdapter;  // Crea una instancia del adaptador CategoryAdapter con los mocks inyectados.

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks y los inyecta en categoryAdapter antes de cada prueba.
    }

    @Test
    void testSaveCategory() {
        Category category = new Category(1L, "Electronics", "Various electronic items");  // Crea una instancia de Category para usar en el test.
        CategoryEntity categoryEntity = new CategoryEntity();  // Crea una instancia de CategoryEntity para usar en la conversión de entidad.

        when(categoryEntityMapper.toEntity(category)).thenReturn(categoryEntity);  // Configura el mock para que devuelva categoryEntity cuando se llame a toEntity(category).

        categoryAdapter.saveCategory(category);  // Llama al método saveCategory del adaptador para probar su comportamiento.

        verify(categoryEntityMapper, times(1)).toEntity(category);  // Verifica que toEntity(category) fue llamado exactamente una vez en el mock categoryEntityMapper.
        verify(categoryRepository, times(1)).save(categoryEntity);  // Verifica que save(categoryEntity) fue llamado exactamente una vez en el mock categoryRepository.
    }

    @Test
    void testCategoryNameExistsWhenNameExists() {
        String name = "Electronics";  // Define el nombre de la categoría para probar.
        when(categoryRepository.existsByName(name)).thenReturn(true);  // Configura el mock para que devuelva true cuando se llame a existsByName(name).

        boolean exists = categoryAdapter.categoryNameExists(name);  // Llama al método categoryNameExists del adaptador para comprobar si el nombre existe.

        assertTrue(exists, "Category name should be reported as existing");  // Verifica que el resultado sea true, indicando que el nombre de la categoría existe.
        verify(categoryRepository, times(1)).existsByName(name);  // Verifica que existsByName(name) fue llamado exactamente una vez en el mock categoryRepository.
    }

    @Test
    void testCategoryNameExistsWhenNameDoesNotExist() {
        String name = "Electronics";  // Define el nombre de la categoría para probar.
        when(categoryRepository.existsByName(name)).thenReturn(false);  // Configura el mock para que devuelva false cuando se llame a existsByName(name).

        boolean exists = categoryAdapter.categoryNameExists(name);  // Llama al método categoryNameExists del adaptador para comprobar si el nombre no existe.

        assertFalse(exists, "Category name should be reported as not existing");  // Verifica que el resultado sea false, indicando que el nombre de la categoría no existe.
        verify(categoryRepository, times(1)).existsByName(name);  // Verifica que existsByName(name) fue llamado exactamente una vez en el mock categoryRepository.
    }

}