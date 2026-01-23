package com.miapp.flashcards.app; 

import com.miapp.flashcards.controlador.ControladorPrincipal;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pds/practicas/flashcards-app/VentanaLoginYRegistro.fxml"));
        
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Flashcards App");
        stage.setScene(scene);

        stage.setOnCloseRequest(event -> {
            System.out.println("Cerrando aplicación... Guardando estadísticas.");
            ControladorPrincipal.getInstance().cerrarSesion();
        });

        ControladorPrincipal.getInstance().setStage(stage); 

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}