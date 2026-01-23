package com.miapp.flashcards.vista;

import com.miapp.flashcards.controlador.ControladorPrincipal;
import com.miapp.flashcards.modelo.Creador;
import com.miapp.flashcards.modelo.Curso;
import com.miapp.flashcards.modelo.Estudiante;
import com.miapp.flashcards.modelo.Usuario;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser; 

import java.io.File;
import java.util.List;

public class ControladorBuscarCursos {

    @FXML private TextField txtBusqueda;
    @FXML private ListView<Curso> listaResultados;

    //Configura la visualizacion de la lista personalizando las celdas para mostrar nombre y autor, y ejecuta una busqueda inicial vacia
    @FXML
    public void initialize() {
        listaResultados.setCellFactory(param -> new ListCell<Curso>() {
            @Override
            protected void updateItem(Curso curso, boolean empty) {
                super.updateItem(curso, empty);
                if (empty || curso == null) {
                    setText(null);
                } else {
                    String nombreCreador = (curso.getCreador() != null) ? curso.getCreador().getNombre() : "Desconocido";
                    setText(curso.getNombre() + " - Autor: " + nombreCreador);
                }
            }
        });
        accionBuscar(null);
    }

    @FXML
    void accionBuscar(ActionEvent event) {
        String texto = txtBusqueda.getText();
        List<Curso> encontrados = ControladorPrincipal.getInstance().buscarCursosDisponibles(texto);
        listaResultados.setItems(FXCollections.observableArrayList(encontrados));
        
        if (encontrados.isEmpty()) {
            listaResultados.setPlaceholder(new javafx.scene.control.Label("No se encontraron cursos."));
        }
    }

    //Gestiona el boton Añadir. Si es estudiante lo inscribe para estudiar y si es creador, clona el curso para crear una copia editable propia
    @FXML
    void accionAnadir(ActionEvent event) {
        Curso seleccionado = listaResultados.getSelectionModel().getSelectedItem();
        
        if (seleccionado == null) {
            mostrarAlerta("Selección", "Selecciona un curso para añadir.");
            return;
        }

        Usuario u = ControladorPrincipal.getInstance().getUsuarioActual();

        if (u instanceof Estudiante) {
            boolean exito = ControladorPrincipal.getInstance().inscribirEstudianteEnCurso(seleccionado);
            if (exito) {
                mostrarAlerta("Inscripción", "Curso añadido a 'Mis Cursos'. ¡A estudiar!");
            } else {
                mostrarAlerta("Aviso", "No se pudo añadir (quizás ya lo tienes).");
            }
        } 
        else {
            if (u instanceof Creador) {
                ControladorPrincipal.getInstance().clonarCurso(seleccionado);
                mostrarAlerta("Éxito", "Curso clonado. Tienes una copia editable en 'Mis Cursos'.");
            }
        }
    }
    
    //Gestiona el boton Exportar, abre un dialogo para que el usuario elija ubicacion y delega la exportacion al controlador principal
    @FXML
    void accionExportar(ActionEvent event) {
        Curso seleccionado = listaResultados.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selección", "Selecciona un curso para exportar.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar Curso");
        String nombreSeguro = seleccionado.getNombre().replaceAll("\\s+", "_");
        fileChooser.setInitialFileName(nombreSeguro);
        
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Archivo JSON (*.json)", "*.json"),
            new FileChooser.ExtensionFilter("Archivo YAML (*.yaml)", "*.yaml")
        );

        File archivoDestino = fileChooser.showSaveDialog(listaResultados.getScene().getWindow());

        if (archivoDestino != null) {
            boolean exito = ControladorPrincipal.getInstance().exportarCurso(seleccionado, archivoDestino);
            if (exito) {
                mostrarAlerta("Éxito", "Curso exportado correctamente.");
            } else {
                mostrarAlerta("Error", "No se pudo exportar.");
            }
        }
    }
    
    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}