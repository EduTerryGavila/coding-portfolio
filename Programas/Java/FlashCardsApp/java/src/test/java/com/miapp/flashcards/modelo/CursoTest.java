package com.miapp.flashcards.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CursoTest {

    @Test
    void testCreacionCursoCompleto() {
        Creador creador = new Creador("Profe", "profe@test.com", "123", 10);
        
        Curso curso = new Curso("Historia", creador);
        curso.setId(1L);
        
        assertEquals("Historia", curso.getNombre());
        assertEquals(creador, curso.getCreador());
        assertNotNull(curso.getPreguntas(), "La lista de preguntas no debe ser nula");
    }

    @Test
    void testAgregarPreguntas() {
        Curso curso = new Curso();
        Pregunta p1 = new PreguntaAbierta("¿Hola?", "Hola");
        
        curso.getPreguntas().add(p1);
        
        assertEquals(1, curso.getPreguntas().size());
        assertEquals("¿Hola?", curso.getPreguntas().get(0).getEnunciado());
    }
    
    @Test
    void testCreadorPropiedades() {
        Creador c = new Creador();
        c.setCursosCreados(5);
        assertEquals(5, c.getCursosCreados());
    }
}