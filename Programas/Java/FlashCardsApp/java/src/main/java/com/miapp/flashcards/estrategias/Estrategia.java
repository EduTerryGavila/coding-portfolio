package com.miapp.flashcards.estrategias;

import com.miapp.flashcards.modelo.Pregunta;
import java.util.List;

//Interfaz para las estrategias de aprendizaje a implementar
public interface Estrategia {
    void inicializar(List<Pregunta> preguntas);
    
    Pregunta siguientePregunta();
    
    void procesarRespuesta(boolean esCorrecta);
    
    boolean quedanPreguntas();
    
    int getTiempoLimite();
}