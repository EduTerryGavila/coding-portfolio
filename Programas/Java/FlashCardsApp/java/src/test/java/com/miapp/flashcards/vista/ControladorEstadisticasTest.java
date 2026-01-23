package com.miapp.flashcards.vista;

import com.miapp.flashcards.controlador.ControladorPrincipal;
import com.miapp.flashcards.modelo.Estudiante;
import javafx.application.Platform;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ControladorEstadisticasTest {

    private ControladorEstadisticas controlador;
    
    private Label lblTituloPrincipal, lblDato1, lblDato2, lblDato3, lblDato4;
    private Label lblTitulo1, lblTitulo2, lblTitulo3, lblTitulo4;
    private Label lblTiempoTotal, lblFechaRegistro;

    @BeforeAll
    static void initJfx() {
        try { Platform.startup(() -> {}); } catch (IllegalStateException e) {}
    }

    @BeforeEach
    void setUp() throws Exception {
        controlador = new ControladorEstadisticas();
        
        lblTituloPrincipal = new Label();
        lblDato1 = new Label(); lblDato2 = new Label(); lblDato3 = new Label(); lblDato4 = new Label();
        lblTitulo1 = new Label(); lblTitulo2 = new Label(); lblTitulo3 = new Label(); lblTitulo4 = new Label();
        lblTiempoTotal = new Label(); lblFechaRegistro = new Label();

        inyectar(controlador, "lblTituloPrincipal", lblTituloPrincipal);
        inyectar(controlador, "lblDato1", lblDato1); inyectar(controlador, "lblTitulo1", lblTitulo1);
        inyectar(controlador, "lblDato2", lblDato2); inyectar(controlador, "lblTitulo2", lblTitulo2);
        inyectar(controlador, "lblDato3", lblDato3); inyectar(controlador, "lblTitulo3", lblTitulo3);
        inyectar(controlador, "lblDato4", lblDato4); inyectar(controlador, "lblTitulo4", lblTitulo4);
        inyectar(controlador, "lblTiempoTotal", lblTiempoTotal);
        inyectar(controlador, "lblFechaRegistro", lblFechaRegistro);
    }

    @Test
    void testCargarDatosEstudiante() {
        Estudiante est = new Estudiante("Alumno Test", "a@test.com", "123", 0);
        est.setCursosCursados(5);
        est.setMejorRacha(10);
        est.setFechaRegistro("2026-01-01");
        
        ControladorPrincipal.getInstance().cerrarSesion();
        ControladorPrincipal.getInstance().registrarEstudiante("Alumno Test", "a@test.com", "123");
        ControladorPrincipal.getInstance().iniciarSesion("a@test.com", "123");
        
        Estudiante logueado = (Estudiante) ControladorPrincipal.getInstance().getUsuarioActual();
        logueado.setCursosCursados(5);
        logueado.setMejorRacha(10);
        logueado.setFechaRegistro("2026-01-01");

        runAndWait(() -> controlador.initialize());

        assertEquals("Estadísticas de Estudiante", lblTituloPrincipal.getText());
        assertEquals("5", lblDato1.getText(), "Debe mostrar 5 cursos cursados");
        assertEquals("10", lblDato4.getText(), "Debe mostrar 10 de racha");
        assertEquals("2026-01-01", lblFechaRegistro.getText());
    }

    @Test
    void testCargarDatosCreador() {
        String email = "profe_" + System.currentTimeMillis() + "@test.com";
        ControladorPrincipal.getInstance().registrarCreador("Profe", email, "123");
        ControladorPrincipal.getInstance().iniciarSesion(email, "123");

        runAndWait(() -> controlador.initialize());

        assertEquals("Estadísticas de Creador", lblTituloPrincipal.getText());
        assertEquals("-", lblDato4.getText(), "El creador no tiene racha");
    }

    private void inyectar(Object obj, String campo, Object valor) throws Exception {
        Field f = obj.getClass().getDeclaredField(campo);
        f.setAccessible(true);
        f.set(obj, valor);
    }

    private void runAndWait(Runnable action) {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            action.run();
            latch.countDown();
        });
        try { latch.await(2, TimeUnit.SECONDS); } catch (InterruptedException e) {}
    }
}