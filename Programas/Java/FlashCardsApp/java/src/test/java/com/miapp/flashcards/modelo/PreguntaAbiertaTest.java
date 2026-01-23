package com.miapp.flashcards.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PreguntaAbiertaTest {

    @Test
    void testConstructor() {
        String enunciado = "Explica el ciclo del agua";
        String respuesta = "El agua se evapora, condensa y precipita...";
        
        PreguntaAbierta p = new PreguntaAbierta(enunciado, respuesta);
        
        assertEquals(enunciado, p.getEnunciado());
        assertEquals(respuesta, p.getRespuestaCorrecta());
    }

    @Test
    void testSetters() {
        PreguntaAbierta p = new PreguntaAbierta();
        p.setEnunciado("Pregunta Test");
        p.setRespuestaCorrecta("Respuesta Test");
        
        assertEquals("Pregunta Test", p.getEnunciado());
        assertEquals("Respuesta Test", p.getRespuestaCorrecta());
    }
    
    @Test
    void testRespuestaVacia() {
        PreguntaAbierta p = new PreguntaAbierta("Dime nada", "");
        assertEquals("", p.getRespuestaCorrecta());
    }
}