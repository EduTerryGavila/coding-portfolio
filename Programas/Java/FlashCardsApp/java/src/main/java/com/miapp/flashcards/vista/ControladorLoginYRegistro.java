package com.miapp.flashcards.vista;

import com.miapp.flashcards.controlador.ControladorPrincipal;
import com.miapp.flashcards.modelo.Estudiante;
import com.miapp.flashcards.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorLoginYRegistro {

    @FXML private TextField txtLoginEmail;
    @FXML private PasswordField txtLoginPass;
    @FXML private RadioButton rbLoginEstudiante;
    @FXML private RadioButton rbLoginCreador;
    @FXML private ToggleGroup grupoLogin;

    @FXML private TextField txtRegNombre;
    @FXML private TextField txtRegEmail;
    @FXML private PasswordField txtRegPass;
    @FXML private RadioButton rbRegEstudiante;
    @FXML private RadioButton rbRegCreador;
    @FXML private ToggleGroup grupoRegistro;
    
    //Carga el archivo fxml de la ventana de acceso, configura la escena en el escenario principal y muestra la interfaz al usuario
    public static void abrir(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(ControladorLoginYRegistro.class.getResource("/pds/practicas/flashcards-app/VentanaLoginYRegistro.fxml"));
            Parent root = loader.load();
            
            if (stage.getScene() == null) {
                stage.setScene(new Scene(root));
            } else {
                stage.getScene().setRoot(root);
            }
            
            stage.setTitle("FlashCards - Acceso");
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error fatal al cargar la ventana de Login.");
        }
    }

    //Valida las credenciales ingresadas y comprueba que el rol seleccionado coincida con el del usuario para permitir el acceso al menu principal
    @FXML
    void accionLogin(ActionEvent event) {
        String email = txtLoginEmail.getText();
        String pass = txtLoginPass.getText();

        if (email.isEmpty() || pass.isEmpty()) {
            mostrarAlerta("Error", "Por favor rellene todos los campos");
            return;
        }

        boolean credencialesCorrectas = ControladorPrincipal.getInstance().iniciarSesion(email, pass);

        if (credencialesCorrectas) {
            Usuario usuario = ControladorPrincipal.getInstance().getUsuarioActual();
            boolean esEstudiante = usuario instanceof Estudiante;
            boolean seleccionoEstudiante = rbLoginEstudiante.isSelected();

            if ((esEstudiante && seleccionoEstudiante) || (!esEstudiante && !seleccionoEstudiante)) {
                ControladorPrincipal.getInstance().mostrarMenuPrincipal();
            } else {
                ControladorPrincipal.getInstance().cerrarSesion();
                mostrarAlerta("Error", "El usuario existe pero el rol seleccionado es incorrecto.");
            }
        } else {
            mostrarAlerta("Error", "Credenciales incorrectas o usuario no encontrado.");
        }
    }
    
    //Recoge los datos del formulario y llama al controlador principal para registrar un nuevo estudiante o creador segun la opcion seleccionada
    @FXML
    void accionRegistrar(ActionEvent event) {
         String nombre = txtRegNombre.getText();
        String email = txtRegEmail.getText();
        String pass = txtRegPass.getText();

        if (nombre.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            mostrarAlerta("Error", "Complete los campos obligatorios");
            return;
        }

        boolean exito;
        
        if (rbRegEstudiante.isSelected()) {
            exito = ControladorPrincipal.getInstance().registrarEstudiante(nombre, email, pass);
        } else {
            exito = ControladorPrincipal.getInstance().registrarCreador(nombre, email, pass);
        }

        if (exito) {
            mostrarAlerta("Éxito", "Cuenta creada correctamente. Ahora puede iniciar sesión.");
            limpiarRegistro();
        } else {
            mostrarAlerta("Error", "El email ya está registrado.");
        }
    }

    private void limpiarRegistro() {
         txtRegNombre.clear();
        txtRegEmail.clear();
        txtRegPass.clear();
        rbRegEstudiante.setSelected(true);
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}