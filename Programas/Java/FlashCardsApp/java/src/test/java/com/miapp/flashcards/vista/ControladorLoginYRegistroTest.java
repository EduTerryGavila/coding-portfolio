package com.miapp.flashcards.vista;

import javafx.application.Platform;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ControladorLoginYRegistroTest {

    private ControladorLoginYRegistro controlador;
    private TextField txtLoginEmail;
    private PasswordField txtLoginPass;
    private RadioButton rbLoginEstudiante;

    @BeforeAll
    static void initJfx() {
        try { Platform.startup(() -> {}); } catch (IllegalStateException e) {}
    }

    @BeforeEach
    void setUp() throws Exception {
        controlador = new ControladorLoginYRegistro();
        txtLoginEmail = new TextField();
        txtLoginPass = new PasswordField();
        rbLoginEstudiante = new RadioButton();
        
        inyectar(controlador, "txtLoginEmail", txtLoginEmail);
        inyectar(controlador, "txtLoginPass", txtLoginPass);
        inyectar(controlador, "rbLoginEstudiante", rbLoginEstudiante);
    }

    @Test
    void testCamposSeInyectan() {
        assertNotNull(controlador);
        assertNotNull(txtLoginEmail);
    }
    
    private void inyectar(Object obj, String campo, Object valor) throws Exception {
        Field f = obj.getClass().getDeclaredField(campo);
        f.setAccessible(true);
        f.set(obj, valor);
    }
}