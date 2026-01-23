package com.miapp.flashcards.vista;

import com.miapp.flashcards.controlador.ControladorPrincipal;
import com.miapp.flashcards.estrategias.*;
import com.miapp.flashcards.modelo.*;
import com.miapp.flashcards.persistencia.RepositorioUsuarios;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class ControladorCursar {

    @FXML private Label lblNombreCurso;
    @FXML private Label lblProgreso;
    @FXML private Label lblEnunciado;
    @FXML private VBox panelVoF;
    @FXML private VBox panelTexto;
    @FXML private ToggleGroup grupoVoF;
    @FXML private RadioButton rbVerdadero;
    @FXML private RadioButton rbFalso;
    @FXML private RadioButton rbNoSe;
    @FXML private TextArea txtRespuesta;
    @FXML private Button btnAnterior;
    @FXML private Button btnSiguiente;

    private Curso cursoActual;
    private Estrategia estrategia;
    private Pregunta preguntaActual;
    private int aciertos = 0;
    private int totalRespondidas = 0;
    private Timeline timeline;
    private int tiempoRestante;

    //Carga la vista de estudio, configura el controlador con el curso y estrategia y gestiona la transicion de pantalla
    public static void abrirSesion(Stage stage, Curso curso, Estrategia estrategia) {
        try {
            FXMLLoader loader = new FXMLLoader(ControladorCursar.class.getResource("/pds/practicas/flashcards-app/VentanaCursar.fxml"));
            Parent vista = loader.load();

            ControladorCursar controlador = loader.getController();
            
            controlador.iniciarCursado(curso, estrategia);

            if (stage.getScene().getRoot() instanceof BorderPane) {
                BorderPane root = (BorderPane) stage.getScene().getRoot();
                if (root.getCenter() instanceof StackPane) {
                   StackPane area = (StackPane) root.getCenter();
                   area.getChildren().clear();
                   area.getChildren().add(vista);
                } else {
                   root.setCenter(vista);
                }
            } else {
                stage.getScene().setRoot(vista);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error crítico al cargar la vista de Cursar.");
        }
    }

    @FXML
    public void initialize() {
        if (btnAnterior != null) {
            btnAnterior.setVisible(false);
        }
    }

    //Inicializa las variables de la sesion, verifica si existe progreso guardado del estudiante y prepara la primera pregunta
    public void iniciarCursado(Curso curso, Estrategia estrategiaSeleccionada) {
        this.cursoActual = curso;
        this.estrategia = estrategiaSeleccionada;
        
        lblNombreCurso.setText(curso.getNombre());
        this.aciertos = 0;
        this.totalRespondidas = 0;

        Usuario u = ControladorPrincipal.getInstance().getUsuarioActual();
        boolean recuperado = false;

        if (u instanceof Estudiante) {
            Estudiante est = (Estudiante) u;
            if (est.getProgresosGuardados().containsKey(curso.getId())) {
                restaurarProgreso(est.getProgresosGuardados().get(curso.getId()), curso);
                recuperado = true;
            }
        }

        if (!recuperado) {
            this.estrategia.inicializar(curso.getPreguntas());
            cargarSiguientePregunta();
        }
    }
    
    //Recupera el estado anterior avanzando la estrategia hasta el punto donde se quedo el estudiante y carga la pregunta
    private void restaurarProgreso(ProgresoCurso progreso, Curso curso) {
         this.aciertos = progreso.getAciertos();
         this.totalRespondidas = progreso.getIndicePregunta();
        
        this.estrategia.inicializar(curso.getPreguntas());
        
        for (int i = 0; i < progreso.getIndicePregunta(); i++) {
            if (this.estrategia.quedanPreguntas()) {
                this.estrategia.siguientePregunta();
            }
        }
        cargarSiguientePregunta();
    }
    
    //Gestiona el flujo de preguntas, reinicia el temporizador si es necesario o finaliza la sesion si no quedan mas
    private void cargarSiguientePregunta() {
         if (timeline != null) {
            timeline.stop();
        }

        if (estrategia.quedanPreguntas()) {
            this.preguntaActual = estrategia.siguientePregunta();
            actualizarUI();
            
            int limite = estrategia.getTiempoLimite();
            if (limite > 0) {
                iniciarTimer(limite);
            } else {
                lblProgreso.setText("Pregunta #" + (totalRespondidas + 1));
                lblProgreso.setTextFill(Color.BLACK);
            }
            
            btnSiguiente.setDisable(false);
            
        } else {
            finalizarSesion(true);
        }
    }
    
    //Refresca la interfaz grafica mostrando el enunciado y configurando los paneles de respuesta segun el tipo de pregunta
    private void actualizarUI() {
        lblEnunciado.setText(preguntaActual.getEnunciado());
        grupoVoF.selectToggle(null);
        txtRespuesta.clear();

        if (preguntaActual instanceof PreguntaVerdaderoOFalso) {
            panelVoF.setVisible(true); 
            panelVoF.setManaged(true);
            panelTexto.setVisible(false); 
            panelTexto.setManaged(false);
            
            PreguntaVerdaderoOFalso pvof = (PreguntaVerdaderoOFalso) preguntaActual;
            List<String> opciones = pvof.getOpciones();
            
            if (opciones != null && opciones.size() >= 3) {
                rbVerdadero.setText(opciones.get(0));
                rbFalso.setText(opciones.get(1));
                
                if (rbNoSe != null) { 
                    rbNoSe.setText(opciones.get(2)); 
                }
            } else {
                rbVerdadero.setText("Verdadero");
                rbFalso.setText("Falso");
            }

        } else {
            panelVoF.setVisible(false); 
            panelVoF.setManaged(false);
            panelTexto.setVisible(true); 
            panelTexto.setManaged(true);
        }
    }
    
    //Configura y arranca un temporizador visual que cuenta hacia atras y avanza automaticamente si el tiempo se agota
    private void iniciarTimer(int segundos) {
         tiempoRestante = segundos;
        lblProgreso.setText("Tiempo: " + tiempoRestante + "s");
        lblProgreso.setTextFill(Color.GREEN);

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            tiempoRestante--;
            lblProgreso.setText("Tiempo: " + tiempoRestante + "s");
            if (tiempoRestante <= 5) {
                lblProgreso.setTextFill(Color.ORANGE);
            }
            if (tiempoRestante <= 0) {
                timeline.stop();
                procesarYAvanzar(false);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    
    @FXML
    void accionSiguiente(ActionEvent event) {
        procesarYAvanzar(evaluarRespuesta());
    }
    
    private void procesarYAvanzar(boolean esCorrecta) {
        if (esCorrecta) {
            aciertos++;
        }
        totalRespondidas++;
        estrategia.procesarRespuesta(esCorrecta);
        cargarSiguientePregunta();
    }

    //Comprueba si la respuesta seleccionada o escrita por el usuario coincide con la correcta segun el tipo de pregunta
    private boolean evaluarRespuesta() {
         if (preguntaActual == null) {
            return false;
        }
        if (preguntaActual instanceof PreguntaVerdaderoOFalso) {
            int sel = rbVerdadero.isSelected() ? 0 : (rbFalso.isSelected() ? 1 : -1);
            return sel == ((PreguntaVerdaderoOFalso) preguntaActual).getIndiceCorrecta();
        } 
        else {
            if (preguntaActual instanceof PreguntaRellenarHueco) {
                return txtRespuesta.getText().trim().equalsIgnoreCase(((PreguntaRellenarHueco) preguntaActual).getRespuestaCorrecta());
            }
            else {
                if (preguntaActual instanceof PreguntaAbierta) {
                    return txtRespuesta.getText().trim().equalsIgnoreCase(((PreguntaAbierta) preguntaActual).getRespuestaCorrecta());
                }
            }
        }
        return false;
    }
    
    //Actualiza las estadisticas del estudiante al terminar, borra el progreso temporal y muestra un resumen de resultados
    private void finalizarSesion(boolean completado) {
        if (timeline != null) {
            timeline.stop();
        }
        Usuario u = ControladorPrincipal.getInstance().getUsuarioActual();
        
        if (u instanceof Estudiante && completado) {
            Estudiante est = (Estudiante) u;
            est.setCursosCursados(est.getCursosCursados() + 1);
            est.setPreguntasRespondidas(est.getPreguntasRespondidas() + totalRespondidas);
            est.setPreguntasCorrectas(est.getPreguntasCorrectas() + aciertos);
            
            String nombreEstrat = (estrategia instanceof EstrategiaRepeticion) ? "Repetición" : 
                                  (estrategia instanceof EstrategiaPorTiempo) ? "Por Tiempo" : "Secuencial";
            est.setEstrategiaFavorita(nombreEstrat);
            
            est.getProgresosGuardados().remove(cursoActual.getId());
            RepositorioUsuarios.getInstance().guardarCambios();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("¡Completado!");
            alert.setHeaderText("Resultados");
            alert.setContentText("Aciertos: " + aciertos + " / " + totalRespondidas);
            alert.showAndWait();
        }
        ControladorPrincipal.getInstance().mostrarMenuPrincipal();
    }
    
    @FXML
    void accionAnterior(ActionEvent event) {
    }

    //Guarda el estado actual del progreso del estudiante en el repositorio para continuarlo mas tarde y vuelve al menu
    @FXML
    void accionGuardarSalir(ActionEvent event) {
         if (timeline != null) {
            timeline.stop();
        }
        Usuario u = ControladorPrincipal.getInstance().getUsuarioActual();
        if (u instanceof Estudiante) {
            Estudiante est = (Estudiante) u;
            String nombreEstrat = (estrategia instanceof EstrategiaRepeticion) ? "Repetición" : 
                                  (estrategia instanceof EstrategiaPorTiempo) ? "Por Tiempo" : "Secuencial";
            
            ProgresoCurso progreso = new ProgresoCurso(totalRespondidas, aciertos, nombreEstrat);
            est.getProgresosGuardados().put(cursoActual.getId(), progreso);
            RepositorioUsuarios.getInstance().guardarCambios();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Guardado");
            alert.setContentText("Progreso guardado.");
            alert.showAndWait();
        }
        ControladorPrincipal.getInstance().mostrarMenuPrincipal();
    }
}