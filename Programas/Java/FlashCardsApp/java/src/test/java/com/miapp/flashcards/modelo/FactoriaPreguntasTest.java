package com.miapp.flashcards.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FactoriaPreguntasTest {

    @Test
    void testCrearPreguntaAbierta() {
        Pregunta p = FactoriaPreguntas.crearPreguntaSimple("Abierta", "¿Capital?", "Madrid");
        
        assertNotNull(p, "No debería ser null");
        assertTrue(p instanceof PreguntaAbierta, "Debería ser instancia de PreguntaAbierta");
        assertEquals("¿Capital?", p.getEnunciado());
        assertEquals("Madrid", ((PreguntaAbierta)p).getRespuestaCorrecta());
    }

    @Test
    void testCrearPreguntaRellenarHueco() {
        Pregunta p = FactoriaPreguntas.crearPreguntaSimple("Rellenar Hueco", "Java es ...", "Guay");
        
        assertNotNull(p);
        assertTrue(p instanceof PreguntaRellenarHueco);
        assertEquals("Guay", ((PreguntaRellenarHueco)p).getRespuestaCorrecta());
    }

    @Test
    void testCrearPreguntaVOF() {
        String[] opciones = {"V", "F"};
        Pregunta p = FactoriaPreguntas.crearPreguntaOpciones("Test (VOF)", "La tierra es plana", opciones, 1); 
        
        assertNotNull(p);
        assertTrue(p instanceof PreguntaVerdaderoOFalso);
        assertEquals(1, ((PreguntaVerdaderoOFalso)p).getIndiceCorrecta());
    }

    @Test
    void testTipoDesconocido() {
        Pregunta p = FactoriaPreguntas.crearPreguntaSimple("Inventado", "A", "B");
        assertNull(p, "Si el tipo no existe, la factoría debe devolver null");
    }
}