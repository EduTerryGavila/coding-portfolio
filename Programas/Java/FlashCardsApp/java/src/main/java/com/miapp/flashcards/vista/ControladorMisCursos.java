package com.miapp.flashcards.vista;

import com.miapp.flashcards.controlador.ControladorPrincipal;
import com.miapp.flashcards.estrategias.*;
import com.miapp.flashcards.modelo.*;
import com.miapp.flashcards.persistencia.RepositorioUsuarios;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser; 

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ControladorMisCursos {

    @FXML private ListView<Curso> listaCursos;
    @FXML private Button btnEditar; 
    @FXML private Button btnCursar; 
    
    //Inicializa la vista cargando los cursos del usuario y configura las celdas de la lista para mostrar el nombre y numero de preguntas
    @FXML
    public void initialize() {
        cargarCursos();
        configurarSeguridad();

        listaCursos.setCellFactory(param -> new ListCell<Curso>() {
            @Override
            protected void updateItem(Curso curso, boolean empty) {
                super.updateItem(curso, empty);
                if (empty || curso == null) {
                    setText(null);
                } else {
                    setText(curso.getNombre() + " (" + curso.getPreguntas().size() + " preguntas)");
                }
            }
        });
    }
    
    //Oculta los botones que no corresponden al rol del usuario actual, impidiendo que el estudiante edite y que el creador estudie
    private void configurarSeguridad() {
        Usuario u = ControladorPrincipal.getInstance().getUsuarioActual();
        
        if (u instanceof Estudiante) {
            if (btnEditar != null) {
                btnEditar.setVisible(false);
                btnEditar.setManaged(false);
            }
        } 
        else if (u instanceof Creador) {
            if (btnCursar != null) {
                btnCursar.setVisible(false);
                btnCursar.setManaged(false);
            }
        }
    }
    
    private void cargarCursos() {
        listaCursos.setItems(FXCollections.observableArrayList(
            ControladorPrincipal.getInstance().obtenerCursosDelUsuarioActual()
        ));
    }

    //Verifica si el estudiante tiene progreso guardado en el curso ofreciendo continuarlo o reiniciar, y si es nuevo solicita elegir estrategia
    @FXML
    void accionCursar(ActionEvent event) {
        Usuario u = ControladorPrincipal.getInstance().getUsuarioActual();
        if (u instanceof Creador) return; 

        Curso seleccionado = listaCursos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selección", "Selecciona un curso para empezar a estudiar.");
            return;
        }

        if (u instanceof Estudiante) {
            Estudiante est = (Estudiante) u;
            if (est.getProgresosGuardados().containsKey(seleccionado.getId())) {
                
                ProgresoCurso progreso = est.getProgresosGuardados().get(seleccionado.getId());
                
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Curso Iniciado");
                alert.setHeaderText("Tienes un progreso guardado");
                alert.setContentText("Te quedaste en la pregunta " + (progreso.getIndicePregunta() + 1) + 
                                   ".\n¿Quieres continuar por donde ibas?");

                ButtonType btnContinuar = new ButtonType("Continuar");
                ButtonType btnReiniciar = new ButtonType("Reiniciar");
                ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
                
                alert.getButtonTypes().setAll(btnContinuar, btnReiniciar, btnCancelar);

                Optional<ButtonType> result = alert.showAndWait();
                
                if (result.isPresent()) {
                    if (result.get() == btnContinuar) {
                        Estrategia estrategiaGuardada = recuperarEstrategia(progreso.getNombreEstrategia());
                        ControladorPrincipal.getInstance().mostrarVentanaCursar(seleccionado, estrategiaGuardada);
                        return;
                    } else {
                        if (result.get() == btnReiniciar) {
                            est.getProgresosGuardados().remove(seleccionado.getId());
                            RepositorioUsuarios.getInstance().guardarCambios();
                        } else {
                            return;
                        }
                    }
                }
            }
        }

        mostrarSelectorDeEstrategia(seleccionado);
    }
    
    //Muestra un dialogo para que el usuario elija el modo de estudio entre las opciones disponibles e inicia la sesion con esa estrategia.
    private void mostrarSelectorDeEstrategia(Curso curso) {
        List<String> opciones = Arrays.asList("Secuencial (Normal)", "Repetición (Fallos vuelven)", "Por Tiempo (10s)");
        ChoiceDialog<String> dialogo = new ChoiceDialog<>("Secuencial (Normal)", opciones);
        dialogo.setTitle("Modo de Estudio");
        dialogo.setHeaderText("Configura tu sesión de estudio:");
        dialogo.setContentText("Estrategia:");

        Optional<String> resultado = dialogo.showAndWait();
        if (resultado.isPresent()) {
            Estrategia estrategia = recuperarEstrategia(resultado.get());
            ControladorPrincipal.getInstance().mostrarVentanaCursar(curso, estrategia);
        }
    }
    
    private Estrategia recuperarEstrategia(String nombre) {
        if (nombre.contains("Repetición")) {
            return new EstrategiaRepeticion();
        }
        if (nombre.contains("Tiempo")) {
            return new EstrategiaPorTiempo();
        }
        return new EstrategiaSecuencial();
    }

    //Abre un selector de archivos filtrando por JSON o YAML y delega la importacion al controlador principal mostrando el resultado
    @FXML
    void accionImportar(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importar Curso");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Todos los soportados", "*.json", "*.yaml", "*.yml"),
            new FileChooser.ExtensionFilter("Archivos JSON", "*.json"),
            new FileChooser.ExtensionFilter("Archivos YAML", "*.yaml", "*.yml")
        );
        File archivo = fileChooser.showOpenDialog(listaCursos.getScene().getWindow());
        if (archivo != null) {
            boolean exito = ControladorPrincipal.getInstance().importarCurso(archivo);
            if (exito) { 
                mostrarAlerta("Éxito", "Curso importado."); 
                cargarCursos(); 
            } else {
                mostrarAlerta("Error", "No se pudo importar.");
            }
        }
    }

    //Permite al usuario elegir donde guardar el curso seleccionado y genera un archivo con el formato elegido JSON o YAML
    @FXML
    void accionExportar(ActionEvent event) {
        Curso seleccionado = listaCursos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) { 
            mostrarAlerta("Selección", "Selecciona un curso."); 
            return; 
        }
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar Curso");
        fileChooser.setInitialFileName(seleccionado.getNombre().replaceAll("\\s+", "_")); 
        
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Archivo JSON (*.json)", "*.json"),
            new FileChooser.ExtensionFilter("Archivo YAML (*.yaml)", "*.yaml")
        );
        
        File archivo = fileChooser.showSaveDialog(listaCursos.getScene().getWindow());
        
        if (archivo != null) {
            boolean exito = ControladorPrincipal.getInstance().exportarCurso(seleccionado, archivo);
            
            if (exito) {
                mostrarAlerta("Éxito", "Curso exportado correctamente a " + archivo.getName());
            } else {
                mostrarAlerta("Error", "No se pudo exportar el curso. Revisa el formato.");
            }
        }
    }

    //Solicita confirmacion para eliminar el curso permanentemente si es creador o para desinscribirse si es estudiante y refresca la lista
    @FXML
    void accionEliminar(ActionEvent event) {
        Curso seleccionado = listaCursos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) { 
            mostrarAlerta("Selección", "Selecciona un curso."); 
            return; 
        }
        Usuario u = ControladorPrincipal.getInstance().getUsuarioActual();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setContentText("¿Eliminar/Desinscribirse?");
        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            if (u instanceof Estudiante) {
                ControladorPrincipal.getInstance().desinscribirEstudianteDeCurso(seleccionado);
            } else {
                ControladorPrincipal.getInstance().eliminarCurso(seleccionado);
            }
            cargarCursos();
        }
    }

    @FXML
    void accionEditar(ActionEvent event) {
        if (ControladorPrincipal.getInstance().getUsuarioActual() instanceof Estudiante) {
            return;
        }
        Curso sel = listaCursos.getSelectionModel().getSelectedItem();
        if (sel != null) {
            ControladorPrincipal.getInstance().mostrarEditarCurso(sel);
        }
    }
    
    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}