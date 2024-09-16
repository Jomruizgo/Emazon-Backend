package com.emazon.msvc_stock.domain.api.usecase;

import com.emazon.msvc_stock.domain.exceptions.DuplicateNameException;
import com.emazon.msvc_stock.domain.model.Category;
import com.emazon.msvc_stock.domain.spi.ICategoryPersistencePort;
import com.emazon.msvc_stock.domain.util.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;
    // Se declara un mock de la interfaz ICategoryPersistencePort, que será inyectado en la clase bajo prueba.

    @InjectMocks
    private CategoryUseCase categoryUseCase;
    // Se inyectan los mocks en la instancia de CategoryUseCase. Mockito creará una instancia de CategoryUseCase
    // e inyectará categoryPersistencePort en ella.

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    // El método `setUp` se ejecuta antes de cada test. Abre los mocks usando MockitoAnnotations.openMocks.

    @ParameterizedTest
    @MethodSource("provideInvalidCategories")
    void testSaveCategoryWithInvalidInput(Category category, String expectedMessage) {
        // Se verifica que al llamar a `saveCategory` con una categoría inválida, se lanza una excepción de tipo IllegalArgumentException.
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryUseCase.saveCategory(category);
        });

        assertEquals(expectedMessage, exception.getMessage());
        // Se comprueba que el mensaje de la excepción es el esperado.
    }

    private static Stream<Arguments> provideInvalidCategories() {
        return Stream.of(
                Arguments.of(new Category(1L, null, "Various electronic items"), TestConstants.FIELD_NAME_OR_DESCRIPTION_EMPTY_MESSAGE),
                Arguments.of(new Category(1L, "", "Various electronic items"), TestConstants.FIELD_NAME_OR_DESCRIPTION_EMPTY_MESSAGE),
                Arguments.of(new Category(1L, "Electronics", null), TestConstants.FIELD_NAME_OR_DESCRIPTION_EMPTY_MESSAGE),
                Arguments.of(new Category(1L, "Electronics", ""), TestConstants.FIELD_NAME_OR_DESCRIPTION_EMPTY_MESSAGE)
        );
    }


    @Test
    void testSaveCategoryWhenNameIsTooLong() {
        // Test para validar que se lanza una excepción cuando el nombre de la categoría excede el límite máximo de longitud.

        String longName = "A".repeat(TestConstants.MAX_CATEGORY_NAME_LENGTH + 1);
        // Se crea un nombre que excede la longitud máxima permitida usando el método `repeat`.

        Category category = new Category(1L, longName, "Various electronic items");
        // Se crea una categoría con un nombre demasiado largo.

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryUseCase.saveCategory(category);
        });
        // Se verifica que se lanza IllegalArgumentException cuando el nombre es demasiado largo.

        assertEquals(TestConstants.FIELD_NAME_TOO_LARGE_MESSAGE + TestConstants.MAX_CATEGORY_NAME_LENGTH, exception.getMessage());
        // Se comprueba que el mensaje de la excepción es el correcto.
    }

    @Test
    void testSaveCategoryWhenDescriptionIsTooLong() {
        // Test para validar que se lanza una excepción cuando la descripción de la categoría excede la longitud máxima.

        String longDescription = "A".repeat(TestConstants.MAX_CATEGORY_DESCRIPTION_LENGTH + 1);
        // Se crea una descripción que excede la longitud máxima permitida.

        Category category = new Category(1L, "Electronics", longDescription);
        // Se crea una categoría con una descripción demasiado larga.

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryUseCase.saveCategory(category);
        });
        // Se verifica que se lanza IllegalArgumentException cuando la descripción es demasiado larga.

        assertEquals(TestConstants.FIELD_DESCRIPTION_TOO_LARGE_MESSAGE + TestConstants.MAX_CATEGORY_DESCRIPTION_LENGTH, exception.getMessage());
        // Se comprueba que el mensaje de la excepción es el esperado.
    }

    @Test
    void testSaveCategorySuccessfully() {
        // Test para validar que se guarda una categoría correctamente cuando los datos son válidos.

        Category category = new Category(1L, "Books", "Various books");
        // Se crea una categoría válida.

        when(categoryPersistencePort.categoryNameExists(category.getName())).thenReturn(false);
        // Se simula que el nombre de la categoría no existe ya en la base de datos.

        assertDoesNotThrow(() -> categoryUseCase.saveCategory(category));
        // Se verifica que no se lanza ninguna excepción cuando se guarda la categoría.

        verify(categoryPersistencePort, times(1)).saveCategory(category);
        // Se comprueba que el método `saveCategory` del mock `categoryPersistencePort` se llama una vez con la categoría.
    }

    @Test
    void testSaveCategoryWithNameAtMaxLength() {
        // Test para validar que una categoría con un nombre de longitud máxima permitida se guarda correctamente.

        String nameAtMaxLength = "A".repeat(TestConstants.MAX_CATEGORY_NAME_LENGTH);
        // Se crea un nombre con la longitud máxima permitida.

        Category category = new Category(1L, nameAtMaxLength, "Various electronic items");
        // Se crea una categoría con un nombre de longitud máxima.

        when(categoryPersistencePort.categoryNameExists(category.getName())).thenReturn(false);
        // Se simula que el nombre de la categoría no existe ya en la base de datos.

        assertDoesNotThrow(() -> categoryUseCase.saveCategory(category));
        // Se verifica que no se lanza ninguna excepción al guardar la categoría.
    }

    @Test
    void testSaveCategoryWhenCategoryNameDoesNotExist() {
        // Test para validar que se guarda correctamente una categoría cuando el nombre no existe.

        Category category = new Category(1L, "Electronics", "Various electronic items");
        // Se crea una categoría válida.

        when(categoryPersistencePort.categoryNameExists(category.getName())).thenReturn(false);
        // Se simula que el nombre de la categoría no existe en la base de datos.

        assertDoesNotThrow(() -> categoryUseCase.saveCategory(category));
        // Se verifica que no se lanza ninguna excepción al guardar la categoría.

        verify(categoryPersistencePort, times(1)).saveCategory(category);
        // Se comprueba que el método `saveCategory` del mock `categoryPersistencePort` se llama una vez con la categoría.
    }

    @Test
    void testSaveCategoryWhenCategoryNameExists() {
        // Test para validar que se lanza una excepción cuando el nombre de la categoría ya existe en la base de datos.

        Category category = new Category(1L, "Electronics", "Various electronic items");
        // Se crea una categoría válida.

        when(categoryPersistencePort.categoryNameExists(category.getName())).thenReturn(true);
        // Se simula que el nombre de la categoría ya existe en la base de datos.

        DuplicateNameException exception = assertThrows(DuplicateNameException.class, () -> {
            categoryUseCase.saveCategory(category);
        });
        // Se verifica que se lanza una excepción `DuplicateNameException` cuando el nombre de la categoría ya existe.

        assertEquals(TestConstants.DUPLICATED_CATEGORY_NAME_MESSAGE, exception.getMessage());
        // Se comprueba que el mensaje de la excepción es el correcto.

        verify(categoryPersistencePort, never()).saveCategory(any(Category.class));
        // Se verifica que el método `saveCategory` del mock `categoryPersistencePort` no se llama cuando el nombre ya existe.
    }

    @ParameterizedTest
    @MethodSource("provideCategoriesForListing")
    void testListCategories(String order, boolean expectedAscending, int page, int size) {
        // Configurar
        List<Category> expectedCategories = List.of(new Category(1L, "Electronics", "Various electronic items"));

        when(categoryPersistencePort.findAllOrderedByName(expectedAscending, page, size)).thenReturn(expectedCategories);

        // Actuar
        List<Category> actualCategories = categoryUseCase.listCategories(order, page, size);

        // Verificar
        assertEquals(expectedCategories, actualCategories);
        verify(categoryPersistencePort, times(1)).findAllOrderedByName(expectedAscending, page, size);
    }

    private static Stream<Arguments> provideCategoriesForListing() {
        return Stream.of(
                Arguments.of(null, true, 0, 10),  // Orden nulo, debe ser ascendente
                Arguments.of("", true, 0, 10),   // Orden vacío, debe ser ascendente
                Arguments.of("asc", true, 0, 10),  // Orden ascendente explícito
                Arguments.of("desc", false, 0, 10) // Orden descendente
        );
    }
}