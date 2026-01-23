package com.miapp.flashcards.persistencia;

import com.miapp.flashcards.modelo.Creador;
import com.miapp.flashcards.modelo.Curso;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepositorioCursosTest {

    @Test
    void testGuardarYListarCurso() {
        RepositorioCursos repo = RepositorioCursos.getInstance();
        
        Creador creador = new Creador("Profe Test", "profe@test.com", "123", 0);
        creador.setId(999L); 
        
        Curso curso = new Curso("Curso de Prueba JUnit", creador);
        
        repo.guardarCurso(curso);
        
        assertNotNull(curso.getId(), "El repositorio debe asignar un ID al guardar");
        
        boolean encontrado = false;
        for (Curso c : repo.listarTodos()) {
            if (c.getNombre().equals("Curso de Prueba JUnit")) {
                encontrado = true;
                break;
            }
        }
        assertTrue(encontrado, "El curso guardado debe aparecer en listarTodos");
    }

    @Test
    void testEliminarCurso() {
        RepositorioCursos repo = RepositorioCursos.getInstance();
        
        Curso c = new Curso("Curso para Borrar", new Creador());
        repo.guardarCurso(c);
        
        Long id = c.getId();
        assertNotNull(id);
        
        repo.eliminarCurso(c);
        
        List<Curso> lista = repo.listarTodos();
        boolean sigueAhi = lista.stream().anyMatch(curso -> curso.getId().equals(id));
        
        assertFalse(sigueAhi, "El curso eliminado no debería estar en la lista");
    }
    
    @Test
    void testListarPorCreador() {
        RepositorioCursos repo = RepositorioCursos.getInstance();
        
        Creador creadorA = new Creador();
        creadorA.setId(500L);
        
        Creador creadorB = new Creador();
        creadorB.setId(501L);
        
        Curso c1 = new Curso("Curso A1", creadorA);
        repo.guardarCurso(c1);
        
        Curso c2 = new Curso("Curso B1", creadorB);
        repo.guardarCurso(c2);
        
        List<Curso> cursosDeA = repo.listarPorCreador(creadorA);
        
        boolean contieneA1 = cursosDeA.stream().anyMatch(c -> c.getNombre().equals("Curso A1"));
        boolean contieneB1 = cursosDeA.stream().anyMatch(c -> c.getNombre().equals("Curso B1"));
        
        assertTrue(contieneA1, "Debería encontrar el curso del creador A");
        assertFalse(contieneB1, "No debería encontrar cursos de otros creadores");
    }
}