package com.miapp.flashcards.estrategias;

import com.miapp.flashcards.modelo.Pregunta;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EstrategiaRepeticion implements Estrategia {

    private List<Pregunta> colaPreguntas;
    private Pregunta preguntaActual;
    
    private Set<Pregunta> preguntasReintentadas;

    @Override
    public void inicializar(List<Pregunta> preguntas) {
        this.colaPreguntas = new ArrayList<>(preguntas);
        this.preguntasReintentadas = new HashSet<>();
    }

    @Override
    public Pregunta siguientePregunta() {
        if (!quedanPreguntas()) {
            return null;
        }
        this.preguntaActual = colaPreguntas.remove(0);
        return preguntaActual;
    }

    @Override
    public boolean quedanPreguntas() {
        return !colaPreguntas.isEmpty();
    }

    @Override
    public void procesarRespuesta(boolean esCorrecta) {
        if (!esCorrecta) {
            if (!preguntasReintentadas.contains(preguntaActual)) {
                
                colaPreguntas.add(preguntaActual);
                preguntasReintentadas.add(preguntaActual);
                
                System.out.println("Fallo registrado: La pregunta volverá a salir al final.");
            } else {
                System.out.println("Fallo en reintento: La pregunta se descarta definitivamente.");
            }
        }
    }

    @Override
    public int getTiempoLimite() {
        return 0;
    }
}