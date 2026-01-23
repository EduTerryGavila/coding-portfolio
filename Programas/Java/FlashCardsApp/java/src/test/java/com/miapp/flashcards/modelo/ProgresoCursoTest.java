package com.miapp.flashcards.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProgresoCursoTest {

    @Test
    void testConstructorVacioYSetters() {
        ProgresoCurso p = new ProgresoCurso();
        
        p.setIndicePregunta(5);
        p.setAciertos(3);
        p.setNombreEstrategia("Secuencial");
        
        assertEquals(5, p.getIndicePregunta());
        assertEquals(3, p.getAciertos());
        assertEquals("Secuencial", p.getNombreEstrategia());
    }

    @Test
    void testConstructorConParametros() {
        ProgresoCurso p = new ProgresoCurso(10, 8, "Repetición");
        
        assertEquals(10, p.getIndicePregunta());
        assertEquals(8, p.getAciertos());
        assertEquals("Repetición", p.getNombreEstrategia());
    }
    
    @Test
    void testValoresIniciales() {
        ProgresoCurso p = new ProgresoCurso();
        assertEquals(0, p.getIndicePregunta());
        assertEquals(0, p.getAciertos());
        assertNull(p.getNombreEstrategia());
    }
}