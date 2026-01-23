package com.miapp.flashcards.vista;

import com.miapp.flashcards.controlador.ControladorPrincipal;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class ControladorMenuPrincipalTest {

    private ControladorMenuPrincipal controlador;
    private Label lblUsuario;
    private Button btnCrearCurso;

    @BeforeAll
    static void initJfx() {
        try { Platform.startup(() -> {}); } catch (IllegalStateException e) {}
    }

    @BeforeEach
    void setUp() throws Exception {
        controlador = new ControladorMenuPrincipal();
        lblUsuario = new Label();
        btnCrearCurso = new Button();
        
        inyectar(controlador, "lblUsuario", lblUsuario);
        inyectar(controlador, "btnCrearCurso", btnCrearCurso);
    }

    @Test
    void testInicializacionEstudiante() {
        String email = "est_menu_" + System.currentTimeMillis() + "@test.com";
        ControladorPrincipal.getInstance().registrarEstudiante("Pepe", email, "1");
        ControladorPrincipal.getInstance().iniciarSesion(email, "1");

        runAndWait(() -> controlador.initialize());

        assertTrue(lblUsuario.getText().contains("Pepe"));
        assertFalse(btnCrearCurso.isVisible(), "El botón de crear curso debe ocultarse para estudiantes");
    }

    @Test
    void testInicializacionCreador() {
        String email = "cre_menu_" + System.currentTimeMillis() + "@test.com";
        ControladorPrincipal.getInstance().registrarCreador("Ana", email, "1");
        ControladorPrincipal.getInstance().iniciarSesion(email, "1");
        
        btnCrearCurso.setVisible(true);

        runAndWait(() -> controlador.initialize());

        assertTrue(lblUsuario.getText().contains("Ana"));
        assertTrue(btnCrearCurso.isVisible(), "El botón debe ser visible para creadores");
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