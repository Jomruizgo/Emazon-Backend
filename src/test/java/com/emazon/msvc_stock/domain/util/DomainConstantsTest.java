package com.emazon.msvc_stock.domain.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DomainConstantsTest {
    @Test
    public void testConstructorThrowsExceptionWithMessage() {
        // Verifica que al intentar acceder al constructor, se lanza una IllegalStateException con el mensaje esperado
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            try {
                // Intenta acceder al constructor privado de la clase
                var constructor = DomainConstants.class.getDeclaredConstructor();
                constructor.setAccessible(true); // Permite acceso al constructor privado
                constructor.newInstance(); // Intenta crear una instancia
            } catch (Exception e) {
                // Verifica si la excepción es una IllegalAccessException y captura la causa original
                if (e.getCause() instanceof IllegalStateException) {
                    throw (IllegalStateException) e.getCause();
                }
                throw new RuntimeException(e);
            }
        });

        // Verifica que el mensaje de la excepción es el esperado
        assertEquals("Utility class", thrown.getMessage());
    }
}
