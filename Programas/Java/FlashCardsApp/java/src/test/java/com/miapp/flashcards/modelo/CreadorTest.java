package com.miapp.flashcards.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreadorTest {

    @Test
    void testConstructorYHerencia() {
        Creador creador = new Creador("Profe", "profe@cole.com", "pass123", 5);

        assertEquals(5, creador.getCursosCreados());

        assertEquals("Profe", creador.getNombre());
        assertEquals("profe@cole.com", creador.getEmail());
        assertEquals("pass123", creador.getPassword());
    }

    @Test
    void testSettersYEstadisticasUsuario() {
        Creador creador = new Creador();
        
        creador.setCursosCreados(10);
        assertEquals(10, creador.getCursosCreados());

        creador.setTiempoUsoSegundos(100);
        creador.sumarTiempo(50);
        
        assertEquals(150, creador.getTiempoUsoSegundos(), "El tiempo total debería ser 150s");
        
        creador.setFechaRegistro("2025-01-01");
        assertEquals("2025-01-01", creador.getFechaRegistro());
    }
    
    @Test
    void testIgualdadUsuarios() {
        Creador c1 = new Creador();
        c1.setId(1L);
        c1.setEmail("a@a.com");
        
        Creador c2 = new Creador();
        c2.setId(1L);
        c2.setEmail("a@a.com");
        
        assertEquals(c1, c2, "Deberían ser iguales si ID e Email coinciden");
    }
}