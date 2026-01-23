package com.miapp.flashcards.modelo;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PreguntaVerdaderoOFalsoTest {

    @Test
    void testConstructorYDatosBasicos() {
        String[] opciones = {"Verdadero", "Falso", "No se sabe"};
        PreguntaVerdaderoOFalso p = new PreguntaVerdaderoOFalso("¿El agua moja?", opciones, 1);
        
        assertEquals("¿El agua moja?", p.getEnunciado());
        assertEquals(1, p.getIndiceCorrecta());
        assertEquals(3, p.getOpciones().size());
        assertEquals("Falso", p.getOpciones().get(1));
    }

    @Test
    void testGetTextoRespuestaCorrecta() {
        String[] opciones = {"Sí", "No"};
        PreguntaVerdaderoOFalso p = new PreguntaVerdaderoOFalso("¿Madrid es capital?", opciones, 0);
        
        assertEquals("Sí", p.getTextoRespuestaCorrecta());
    }

    @Test
    void testGetTextoRespuestaCorrectaIndiceInvalido() {
        String[] opciones = {"A", "B"};
        PreguntaVerdaderoOFalso p = new PreguntaVerdaderoOFalso("Test", opciones, 5);
        
        assertNull(p.getTextoRespuestaCorrecta(), "Debería devolver null si el índice está fuera de rango");
    }

    @Test
    void testSetters() {
        PreguntaVerdaderoOFalso p = new PreguntaVerdaderoOFalso();
        p.setEnunciado("Nuevo Enunciado");
        p.setOpciones(List.of("V", "F"));
        p.setIndiceCorrecta(0);
        
        assertEquals("Nuevo Enunciado", p.getEnunciado());
        assertEquals(2, p.getOpciones().size());
        assertEquals("V", p.getTextoRespuestaCorrecta());
    }
}