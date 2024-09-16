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
    private String validToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJNU1ZDLVVTRVIiLCJzdWIiOiJqdWFuLnBlcmV6QGVtYWlsLmNvbSIsInVzZXJJZCI6MSwiYXV0aG9yaXRpZXMiOiJST0xFX0FETUlOSVNUUkFUT1IiLCJpYXQiOjE3MjY1MDU3MjAsImV4cCI6LTg4NjUwMTQzMTk2MjkwMzQsImp0aSI6ImEzZGY5ZWQxLWIxMzEtNGM5YS1iOGE4LTk4OWZiMDA4MThkNyJ9.LAIAv0eCXCLK_HjURhqOQIEt0vmxlJbktzeybSg5L7Q"; // Token ficticio

   @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(categoryRestControllerAdapter)
                .setControllerAdvice(new ControllerAdvisor()) // Si tienes un manejador de excepciones globales
                .build();

        objectMapper = new ObjectMapper();
    }



    @Test
    void testAddCategory_WithToken_ShouldReturnCreated() throws Exception {
        // Arrange
        AddCategoryRequestDto addCategoryRequest = new AddCategoryRequestDto("Electronics", "Various electronic items");
        Category category = new Category(null, "Electronics", "Various electronic items");

        when(categoryRequestMapper.addRequestToCategory(any(AddCategoryRequestDto.class))).thenReturn(category);
        doNothing().when(categoryServicePort).saveCategory(any(Category.class));

        // Act & Assert
        mockMvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", validToken) // Añadir el token en el header
                        .content(objectMapper.writeValueAsString(addCategoryRequest)))
                .andExpect(status().isCreated());
    }
}