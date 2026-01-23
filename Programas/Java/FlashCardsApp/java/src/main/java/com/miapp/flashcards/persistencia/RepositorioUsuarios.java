package com.miapp.flashcards.persistencia;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.miapp.flashcards.modelo.Creador;
import com.miapp.flashcards.modelo.Estudiante;
import com.miapp.flashcards.modelo.Usuario;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Repositorio para la persistencia de los usuarios usando singleton
public class RepositorioUsuarios {

    private static RepositorioUsuarios instance;
    private static final String RUTA_ARCHIVO = "usuarios.json";
    
    private ObjectMapper mapper;
    private List<Usuario> cacheUsuarios;

    private RepositorioUsuarios() {
        this.mapper = new ObjectMapper();
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.mapper.activateDefaultTyping(
            mapper.getPolymorphicTypeValidator(), 
            ObjectMapper.DefaultTyping.NON_FINAL, 
            com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY
        );

        this.cacheUsuarios = new ArrayList<>();
        cargarDesdeFichero();
    }

    public static RepositorioUsuarios getInstance() {
        if (instance == null) {
            instance = new RepositorioUsuarios();
        }
        return instance;
    }

    public Usuario identificarUsuario(String email, String password) {
        Usuario u = buscarPorEmail(email);
        
        if (u != null && u.getPassword().equals(password)) {
            return u;
        }
        return null; 
    }

    public void guardarNuevoUsuario(Usuario usuario) {
        long id = generarNuevoId();
        usuario.setId(id);
        this.cacheUsuarios.add(usuario);
        guardarEnFichero();
    }

    public boolean existeEmail(String email) {
        return buscarPorEmail(email) != null;
    }

    public Usuario buscarPorEmail(String email) {
        return cacheUsuarios.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
    
    public List<Usuario> listarTodos() {
        return new ArrayList<>(cacheUsuarios);
    }
    
    public void guardarCambios() {
        guardarEnFichero();
    }

    private void guardarEnFichero() {
        try {
            File archivoTemp = new File(RUTA_ARCHIVO + ".tmp");
            mapper.writeValue(archivoTemp, cacheUsuarios);

            File archivoReal = new File(RUTA_ARCHIVO);
            if (archivoReal.exists()) {
                archivoReal.delete();
            }
            archivoTemp.renameTo(archivoReal);
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("¡ERROR GRAVE! No se pudo guardar usuarios.json");
        }
    }

    private void cargarDesdeFichero() {
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) return;

        try {
            List<Usuario> usuariosLeidos = mapper.readValue(archivo, new TypeReference<List<Usuario>>(){});
            if (usuariosLeidos != null) {
                this.cacheUsuarios = usuariosLeidos;
            }
        } catch (IOException e) {
            System.err.println("Error FATAL cargando usuarios.json. El archivo podría estar corrupto.");
            e.printStackTrace(); 
            this.cacheUsuarios = new ArrayList<>();
        }
    }

    private long generarNuevoId() {
        return cacheUsuarios.stream().mapToLong(Usuario::getId).max().orElse(0) + 1;
    }
    
    public void crearEstudiante(String nombre, String email, String password) {
        Estudiante nuevoUsuario = new Estudiante(nombre, email, password);
        this.guardarNuevoUsuario(nuevoUsuario);
    }
    
    public void crearCreador(String nombre, String email, String password) {
        Creador nuevoUsuario = new Creador(nombre, email, password);
        this.guardarNuevoUsuario(nuevoUsuario);
    }
}