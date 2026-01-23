package com.miapp.flashcards.estrategiafactExpImp;

import com.miapp.flashcards.modelo.Curso;
import java.io.File;
import java.io.IOException;

//Interfaz para el patron estrategia que sigue la importacion y exportacion
public interface EstrategiaExpImp {
    
    void guardarCurso(Curso curso, File archivoDestino) throws IOException;

    Curso cargarCurso(File archivoOrigen) throws IOException;
}