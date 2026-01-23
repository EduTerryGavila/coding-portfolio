package com.miapp.flashcards.estrategias;

import com.miapp.flashcards.modelo.Pregunta;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EstrategiaSecuencial implements Estrategia {

    protected Queue<Pregunta> colaPreguntas;
    protected Pregunta preguntaActual;

    @Override
    public void inicializar(List<Pregunta> preguntas) {
        this.colaPreguntas = new LinkedList<>(preguntas);
    }

    @Override
    public Pregunta siguientePregunta() {
        this.preguntaActual = this.colaPreguntas.poll();
        return this.preguntaActual;
    }

    @Override
    public void procesarRespuesta(boolean esCorrecta) {
    }

    @Override
    public boolean quedanPreguntas() {
        return !this.colaPreguntas.isEmpty();
    }

    @Override
    public int getTiempoLimite() {
        return 0;
    }
}