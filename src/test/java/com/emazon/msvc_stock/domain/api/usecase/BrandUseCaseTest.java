package com.emazon.msvc_stock.domain.api.usecase;

import com.emazon.msvc_stock.domain.exception.DuplicateNameException;
import com.emazon.msvc_stock.domain.model.Brand;
import com.emazon.msvc_stock.domain.spi.IBrandPersistencePort;
import com.emazon.msvc_stock.domain.util.DomainConstantsTrial;
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

class BrandUseCaseTest {

    @Mock
    private IBrandPersistencePort brandPersistencePort;
    // Se declara un mock de la interfaz IBrandPersistencePort, que será inyectado en la clase bajo prueba.

    @InjectMocks
    private BrandUseCase brandUseCase;
    // Se inyectan los mocks en la instancia de BrandUseCase. Mockito creará una instancia de BrandUseCase
    // e inyectará brandPersistencePort en ella.

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    // El método `setUp` se ejecuta antes de cada test. Abre los mocks usando MockitoAnnotations.openMocks.

    @ParameterizedTest
    @MethodSource("provideInvalidBrands")
    void testSaveBrandWithInvalidInput(Brand brand, String expectedMessage) {
        // Se verifica que al llamar a `saveBrand` con una categoría inválida, se lanza una excepción de tipo IllegalArgumentException.
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            brandUseCase.saveBrand(brand);
        });

        assertEquals(expectedMessage, exception.getMessage());
        // Se comprueba que el mensaje de la excepción es el esperado.
    }

    private static Stream<Arguments> provideInvalidBrands() {
        return Stream.of(
                Arguments.of(new Brand(1L, null, "Various electronic items"), DomainConstantsTrial.FIELD_NAME_OR_DESCRIPTION_EMPTY_MESSAGE),
                Arguments.of(new Brand(1L, "", "Various electronic items"), DomainConstantsTrial.FIELD_NAME_OR_DESCRIPTION_EMPTY_MESSAGE),
                Arguments.of(new Brand(1L, "Electronics", null), DomainConstantsTrial.FIELD_NAME_OR_DESCRIPTION_EMPTY_MESSAGE),
                Arguments.of(new Brand(1L, "Electronics", ""), DomainConstantsTrial.FIELD_NAME_OR_DESCRIPTION_EMPTY_MESSAGE)
        );
    }


    @Test
    void testSaveBrandWhenNameIsTooLong() {
        // Test para validar que se lanza una excepción cuando el nombre de la categoría excede el límite máximo de longitud.

        String longName = "A".repeat(DomainConstantsTrial.MAX_BRAND_NAME_LENGTH + 1);
        // Se crea un nombre que excede la longitud máxima permitida usando el método `repeat`.

        Brand brand = new Brand(1L, longName, "Various electronic items");
        // Se crea una categoría con un nombre demasiado largo.

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            brandUseCase.saveBrand(brand);
        });
        // Se verifica que se lanza IllegalArgumentException cuando el nombre es demasiado largo.

        assertEquals(DomainConstantsTrial.FIELD_NAME_TOO_LARGE_MESSAGE + DomainConstantsTrial.MAX_BRAND_NAME_LENGTH, exception.getMessage());
        // Se comprueba que el mensaje de la excepción es el correcto.
    }

    @Test
    void testSaveBrandWhenDescriptionIsTooLong() {
        // Test para validar que se lanza una excepción cuando la descripción de la categoría excede la longitud máxima.

        String longDescription = "A".repeat(DomainConstantsTrial.MAX_BRAND_DESCRIPTION_LENGTH + 1);
        // Se crea una descripción que excede la longitud máxima permitida.

        Brand brand = new Brand(1L, "Electronics", longDescription);
        // Se crea una categoría con una descripción demasiado larga.

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            brandUseCase.saveBrand(brand);
        });
        // Se verifica que se lanza IllegalArgumentException cuando la descripción es demasiado larga.

        assertEquals(DomainConstantsTrial.FIELD_DESCRIPTION_TOO_LARGE_MESSAGE + DomainConstantsTrial.MAX_BRAND_DESCRIPTION_LENGTH, exception.getMessage());
        // Se comprueba que el mensaje de la excepción es el esperado.
    }

    @Test
    void testSaveBrandSuccessfully() {
        // Test para validar que se guarda una categoría correctamente cuando los datos son válidos.

        Brand brand = new Brand(1L, "Books", "Various books");
        // Se crea una categoría válida.

        when(brandPersistencePort.brandNameExists(brand.getName())).thenReturn(false);
        // Se simula que el nombre de la categoría no existe ya en la base de datos.

        assertDoesNotThrow(() -> brandUseCase.saveBrand(brand));
        // Se verifica que no se lanza ninguna excepción cuando se guarda la categoría.

        verify(brandPersistencePort, times(1)).saveBrand(brand);
        // Se comprueba que el método `saveBrand` del mock `brandPersistencePort` se llama una vez con la categoría.
    }

    @Test
    void testSaveBrandWithNameAtMaxLength() {
        // Test para validar que una categoría con un nombre de longitud máxima permitida se guarda correctamente.

        String nameAtMaxLength = "A".repeat(DomainConstantsTrial.MAX_BRAND_NAME_LENGTH);
        // Se crea un nombre con la longitud máxima permitida.

        Brand brand = new Brand(1L, nameAtMaxLength, "Various electronic items");
        // Se crea una categoría con un nombre de longitud máxima.

        when(brandPersistencePort.brandNameExists(brand.getName())).thenReturn(false);
        // Se simula que el nombre de la categoría no existe ya en la base de datos.

        assertDoesNotThrow(() -> brandUseCase.saveBrand(brand));
        // Se verifica que no se lanza ninguna excepción al guardar la categoría.
    }

    @Test
    void testSaveBrandWhenBrandNameDoesNotExist() {
        // Test para validar que se guarda correctamente una categoría cuando el nombre no existe.

        Brand brand = new Brand(1L, "Electronics", "Various electronic items");
        // Se crea una categoría válida.

        when(brandPersistencePort.brandNameExists(brand.getName())).thenReturn(false);
        // Se simula que el nombre de la categoría no existe en la base de datos.

        assertDoesNotThrow(() -> brandUseCase.saveBrand(brand));
        // Se verifica que no se lanza ninguna excepción al guardar la categoría.

        verify(brandPersistencePort, times(1)).saveBrand(brand);
        // Se comprueba que el método `saveBrand` del mock `brandPersistencePort` se llama una vez con la categoría.
    }

    @Test
    void testSaveBrandWhenBrandNameExists() {
        // Test para validar que se lanza una excepción cuando el nombre de la categoría ya existe en la base de datos.

        Brand brand = new Brand(1L, "Electronics", "Various electronic items");
        // Se crea una categoría válida.

        when(brandPersistencePort.brandNameExists(brand.getName())).thenReturn(true);
        // Se simula que el nombre de la categoría ya existe en la base de datos.

        DuplicateNameException exception = assertThrows(DuplicateNameException.class, () -> {
            brandUseCase.saveBrand(brand);
        });
        // Se verifica que se lanza una excepción `DuplicateNameException` cuando el nombre de la categoría ya existe.

        assertEquals(DomainConstantsTrial.DUPLICATED_BRAND_NAME_MESSAGE, exception.getMessage());
        // Se comprueba que el mensaje de la excepción es el correcto.

        verify(brandPersistencePort, never()).saveBrand(any(Brand.class));
        // Se verifica que el método `saveBrand` del mock `brandPersistencePort` no se llama cuando el nombre ya existe.
    }

    @ParameterizedTest
    @MethodSource("provideBrandsForListing")
    void testListBrands(String order, boolean expectedAscending, int page, int size) {
        // Configurar
        List<Brand> expectedBrands = List.of(new Brand(1L, "Electronics", "Various electronic items"));

        when(brandPersistencePort.findAllOrderedByName(expectedAscending, page, size)).thenReturn(expectedBrands);

        // Actuar
        List<Brand> actualBrands = brandUseCase.listBrands(order, page, size);

        // Verificar
        assertEquals(expectedBrands, actualBrands);
        verify(brandPersistencePort, times(1)).findAllOrderedByName(expectedAscending, page, size);
    }

    private static Stream<Arguments> provideBrandsForListing() {
        return Stream.of(
                Arguments.of(null, true, 0, 10),  // Orden nulo, debe ser ascendente
                Arguments.of("", true, 0, 10),   // Orden vacío, debe ser ascendente
                Arguments.of("asc", true, 0, 10),  // Orden ascendente explícito
                Arguments.of("desc", false, 0, 10) // Orden descendente
        );
    }

}