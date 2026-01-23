package com.miapp.flashcards.estrategiafactExpImp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.miapp.flashcards.modelo.Curso;

import java.io.File;
import java.io.IOException;

public class EstrategiaJSON implements EstrategiaExpImp {

    private ObjectMapper mapper;

    public EstrategiaJSON() {
        this.mapper = new ObjectMapper();
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.mapper.findAndRegisterModules();
    }

    @Override
    public void guardarCurso(Curso curso, File archivoDestino) throws IOException {
        mapper.writeValue(archivoDestino, curso);
    }

    @Override
    public Curso cargarCurso(File archivoOrigen) throws IOException {
        return mapper.readValue(archivoOrigen, Curso.class);
    }
}