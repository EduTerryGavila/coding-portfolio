package com.miapp.flashcards.vista;

import com.miapp.flashcards.controlador.ControladorPrincipal;
import com.miapp.flashcards.modelo.Creador;
import com.miapp.flashcards.modelo.Estudiante;
import com.miapp.flashcards.modelo.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.Map;

public class ControladorEstadisticas {
	
	@FXML private Pane cardRacha;
	@FXML private Pane cardPromedio;
	
    @FXML private Label lblTituloPrincipal;
    
    @FXML private Label lblDato1;
    @FXML private Label lblTitulo1;
    
    @FXML private Label lblDato2;
    @FXML private Label lblTitulo2;
    
    @FXML private Label lblDato3;
    @FXML private Label lblTitulo3;
    
    @FXML private Label lblDato4;
    @FXML private Label lblTitulo4; 
    
    @FXML private Label lblTiempoTotal;
    @FXML private Label lblFechaRegistro;

    @FXML
    public void initialize() {
        Usuario usuario = ControladorPrincipal.getInstance().getUsuarioActual();
        
        if (usuario == null) return;

        cargarDatosComunes(usuario);

        if (usuario instanceof Creador) {
            cargarDatosCreador();
        } else if (usuario instanceof Estudiante) {
            cargarDatosEstudiante((Estudiante) usuario);
        }
    }

    //Muestra la fecha de registro y calcula el tiempo total de uso formateandolo en horas y minutos
    private void cargarDatosComunes(Usuario u) {
        if (u.getFechaRegistro() != null && !u.getFechaRegistro().isEmpty()) {
            lblFechaRegistro.setText(u.getFechaRegistro());
        } else {
            lblFechaRegistro.setText("Desconocido");
        }

        long seg = u.getTiempoUsoSegundos();
        long horas = seg / 3600;
        long minutos = (seg % 3600) / 60;
        
        if (horas > 0) {
            lblTiempoTotal.setText(horas + "h " + minutos + "m");
        } else {
            lblTiempoTotal.setText(minutos + " min");
        }
    }

    //Configura la vista para el creador mostrando la cantidad de cursos, preguntas totales y el promedio por curso obtenidos del controlador principal
    private void cargarDatosCreador() {
        lblTituloPrincipal.setText("Estadísticas de Creador");
        
        lblTitulo1.setText("Cursos Creados");
        lblTitulo2.setText("Preguntas Totales");
        lblTitulo3.setText("Promedio");
        
        if (cardRacha != null) {
            cardRacha.setVisible(false);
            cardRacha.setManaged(false); 
        }
        
        lblTitulo4.setVisible(false); 
        lblTitulo4.setManaged(false);
        lblDato4.setVisible(false);
        lblDato4.setManaged(false);
        
        if (cardRacha != null && cardPromedio != null) {
            cardRacha.setVisible(false);
            cardRacha.setManaged(false);
            
            GridPane.setColumnSpan(cardPromedio, 2);
            
            cardPromedio.setMaxWidth(Double.MAX_VALUE); 
        }

        Map<String, Number> stats = ControladorPrincipal.getInstance().obtenerEstadisticasCreador();
        
        if (stats != null) {
            lblDato1.setText(String.valueOf(stats.get("cursos")));
            lblDato2.setText(String.valueOf(stats.get("preguntas")));
            
            double promedio = stats.get("promedio").doubleValue();
            lblDato3.setText(String.format("%.1f", promedio));
        }
    }

    //Configura la vista para el estudiante mostrando cursos completados, tasa de aciertos, ç
    //estrategia favorita con estilo personalizado y mejor racha de dias.
    private void cargarDatosEstudiante(Estudiante est) {
        lblTituloPrincipal.setText("Estadísticas de Estudiante");
        
        lblTitulo1.setText("Cursos Completados");
        lblTitulo2.setText("Preguntas (Resp. / Correctas)");
        lblTitulo3.setText("Estrategia Favorita");
        
        if (cardRacha != null) {
            cardRacha.setVisible(true);
            cardRacha.setManaged(true);
        }
        
        lblTitulo4.setVisible(true);
        lblTitulo4.setManaged(true);
        lblDato4.setVisible(true);
        lblDato4.setManaged(true);
        
        if (cardRacha != null && cardPromedio != null) {
            cardRacha.setVisible(true);
            cardRacha.setManaged(true);
            
            GridPane.setColumnSpan(cardPromedio, 1);
        }
        
        lblTitulo4.setText("Mejor Racha (Días)");

        lblDato1.setText(String.valueOf(est.getCursosCursados()));
        
        lblDato2.setText(est.getPreguntasRespondidas() + " / " + est.getPreguntasCorrectas());
        
        lblDato3.setText(est.getEstrategiaFavorita());
        lblDato3.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #EF6C00;");
        
        lblDato4.setText(String.valueOf(est.getMejorRacha()));
    }
}