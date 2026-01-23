package com.miapp.flashcards.vista;

import java.io.IOException;

import com.miapp.flashcards.controlador.ControladorPrincipal;
import com.miapp.flashcards.modelo.Estudiante;
import com.miapp.flashcards.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ControladorMenuPrincipal {

    @FXML private Button btnEstadisticas;
    @FXML private Button btnBuscarCursos;
    @FXML private Button btnMisCursos;
    @FXML private Button btnCrearCurso;
    
    @FXML private Label lblUsuario;
    @FXML private StackPane areaContenido;

    //Carga el archivo fxml del menu principal, configura la escena y el titulo de la ventana y la muestra al usuario gestionando la navegacion
    public static void abrir(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(ControladorMenuPrincipal.class.getResource("/pds/practicas/flashcards-app/VentanaMenuPrincipal.fxml"));
            Parent root = loader.load();
            
            if (stage.getScene() == null) {
                stage.setScene(new Scene(root));
            } else {
                stage.getScene().setRoot(root);
            }
            
            stage.setTitle("FlashCards - Menú Principal");
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar el Menú Principal.");
        }
    }

    //Obtiene el usuario actual para mostrar su nombre en la interfaz y oculta el boton de crear curso si el usuario es un estudiante
    @FXML
    public void initialize() {
        Usuario usuarioActual = ControladorPrincipal.getInstance().getUsuarioActual();

        if (usuarioActual != null) {
            lblUsuario.setText("Usuario: " + usuarioActual.getNombre());

            if (usuarioActual instanceof Estudiante) {
                btnCrearCurso.setVisible(false);
                btnCrearCurso.setManaged(false);
            }
        } else {
            lblUsuario.setText("Usuario: Desconocido (Error de Sesión)");
        }
    }

    //Carga la vista de estadisticas desde el archivo fxml y la incrusta en el area de contenido principal reemplazando lo que hubiera antes
    @FXML
    void accionVerEstadisticas(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pds/practicas/flashcards-app/VentanaEstadisticas.fxml"));
            Parent vista = loader.load();
            
            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(vista);
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar Estadísticas.");
        }
    }

    @FXML
    void accionBuscarCursos(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pds/practicas/flashcards-app/VentanaBuscarCursos.fxml"));
            Parent vista = loader.load();
            
            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(vista);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void accionVerMisCursos(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pds/practicas/flashcards-app/VentanaMisCursos.fxml"));
            Parent vista = loader.load();
            
            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(vista);
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista de Mis Cursos.");
        }
    }

    //Cierra la sesion del usuario actual en el controlador principal y redirige a la ventana de inicio de sesion y registro
    @FXML
    void accionCerrarSesion(ActionEvent event) {
        ControladorPrincipal.getInstance().cerrarSesion();
        
        Stage stage = (Stage) btnEstadisticas.getScene().getWindow();
        
        ControladorLoginYRegistro.abrir(stage);
    }
    
    //Carga la vista de creacion de cursos y la muestra en el panel central permitiendo al creador diseñar nuevos contenidos.
    @FXML
    void accionCrearCurso(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pds/practicas/flashcards-app/VentanaCrearCurso.fxml"));
            Parent vistaCrearCurso = loader.load();
            
            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(vistaCrearCurso);
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista de crear curso.");
        }
    }
}