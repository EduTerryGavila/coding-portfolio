package com.miapp.flashcards.vista;

import com.miapp.flashcards.controlador.ControladorPrincipal;
import com.miapp.flashcards.modelo.Creador;
import com.miapp.flashcards.modelo.Curso;
import com.miapp.flashcards.modelo.FactoriaPreguntas;
import com.miapp.flashcards.modelo.Pregunta;
import com.miapp.flashcards.persistencia.RepositorioCursos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ControladorCrearCurso {

    @FXML private TextField txtNombreCurso;
    @FXML private TextField txtEnunciadoPregunta;
    @FXML private ComboBox<String> comboTipoPregunta;
    @FXML private VBox panelAbierta;
    @FXML private VBox panelOpciones;
    @FXML private TextField txtRespuestaAbierta;
    @FXML private TextField txtOp1, txtOp2, txtOp3;
    @FXML private RadioButton rbOp1, rbOp2, rbOp3;
    @FXML private ToggleGroup grupoOpciones;
    @FXML private ListView<String> listaPreguntas;
    
    private ArrayList<Pregunta> preguntasTemporales; 
    private ObservableList<String> preguntasVisuales;
    private Curso cursoEnEdicion = null;

    //Carga la vista de edicion, pasa el curso seleccionado al controlador y muestra la pantalla en la ventana principal gestionando el contenedor raiz
    public static void abrirParaEditar(Stage stage, Curso cursoAEditar) {
        try {
            FXMLLoader loader = new FXMLLoader(ControladorCrearCurso.class.getResource("/pds/practicas/flashcards-app/VentanaCrearCurso.fxml"));
            Parent vista = loader.load();

            ControladorCrearCurso controlador = loader.getController();
            
            controlador.cargarDatosParaEdicion(cursoAEditar);

            if (stage.getScene().getRoot() instanceof BorderPane) {
                BorderPane root = (BorderPane) stage.getScene().getRoot();
                if (root.getCenter() instanceof StackPane) {
                   StackPane area = (StackPane) root.getCenter();
                   area.getChildren().clear();
                   area.getChildren().add(vista);
                } else {
                   root.setCenter(vista);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista de edición.");
        }
    }

    //Inicializa las listas de preguntas y configura los elementos del desplegable de tipos de pregunta
    @FXML
    public void initialize() {
        preguntasTemporales = new ArrayList<>();
        preguntasVisuales = FXCollections.observableArrayList();
        listaPreguntas.setItems(preguntasVisuales);

        comboTipoPregunta.setItems(FXCollections.observableArrayList(
            "Abierta", 
            "Rellenar Hueco", 
            "Test (VOF)"
        ));
    }

    //Controla la visibilidad de los paneles de respuesta segun el tipo de pregunta seleccionado en el desplegable
    @FXML
    void alCambiarTipoPregunta(ActionEvent event) {
         String tipo = comboTipoPregunta.getValue();
        if (tipo == null) {
            return;
        }

        panelAbierta.setVisible(false); 
        panelAbierta.setManaged(false);
        panelOpciones.setVisible(false); 
        panelOpciones.setManaged(false);

        if (tipo.equals("Abierta") || tipo.equals("Rellenar Hueco")) {
            panelAbierta.setVisible(true);
            panelAbierta.setManaged(true);
        } 
        else {
            panelOpciones.setVisible(true);
            panelOpciones.setManaged(true);
        }
    }

    //Valida los campos de entrada, utiliza la factoria para crear la pregunta segun el tipo y la anade a la lista temporal visual
    @FXML
    void accionAnadirPregunta(ActionEvent event) {
         String enunciado = txtEnunciadoPregunta.getText();
        String tipo = comboTipoPregunta.getValue();

        if (enunciado.isEmpty() || tipo == null) {
            mostrarAlerta("Datos incompletos", "Falta el enunciado o el tipo de pregunta.");
            return;
        }

        Pregunta nuevaPregunta = null;

        if (tipo.equals("Abierta") || tipo.equals("Rellenar Hueco")) {
            String respuesta = txtRespuestaAbierta.getText();
            if (respuesta.isEmpty()) {
                mostrarAlerta("Error", "Debes escribir la respuesta correcta.");
                return;
            }
            nuevaPregunta = FactoriaPreguntas.crearPreguntaSimple(tipo, enunciado, respuesta);

        } else {
            if (txtOp1.getText().isEmpty() || txtOp2.getText().isEmpty() || txtOp3.getText().isEmpty()) {
                mostrarAlerta("Error", "Debes rellenar las 3 opciones.");
                return;
            }
            
            int indiceCorrecto = 0;
            if (rbOp2.isSelected()) {
                indiceCorrecto = 1;
            }
            if (rbOp3.isSelected()) {
                indiceCorrecto = 2;
            }
            
            String[] opciones = {txtOp1.getText(), txtOp2.getText(), txtOp3.getText()};

            nuevaPregunta = FactoriaPreguntas.crearPreguntaOpciones(tipo, enunciado, opciones, indiceCorrecto);
        }

        if (nuevaPregunta != null) {
            preguntasTemporales.add(nuevaPregunta);
            preguntasVisuales.add("[" + tipo + "] " + enunciado);
            limpiarCamposPregunta();
        } else {
            mostrarAlerta("Error", "No se pudo crear la pregunta. Verifica la Factoría.");
        }
    }
    
    //Verifica que haya nombre y preguntas, y guarda el curso nuevo o actualiza el existente en el repositorio asignandolo al creador actual
    @FXML
    void accionGuardarCurso(ActionEvent event) {
         String nombreCurso = txtNombreCurso.getText();

        if (nombreCurso.isEmpty()) {
            mostrarAlerta("Error", "El curso necesita un nombre.");
            return;
        }

        if (preguntasTemporales.isEmpty()) {
            mostrarAlerta("Error", "El curso debe tener al menos una pregunta.");
            return;
        }

        if (cursoEnEdicion != null) {
            cursoEnEdicion.setNombre(nombreCurso);
            cursoEnEdicion.setPreguntas(new ArrayList<>(preguntasTemporales));
            
            ControladorPrincipal.getInstance().guardarCurso(cursoEnEdicion);
            mostrarAlerta("Éxito", "Curso actualizado correctamente.");
        } 
        else {
            Creador creadorActual = (Creador) ControladorPrincipal.getInstance().getUsuarioActual();
            
            RepositorioCursos.getInstance().crearCurso(nombreCurso, creadorActual, new ArrayList<>(preguntasTemporales));
            
            mostrarAlerta("Éxito", "Curso creado y guardado.");
            
            limpiarTodoElFormulario();
        }
    }
    
    //Recibe el curso a editar, rellena el campo de nombre y carga las preguntas existentes en la lista visual.
    public void cargarDatosParaEdicion(Curso curso) {
         this.cursoEnEdicion = curso;
        
        txtNombreCurso.setText(curso.getNombre());
        
        this.preguntasTemporales = new ArrayList<>(curso.getPreguntas());
        
        preguntasVisuales.clear();
        for (Pregunta p : preguntasTemporales) {
            String tipo = p.getClass().getSimpleName().replace("Pregunta", "");
            preguntasVisuales.add("[" + tipo + "] " + p.getEnunciado());
        }
    }
    
    private void limpiarCamposPregunta() {
         txtEnunciadoPregunta.clear();
        txtRespuestaAbierta.clear();
        txtOp1.clear(); txtOp2.clear(); txtOp3.clear();
        
        if (rbOp1 != null) {
            rbOp1.setSelected(true);
        }
        
        txtEnunciadoPregunta.requestFocus();
    }
    
    private void limpiarTodoElFormulario() {
        txtNombreCurso.clear();
        preguntasTemporales.clear();
        preguntasVisuales.clear();
        limpiarCamposPregunta();
        cursoEnEdicion = null;
    }
    
    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
    
    @FXML
    void accionBorrarPregunta(ActionEvent event) {
        int indice = listaPreguntas.getSelectionModel().getSelectedIndex();

        if (indice >= 0) {
            preguntasTemporales.remove(indice);
            preguntasVisuales.remove(indice);
        } else {
            mostrarAlerta("Selección", "Selecciona una pregunta de la lista para borrar.");
        }
    }
    
    //Recupera la pregunta seleccionada, rellena el formulario segun el tipo de pregunta y la elimina de la lista para permitir su modificacion
    @FXML
    void accionEditarPregunta(ActionEvent event) {
         int indice = listaPreguntas.getSelectionModel().getSelectedIndex();

        if (indice < 0) {
            mostrarAlerta("Selección", "Selecciona una pregunta para editar.");
            return;
        }

        Pregunta p = preguntasTemporales.get(indice);

        txtEnunciadoPregunta.setText(p.getEnunciado());

        if (p instanceof com.miapp.flashcards.modelo.PreguntaAbierta) {
            comboTipoPregunta.setValue("Abierta");
            alCambiarTipoPregunta(null); 
            txtRespuestaAbierta.setText(((com.miapp.flashcards.modelo.PreguntaAbierta) p).getRespuestaCorrecta());

        } else if (p instanceof com.miapp.flashcards.modelo.PreguntaRellenarHueco) {
            comboTipoPregunta.setValue("Rellenar Hueco");
            alCambiarTipoPregunta(null);
            txtRespuestaAbierta.setText(((com.miapp.flashcards.modelo.PreguntaRellenarHueco) p).getRespuestaCorrecta());

        } else if (p instanceof com.miapp.flashcards.modelo.PreguntaVerdaderoOFalso) {
            comboTipoPregunta.setValue("Test (VOF)");
            alCambiarTipoPregunta(null);
            
            com.miapp.flashcards.modelo.PreguntaVerdaderoOFalso pvof = (com.miapp.flashcards.modelo.PreguntaVerdaderoOFalso) p;
            
            if (pvof.getOpciones().size() >= 3) {
                txtOp1.setText(pvof.getOpciones().get(0));
                txtOp2.setText(pvof.getOpciones().get(1));
                txtOp3.setText(pvof.getOpciones().get(2));
            }
            
            int correcta = pvof.getIndiceCorrecta();
            if (correcta == 0) {
                rbOp1.setSelected(true);
            } else if (correcta == 1) {
                rbOp2.setSelected(true);
            } else if (correcta == 2) {
                rbOp3.setSelected(true);
            }
        }

        preguntasTemporales.remove(indice);
        preguntasVisuales.remove(indice);
        
        txtEnunciadoPregunta.requestFocus();
    }
}