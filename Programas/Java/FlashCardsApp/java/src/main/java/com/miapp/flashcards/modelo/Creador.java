package com.miapp.flashcards.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "creadores")
public class Creador extends Usuario {

    private static final long serialVersionUID = 1L;

    private int cursosCreados;

    public Creador() {
        super();
    }
    
    public Creador(String nombre, String email, String password) {
        super(nombre, email, password, java.time.LocalDate.now().toString(), 0);
    }

    public Creador(String nombre, String email, String password, int cursosCreados) {
        super(nombre, email, password);
        this.cursosCreados = cursosCreados;
    }

    public int getCursosCreados() {
        return cursosCreados;
    }

    public void setCursosCreados(int cursosCreados) {
        this.cursosCreados = cursosCreados;
    }
}