package com.miapp.flashcards.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PreguntasTest {

    @Test
    void testFactoriaPreguntaAbierta() {
        Pregunta p = FactoriaPreguntas.crearPreguntaSimple("Abierta", "¿Capital de Francia?", "París");
        
        assertNotNull(p);
        assertTrue(p instanceof PreguntaAbierta);
        assertEquals("¿Capital de Francia?", p.getEnunciado());
        assertEquals("París", ((PreguntaAbierta)p).getRespuestaCorrecta());
    }

    @Test
    void testFactoriaPreguntaHueco() {
        Pregunta p = FactoriaPreguntas.crearPreguntaSimple("Rellenar Hueco", "Java es...", "genial");
        
        assertTrue(p instanceof PreguntaRellenarHueco);
        assertEquals("genial", ((PreguntaRellenarHueco)p).getRespuestaCorrecta());
    }

    @Test
    void testFactoriaPreguntaVOF() {
        String[] opciones = {"Verdadero", "Falso", "NS"};
        
        Pregunta p = FactoriaPreguntas.crearPreguntaOpciones("Test (VOF)", "Enunciado", opciones, 0);
        
        assertNotNull(p, "La pregunta no debería ser null");
        assertTrue(p instanceof PreguntaVerdaderoOFalso);
    }
    
    @Test
    void testFactoriaTipoDesconocido() {
        Pregunta p = FactoriaPreguntas.crearPreguntaSimple("TipoInexistente", "X", "Y");
        assertNull(p, "La factoría debe devolver null si el tipo no existe");
    }
}