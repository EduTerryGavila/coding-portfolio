package com.miapp.flashcards.persistencia;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.miapp.flashcards.modelo.Creador;
import com.miapp.flashcards.modelo.Curso;
import com.miapp.flashcards.modelo.Pregunta;
import com.miapp.flashcards.modelo.Usuario;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioCursos {

    private static RepositorioCursos instance;
    private static final String RUTA_ARCHIVO = "cursos.json";
    
    private ObjectMapper mapper;
    private List<Curso> cacheCursos;

    private RepositorioCursos() {
        this.mapper = new ObjectMapper();
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.mapper.activateDefaultTyping(
            mapper.getPolymorphicTypeValidator(), 
            ObjectMapper.DefaultTyping.NON_FINAL, 
            com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY
        );

        this.cacheCursos = new ArrayList<>();
        cargarDesdeFichero();
    }

    public static RepositorioCursos getInstance() {
        if (instance == null) {
            instance = new RepositorioCursos();
        }
        return instance;
    }

    public void crearCurso(String nombre, Usuario usuarioActual, ArrayList<Pregunta> preguntas) {
        if (usuarioActual instanceof Creador) {
            Curso nuevoCurso = new Curso(nombre, (Creador) usuarioActual, preguntas);
            this.guardarCurso(nuevoCurso);
        } else {
            System.err.println("Error: El usuario actual no es un Creador.");
        }
    }

    public void guardarCurso(Curso curso) {
        if (curso.getId() == null || curso.getId() == 0) {
            // NUEVO CURSO
            curso.setId(generarNuevoId());
            cacheCursos.add(curso);
            System.out.println("Guardando nuevo curso: " + curso.getNombre() + " (ID: " + curso.getId() + ")");
        } else {
            boolean encontrado = false;
            for (int i = 0; i < cacheCursos.size(); i++) {
                if (cacheCursos.get(i).getId().equals(curso.getId())) {
                    cacheCursos.set(i, curso);
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                cacheCursos.add(curso);
            }
            System.out.println("Actualizando curso: " + curso.getNombre() + " (ID: " + curso.getId() + ")");
        }
        guardarEnFichero();
    }

    public List<Curso> listarTodos() {
        return new ArrayList<>(cacheCursos);
    }

    public List<Curso> listarPorCreador(Usuario creador) {
        return cacheCursos.stream()
                .filter(c -> c.getCreador() != null 
                          && c.getCreador().getId() != null
                          && creador.getId() != null
                          && c.getCreador().getId().equals(creador.getId()))
                .collect(Collectors.toList());
    }

    public void eliminarCurso(Curso curso) {
        cacheCursos.removeIf(c -> c.getId().equals(curso.getId()));
        guardarEnFichero();
    }

    private void guardarEnFichero() {
        try {
            File archivoTemp = new File(RUTA_ARCHIVO + ".tmp");
            mapper.writeValue(archivoTemp, cacheCursos);

            File archivoReal = new File(RUTA_ARCHIVO);
            if (archivoReal.exists()) {
                if (!archivoReal.delete()) {
                    System.err.println("Advertencia: No se pudo borrar el archivo original. Intentando sobrescribir...");
                }
            }
            
            if (!archivoTemp.renameTo(archivoReal)) {
                throw new IOException("No se pudo renombrar el archivo temporal a " + RUTA_ARCHIVO + ". Comprueba permisos o bloqueos.");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("¡ERROR GRAVE! Fallo al guardar en JSON: " + e.getMessage());
        }
    }

    private void cargarDesdeFichero() {
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            return;
        }

        try {
            List<Curso> cursosLeidos = mapper.readValue(archivo, new TypeReference<List<Curso>>(){});
            if (cursosLeidos != null) {
                this.cacheCursos = cursosLeidos;
            }
        } catch (IOException e) {
            System.err.println("Error cargando cursos.json. El archivo podría estar corrupto o las clases han cambiado.");
            e.printStackTrace();
            this.cacheCursos = new ArrayList<>();
        }
    }

    private Long generarNuevoId() {
        return cacheCursos.stream()
                .mapToLong(c -> c.getId() != null ? c.getId() : 0)
                .max()
                .orElse(0) + 1;
    }
}