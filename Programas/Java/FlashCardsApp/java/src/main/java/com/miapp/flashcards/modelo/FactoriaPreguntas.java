package com.miapp.flashcards.modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Metodo factoria para los distintos tipos de preguntas que soporta el sistema
public class FactoriaPreguntas {

    public static Pregunta crearPreguntaSimple(String tipo, String enunciado, String respuesta) {
        if (tipo.equalsIgnoreCase("Abierta")) {
            return new PreguntaAbierta(enunciado, respuesta);
        } 
        else if (tipo.equalsIgnoreCase("Rellenar Hueco")) {
            return new PreguntaRellenarHueco(enunciado, respuesta);
        }
        return null;
    }

    public static Pregunta crearPreguntaOpciones(String tipo, String enunciado, String[] opcionesArray, int indiceCorrecta) {
        List<String> listaOpciones = new ArrayList<>(Arrays.asList(opcionesArray));
        
        if (tipo.equals("Test (VOF)") || tipo.equals("Test")) {
            return new PreguntaVerdaderoOFalso(enunciado, listaOpciones, indiceCorrecta);
        }
        
        return null;
    }
}