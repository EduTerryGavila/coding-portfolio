package com.miapp.flashcards.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "estudiantes")
public class Estudiante extends Usuario {

    private static final long serialVersionUID = 1L;

    @Column(name = "cursos_cursados")
    private int cursosCursados;

    @Column(name = "preguntas_respondidas")
    private int preguntasRespondidas;

    @Column(name = "preguntas_correctas")
    private int preguntasCorrectas;

    @Column(name = "estrategia_favorita")
    private String estrategiaFavorita;
    
    @Column(name = "racha_actual")
    private int rachaActual;

    @Column(name = "mejor_racha")
    private int mejorRacha;

    @Column(name = "fecha_ultima_actividad")
    private String fechaUltimaActividad;
    
    @ElementCollection
    private Set<Long> cursosSuscritosIds;
    
    @ElementCollection
    private Map<Long, ProgresoCurso> progresosGuardados;

    public Estudiante() {
        super();
        this.cursosCursados = 0;
        this.preguntasRespondidas = 0;
        this.preguntasCorrectas = 0;
        this.estrategiaFavorita = "Ninguna";
        
        this.rachaActual = 0;
        this.mejorRacha = 0;
        this.fechaUltimaActividad = "";
        
        this.cursosSuscritosIds = new HashSet<>();
        this.progresosGuardados = new HashMap<>();
    }

    public Estudiante(String nombre, String email, String password, int cursosCursados) {
        super(nombre, email, password);
        this.cursosCursados = cursosCursados;
        this.preguntasRespondidas = 0;
        this.preguntasCorrectas = 0;
        this.estrategiaFavorita = "Ninguna";
        this.rachaActual = 0;
        this.mejorRacha = 0;
        this.fechaUltimaActividad = "";
        this.cursosSuscritosIds = new HashSet<>();
        this.progresosGuardados = new HashMap<>();
    }
    
    public Estudiante(String nombre, String email, String password) {
    	super(nombre, email, password, LocalDate.now().toString(), 0);
        this.rachaActual = 1;
        this.mejorRacha = 1;
        this.fechaUltimaActividad = java.time.LocalDate.now().toString();
    }

    public int getCursosCursados() { 
        return cursosCursados; 
    }

    public void setCursosCursados(int cursosCursados) { 
        this.cursosCursados = cursosCursados; 
    }

    public int getPreguntasRespondidas() { 
        return preguntasRespondidas; 
    }

    public void setPreguntasRespondidas(int preguntasRespondidas) { 
        this.preguntasRespondidas = preguntasRespondidas; 
    }

    public int getPreguntasCorrectas() { 
        return preguntasCorrectas; 
    }

    public void setPreguntasCorrectas(int preguntasCorrectas) { 
        this.preguntasCorrectas = preguntasCorrectas; 
    }

    public String getEstrategiaFavorita() { 
        return estrategiaFavorita; 
    }

    public void setEstrategiaFavorita(String estrategiaFavorita) { 
        this.estrategiaFavorita = estrategiaFavorita; 
    }
    
    public int getRachaActual() { 
        return rachaActual; 
    }

    public void setRachaActual(int rachaActual) { 
        this.rachaActual = rachaActual; 
    }

    public int getMejorRacha() { 
        return mejorRacha; 
    }

    public void setMejorRacha(int mejorRacha) { 
        this.mejorRacha = mejorRacha; 
    }

    public String getFechaUltimaActividad() { 
        return fechaUltimaActividad; 
    }

    public void setFechaUltimaActividad(String fechaUltimaActividad) { 
        this.fechaUltimaActividad = fechaUltimaActividad; 
    }

    public Set<Long> getCursosSuscritosIds() {
        if (cursosSuscritosIds == null) {
            cursosSuscritosIds = new HashSet<>();
        }
        return cursosSuscritosIds;
    }

    public void setCursosSuscritosIds(Set<Long> cursosSuscritosIds) { 
        this.cursosSuscritosIds = cursosSuscritosIds; 
    }

    public Map<Long, ProgresoCurso> getProgresosGuardados() {
        if (progresosGuardados == null) {
            progresosGuardados = new HashMap<>();
        }
        return progresosGuardados;
    }

    public void setProgresosGuardados(Map<Long, ProgresoCurso> progresosGuardados) {
        this.progresosGuardados = progresosGuardados;
    }
}