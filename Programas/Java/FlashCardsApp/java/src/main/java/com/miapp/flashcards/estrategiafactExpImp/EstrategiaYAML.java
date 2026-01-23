package com.miapp.flashcards.estrategiafactExpImp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.miapp.flashcards.modelo.Curso;

import java.io.File;
import java.io.IOException;

public class EstrategiaYAML implements EstrategiaExpImp {

    private ObjectMapper mapper;

    public EstrategiaYAML() {
        this.mapper = new ObjectMapper(new YAMLFactory());
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