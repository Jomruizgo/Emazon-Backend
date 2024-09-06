package com.emazon.msvc_stock.adapters.driving.http.controller;

import com.emazon.msvc_stock.adapters.driving.http.dto.request.AddCategoryRequestDto;
import com.emazon.msvc_stock.adapters.driving.http.mapper.request.ICategoryRequestMapper;
import com.emazon.msvc_stock.configuration.exceptionhandler.ControllerAdvisor;
import com.emazon.msvc_stock.domain.api.ICategoryServicePort;
import com.emazon.msvc_stock.domain.model.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryRestControllerAdapterTest {

    @Mock
    private ICategoryServicePort categoryServicePort;
    // Se declara un mock de la interfaz ICategoryServicePort. Este mock simula el comportamiento de la interfaz
    // para que puedas probar el controlador sin depender de una implementación real del servicio.

    @Mock
    private ICategoryRequestMapper categoryRequestMapper;
    // Se declara un mock de la interfaz ICategoryRequestMapper. Este mock simula el comportamiento de la interfaz
    // para que puedas probar el controlador sin depender de una implementación real del mapeador.

    @InjectMocks
    private CategoryRestControllerAdapter categoryRestControllerAdapter;
    // Se crea una instancia de CategoryRestControllerAdapter y se inyectan los mocks (categoryServicePort y categoryRequestMapper)
    // en ella. Esto permite que el controlador utilice los mocks durante las pruebas.

    private MockMvc mockMvc;
    // Se declara una variable para MockMvc, que se usará para realizar peticiones HTTP y verificar respuestas.

    private ObjectMapper objectMapper;
    // Se declara una variable para ObjectMapper, que se usará para convertir objetos a JSON y viceversa.

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Inicializa los mocks anotados con @Mock y @InjectMocks antes de cada prueba. Esto asegura que los mocks
        // estén listos para ser usados en los métodos de prueba.

        mockMvc = MockMvcBuilders.standaloneSetup(categoryRestControllerAdapter)
                .setControllerAdvice(new ControllerAdvisor()) // Si tienes un manejador de excepciones globales
                .build();
        // Configura MockMvc para usar el controlador que estás probando (categoryRestControllerAdapter).
        // También puedes configurar un manejador de excepciones globales (ControllerAdvisor) si es necesario para la prueba.

        objectMapper = new ObjectMapper();
        // Inicializa ObjectMapper, que se usa para convertir objetos Java en JSON y viceversa.
    }

    @Test
    void testAddCategory() throws Exception {
        // Arrange
        AddCategoryRequestDto addCategoryRequest = new AddCategoryRequestDto("Electronics", "Various electronic items");
        // Crea una instancia de AddCategoryRequest que se usará como el contenido de la solicitud en la prueba.

        Category category = new Category(null, "Electronics", "Various electronic items");
        // Crea una instancia de Category que representa el objeto que se espera que el mapeador devuelva.

        when(categoryRequestMapper.addRequestToCategory(any(AddCategoryRequestDto.class))).thenReturn(category);
        // Configura el mock de categoryRequestMapper para que devuelva el objeto `category` cuando se le pase cualquier
        // instancia de AddCategoryRequest.

        doNothing().when(categoryServicePort).saveCategory(any(Category.class));
        // Configura el mock de categoryServicePort para que no haga nada cuando se le llame al método saveCategory
        // con cualquier instancia de Category. Esto simula que el servicio guarda la categoría sin realizar ninguna acción.

        // Act & Assert
        mockMvc.perform(post("/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addCategoryRequest)))
                .andExpect(status().isCreated());
        // Realiza una petición POST a la ruta "/category/" con el contenido de la solicitud (addCategoryRequest) convertido a JSON.
        // Luego, verifica que el estado de la respuesta es 201 Created, lo que indica que la categoría se creó correctamente.
    }
}

