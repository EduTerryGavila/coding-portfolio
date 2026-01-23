package com.miapp.flashcards.vista;

import com.miapp.flashcards.modelo.Curso;
import com.miapp.flashcards.modelo.Creador;
import com.miapp.flashcards.persistencia.RepositorioCursos;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ControladorBuscarCursosTest {

    private ControladorBuscarCursos controlador;
    private TextField txtBusquedaFake;
    private ListView<Curso> listaResultadosFake;

    @BeforeAll
    static void initJfx() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(() -> latch.countDown());
        } catch (IllegalStateException e) {
            latch.countDown();
        }
        latch.await(5, TimeUnit.SECONDS);
    }

    @BeforeEach
    void setUp() throws Exception {
        controlador = new ControladorBuscarCursos();
        
        txtBusquedaFake = new TextField();
        listaResultadosFake = new ListView<>();

        inyectarCampo(controlador, "txtBusqueda", txtBusquedaFake);
        inyectarCampo(controlador, "listaResultados", listaResultadosFake);
    }

    @Test
    void testBuscarCursoExistente() {
        Creador creador = new Creador("Profe", "p@p.com", "123", 0);
        creador.setId(1L);
        Curso c = new Curso("Curso Busqueda Java", creador);
        RepositorioCursos.getInstance().guardarCurso(c);

        txtBusquedaFake.setText("Busqueda");

        Platform.runLater(() -> {
            controlador.accionBuscar(null);
        });
        waitForRunLater();

        assertFalse(listaResultadosFake.getItems().isEmpty(), "La lista debería tener resultados");
        assertEquals("Curso Busqueda Java", listaResultadosFake.getItems().get(0).getNombre());
    }

    @Test
    void testBuscarCursoInexistente() {
        txtBusquedaFake.setText("PalabraQueNoExisteXYZ");

        Platform.runLater(() -> controlador.accionBuscar(null));
        waitForRunLater();

        assertTrue(listaResultadosFake.getItems().isEmpty(), "La lista debería estar vacía");
    }

    private void inyectarCampo(Object objeto, String nombreCampo, Object valor) throws Exception {
        Field field = objeto.getClass().getDeclaredField(nombreCampo);
        field.setAccessible(true);
        field.set(objeto, valor);
    }

    private void waitForRunLater() {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(latch::countDown);
            latch.await(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}