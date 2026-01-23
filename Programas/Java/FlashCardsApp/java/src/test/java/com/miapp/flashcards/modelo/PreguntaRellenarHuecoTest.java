package com.miapp.flashcards.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PreguntaRellenarHuecoTest {

    @Test
    void testConstructor() {
        PreguntaRellenarHueco p = new PreguntaRellenarHueco("En 1492 Colón descubrió ____", "América");
        
        assertNotNull(p);
        assertEquals("En 1492 Colón descubrió ____", p.getEnunciado());
        assertEquals("América", p.getRespuestaCorrecta());
    }

    @Test
    void testModificarRespuesta() {
        PreguntaRellenarHueco p = new PreguntaRellenarHueco();
        p.setEnunciado("La capital de Italia es ____");
        p.setRespuestaCorrecta("Roma");
        
        assertEquals("Roma", p.getRespuestaCorrecta());
        
        p.setRespuestaCorrecta("Milán");
        assertEquals("Milán", p.getRespuestaCorrecta());
    }
    
    @Test
    void testHerenciaPregunta() {
        PreguntaRellenarHueco p = new PreguntaRellenarHueco();
        p.setId(100L);
        
        assertEquals(100L, p.getId());
    }
}