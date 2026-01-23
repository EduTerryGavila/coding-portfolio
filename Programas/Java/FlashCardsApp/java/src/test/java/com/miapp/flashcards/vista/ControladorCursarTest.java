package com.miapp.flashcards.vista;

import com.miapp.flashcards.controlador.ControladorPrincipal;
import com.miapp.flashcards.estrategias.EstrategiaSecuencial;
import com.miapp.flashcards.modelo.*;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ControladorCursarTest {

    private ControladorCursar controlador;
    private Label lblEnunciado;
    private Label lblNombreCurso;
    private Label lblProgreso;
    private TextArea txtRespuesta;
    private VBox panelVoF, panelTexto;
    private Button btnSiguiente;

    @BeforeAll
    static void initJfx() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {}
    }

    @BeforeEach
    void setUp() throws Exception {
        controlador = new ControladorCursar();
        
        lblEnunciado = new Label();
        lblNombreCurso = new Label();
        lblProgreso = new Label();
        txtRespuesta = new TextArea();
        panelVoF = new VBox();
        panelTexto = new VBox();
        btnSiguiente = new Button();
        
        inyectarCampo(controlador, "lblEnunciado", lblEnunciado);
        inyectarCampo(controlador, "lblNombreCurso", lblNombreCurso);
        inyectarCampo(controlador, "lblProgreso", lblProgreso);
        inyectarCampo(controlador, "txtRespuesta", txtRespuesta);
        inyectarCampo(controlador, "panelVoF", panelVoF);
        inyectarCampo(controlador, "panelTexto", panelTexto);
        inyectarCampo(controlador, "btnSiguiente", btnSiguiente);
        inyectarCampo(controlador, "grupoVoF", new ToggleGroup());
    }

    @Test
    void testIniciarCursado() {
        Curso c = new Curso("Curso Test", new Creador());
        ArrayList<Pregunta> preguntas = new ArrayList<>();
        preguntas.add(new PreguntaAbierta("P1", "R1"));
        c.setPreguntas(preguntas);
        
        @SuppressWarnings("unused")
		Estudiante est = new Estudiante("Est", "e@e.com", "1", 0);
        ControladorPrincipal.getInstance().registrarEstudiante("Est", "e@e.com", "1");
        ControladorPrincipal.getInstance().iniciarSesion("e@e.com", "1");

        Platform.runLater(() -> {
            controlador.iniciarCursado(c, new EstrategiaSecuencial());
        });
        waitForRunLater();

        assertEquals("Curso Test", lblNombreCurso.getText());
        assertEquals("P1", lblEnunciado.getText());
        assertTrue(panelTexto.isVisible(), "El panel de texto debería ser visible para preguntas abiertas");
    }

    private void inyectarCampo(Object objeto, String nombreCampo, Object valor) throws Exception {
        Field field = objeto.getClass().getDeclaredField(nombreCampo);
        field.setAccessible(true);
        field.set(objeto, valor);
    }
    
    private void waitForRunLater() {
        try {
            java.util.concurrent.CountDownLatch latch = new java.util.concurrent.CountDownLatch(1);
            Platform.runLater(latch::countDown);
            latch.await(1, java.util.concurrent.TimeUnit.SECONDS);
        } catch (Exception e) {}
    }
}