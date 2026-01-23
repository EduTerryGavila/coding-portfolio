package com.miapp.flashcards.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Curso implements Serializable {
    
    private static final long serialVersionUID = 1L;
	
    private Long id;
    private String nombre;
    private Creador creador;      
    private List<Pregunta> preguntas;

    public Curso() {
        this.preguntas = new ArrayList<>();
    }

    public Curso(String nombre, Creador creador) {
        this.nombre = nombre;
        this.creador = creador;
        this.preguntas = new ArrayList<>();
    }
    
    public Curso(String nombre, Creador creador, List<Pregunta> preguntas) {
        this.nombre = nombre;
        this.creador = creador;
        this.preguntas = preguntas;
    }

    public Long getId() { 
        return id; 
    }

    public void setId(Long id) { 
        this.id = id; 
    }

    public String getNombre() { 
        return nombre; 
    }

    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public Creador getCreador() { 
        return creador; 
    }

    public void setCreador(Creador creador) { 
        this.creador = creador; 
    }

    public List<Pregunta> getPreguntas() { 
        return preguntas; 
    }

    public void setPreguntas(List<Pregunta> preguntas) { 
        this.preguntas = preguntas; 
    }
}