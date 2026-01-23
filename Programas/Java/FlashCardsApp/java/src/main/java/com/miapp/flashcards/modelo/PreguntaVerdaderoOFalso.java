package com.miapp.flashcards.modelo;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

@Entity
@Table(name = "preguntas_vf")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PreguntaVerdaderoOFalso extends Pregunta {

    private static final long serialVersionUID = 1L;

    @ElementCollection
    private List<String> opciones = new ArrayList<>();

    private int indiceCorrecta;

    public PreguntaVerdaderoOFalso() {
        super();
    }
    
    public PreguntaVerdaderoOFalso(String enunciado, List<String> opciones, int indiceCorrecta) {
        super(enunciado);
        this.opciones = opciones;
        this.indiceCorrecta = indiceCorrecta;
    }
    
    public PreguntaVerdaderoOFalso(String enunciado, String[] opcionesArray, int indiceCorrecta) {
        super(enunciado);
        this.opciones = new ArrayList<>(Arrays.asList(opcionesArray));
        this.indiceCorrecta = indiceCorrecta;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }

    public int getIndiceCorrecta() {
        return indiceCorrecta;
    }

    public void setIndiceCorrecta(int indiceCorrecta) {
        this.indiceCorrecta = indiceCorrecta;
    }
    
    public String getTextoRespuestaCorrecta() {
        if (opciones != null && indiceCorrecta >= 0 && indiceCorrecta < opciones.size()) {
            return opciones.get(indiceCorrecta);
        }
        return null;
    }
}