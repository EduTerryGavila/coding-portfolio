package com.miapp.flashcards.vista;

import com.miapp.flashcards.modelo.Pregunta;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ControladorCrearCursoTest {

    private ControladorCrearCurso controlador;
    
    private TextField txtEnunciado;
    private ComboBox<String> comboTipo;
    private TextField txtRespuesta;
    private ListView<String> listaVisual;
    private ArrayList<Pregunta> listaInterna;

    @BeforeAll
    static void initJfx() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {}
    }

    @BeforeEach
    void setUp() throws Exception {
        controlador = new ControladorCrearCurso();
        
        txtEnunciado = new TextField();
        comboTipo = new ComboBox<>();
        txtRespuesta = new TextField();
        listaVisual = new ListView<>();
        listaInterna = new ArrayList<>();
        
        inyectarCampo(controlador, "txtEnunciadoPregunta", txtEnunciado);
        inyectarCampo(controlador, "comboTipoPregunta", comboTipo);
        inyectarCampo(controlador, "txtRespuestaAbierta", txtRespuesta);
        inyectarCampo(controlador, "listaPreguntas", listaVisual);
        inyectarCampo(controlador, "preguntasTemporales", listaInterna);
        
        inyectarCampo(controlador, "preguntasVisuales", FXCollections.observableArrayList());
        listaVisual.setItems(FXCollections.observableArrayList());
    }

    @Test
    void testAnadirPreguntaAbierta() throws Exception {
        txtEnunciado.setText("¿Qué es Java?");
        txtRespuesta.setText("Un lenguaje");
        
        Platform.runLater(() -> comboTipo.setValue("Abierta"));
        waitForRunLater();

        Platform.runLater(() -> controlador.accionAnadirPregunta(null));
        waitForRunLater();

        assertFalse(listaInterna.isEmpty(), "La lista interna de preguntas no debe estar vacía");
        assertEquals("¿Qué es Java?", listaInterna.get(0).getEnunciado());
        
        Field fVisual = controlador.getClass().getDeclaredField("preguntasVisuales");
        fVisual.setAccessible(true);
        var visual = (java.util.List<?>) fVisual.get(controlador);
        assertFalse(visual.isEmpty(), "La lista visual debe actualizarse");
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