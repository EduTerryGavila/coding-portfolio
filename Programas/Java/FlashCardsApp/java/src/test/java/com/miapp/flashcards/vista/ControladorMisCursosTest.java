package com.miapp.flashcards.vista;

import com.miapp.flashcards.controlador.ControladorPrincipal;
import com.miapp.flashcards.modelo.Curso;
import com.miapp.flashcards.modelo.Creador;
import com.miapp.flashcards.persistencia.RepositorioCursos;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class ControladorMisCursosTest {

    private ControladorMisCursos controlador;
    private ListView<Curso> listaCursos;
    private Button btnEditar;

    @BeforeAll
    static void initJfx() {
        try { Platform.startup(() -> {}); } catch (IllegalStateException e) {}
    }

    @BeforeEach
    void setUp() throws Exception {
        controlador = new ControladorMisCursos();
        listaCursos = new ListView<>();
        btnEditar = new Button();

        inyectar(controlador, "listaCursos", listaCursos);
        inyectar(controlador, "btnEditar", btnEditar);
    }

    @Test
    void testCargarCursosCreador() {
        String email = "profe_miscursos_" + System.currentTimeMillis() + "@t.com";
        ControladorPrincipal.getInstance().registrarCreador("Profe Lista", email, "1");
        ControladorPrincipal.getInstance().iniciarSesion(email, "1");
        
        Creador creador = (Creador) ControladorPrincipal.getInstance().getUsuarioActual();
        Curso c1 = new Curso("Curso Test Lista", creador);
        RepositorioCursos.getInstance().guardarCurso(c1);

        runAndWait(() -> controlador.initialize());

        assertFalse(listaCursos.getItems().isEmpty());
        assertEquals("Curso Test Lista", listaCursos.getItems().get(0).getNombre());
        assertTrue(btnEditar.isVisible(), "El creador debe poder ver el botón editar");
    }
    
    @Test
    void testSeguridadEstudiante() {
        String email = "est_miscursos_" + System.currentTimeMillis() + "@t.com";
        ControladorPrincipal.getInstance().registrarEstudiante("Est", email, "1");
        ControladorPrincipal.getInstance().iniciarSesion(email, "1");

        runAndWait(() -> controlador.initialize());

        assertFalse(btnEditar.isVisible(), "El botón editar debe ocultarse a estudiantes");
    }

    private void inyectar(Object obj, String campo, Object valor) throws Exception {
        Field f = obj.getClass().getDeclaredField(campo);
        f.setAccessible(true);
        f.set(obj, valor);
    }

    private void runAndWait(Runnable action) {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> { action.run(); latch.countDown(); });
        try { latch.await(1, java.util.concurrent.TimeUnit.SECONDS); } catch (Exception e) {}
    }
}