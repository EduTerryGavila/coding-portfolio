package com.miapp.flashcards.estrategias;

import com.miapp.flashcards.modelo.Pregunta;
import com.miapp.flashcards.modelo.PreguntaAbierta; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class EstrategiasTest {

    private List<Pregunta> preguntasPrueba;

    @BeforeEach
    void setUp() {
        preguntasPrueba = new ArrayList<>();
        preguntasPrueba.add(new PreguntaAbierta("P1", "R1"));
        preguntasPrueba.add(new PreguntaAbierta("P2", "R2"));
        preguntasPrueba.add(new PreguntaAbierta("P3", "R3"));
    }

    @Test
    void testEstrategiaSecuencialOrden() {
        Estrategia e = new EstrategiaSecuencial();
        e.inicializar(preguntasPrueba);

        assertTrue(e.quedanPreguntas());
        
        assertEquals("P1", e.siguientePregunta().getEnunciado());
        assertEquals("P2", e.siguientePregunta().getEnunciado());
        assertEquals("P3", e.siguientePregunta().getEnunciado());
        
        assertFalse(e.quedanPreguntas(), "No deberían quedar preguntas");
    }

    @Test
    void testEstrategiaRepeticionFallo() {
        Estrategia e = new EstrategiaRepeticion();
        e.inicializar(preguntasPrueba);

        Pregunta p1 = e.siguientePregunta();
        assertEquals("P1", p1.getEnunciado());

        e.procesarRespuesta(false); 

        assertEquals("P2", e.siguientePregunta().getEnunciado());
        assertEquals("P3", e.siguientePregunta().getEnunciado());

        assertTrue(e.quedanPreguntas(), "La pregunta fallada debería volver a la cola");
        assertEquals("P1", e.siguientePregunta().getEnunciado());
    }
    
    @Test
    void testEstrategiaRepeticionAcierto() {
        Estrategia e = new EstrategiaRepeticion();
        e.inicializar(preguntasPrueba);

        Pregunta p1 = e.siguientePregunta();
        assertEquals("P1", p1.getEnunciado()); 
        
        e.procesarRespuesta(true); 

        e.siguientePregunta(); 
        e.siguientePregunta(); 
        
        assertFalse(e.quedanPreguntas(), "Si acertamos todo, no deben repetirse");
    }

    @Test
    void testEstrategiaPorTiempo() {
        Estrategia e = new EstrategiaPorTiempo();
        
        assertEquals(10, e.getTiempoLimite());
        
        e.inicializar(preguntasPrueba);
        assertEquals("P1", e.siguientePregunta().getEnunciado());
    }
}