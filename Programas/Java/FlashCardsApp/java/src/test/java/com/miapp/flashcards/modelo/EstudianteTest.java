package com.miapp.flashcards.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EstudianteTest {

    @Test
    void testConstructorYGetters() {
        Estudiante est = new Estudiante("Ana", "ana@test.com", "123", 5);
        
        assertEquals("Ana", est.getNombre());
        assertEquals("ana@test.com", est.getEmail());
        
        assertEquals(5, est.getCursosCursados());
        assertEquals(0, est.getRachaActual(), "La racha debe iniciar en 0");
    }

    @Test
    void testGestionDeRachas() {
        Estudiante est = new Estudiante();
        
        est.setRachaActual(10);
        est.setMejorRacha(15);
        est.setFechaUltimaActividad("2026-01-01");
        
        assertEquals(10, est.getRachaActual());
        assertEquals(15, est.getMejorRacha());
        assertEquals("2026-01-01", est.getFechaUltimaActividad());
    }

    @Test
    void testGestionDeCursosSuscritos() {
        Estudiante est = new Estudiante();
        Long cursoId = 101L;
        
        est.getCursosSuscritosIds().add(cursoId);
        
        assertTrue(est.getCursosSuscritosIds().contains(cursoId), "El estudiante debería estar suscrito al curso 101");
        assertEquals(1, est.getCursosSuscritosIds().size());
    }

    @Test
    void testGuardarProgresoCurso() {
        Estudiante est = new Estudiante();
        Long cursoId = 50L;
        
        ProgresoCurso progreso = new ProgresoCurso(5, 4, "Secuencial");
        
        est.getProgresosGuardados().put(cursoId, progreso);
        
        assertTrue(est.getProgresosGuardados().containsKey(cursoId));
        ProgresoCurso recuperado = est.getProgresosGuardados().get(cursoId);
        
        assertEquals(5, recuperado.getIndicePregunta());
        assertEquals("Secuencial", recuperado.getNombreEstrategia());
    }

    @Test
    void testSumarTiempoUso() {
        Estudiante est = new Estudiante();
        est.setTiempoUsoSegundos(100);
        
        est.sumarTiempo(50);
        
        assertEquals(150, est.getTiempoUsoSegundos());
    }
}