package com.miapp.flashcards.modelo;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@MappedSuperclass 
public abstract class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
    
    @Column(name = "fecha_registro")
    private String fechaRegistro;

    @Column(name = "tiempo_uso")
    private long tiempoUsoSegundos;

    public Usuario() {
    }

    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }
    
    
    public Usuario(String nombre, String email, String password, String fechaRegistro, int tiempoUsoSegundos) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.fechaRegistro = fechaRegistro;
        this.tiempoUsoSegundos = tiempoUsoSegundos;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public long getTiempoUsoSegundos() {
        return tiempoUsoSegundos;
    }

    public void setTiempoUsoSegundos(long tiempoUsoSegundos) {
        this.tiempoUsoSegundos = tiempoUsoSegundos;
    }
    
    public void sumarTiempo(long segundos) {
        this.tiempoUsoSegundos += segundos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}