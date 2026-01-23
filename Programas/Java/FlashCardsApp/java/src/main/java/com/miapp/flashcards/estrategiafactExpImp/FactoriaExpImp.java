package com.miapp.flashcards.estrategiafactExpImp;

import java.io.File;

//Esta clase implementa el patrón factoria para decidir automaticamente que estrategia crear 
//analizando la extensión del archivo, ocultando esa lógica de decisión al resto del programa.
public class FactoriaExpImp {

    public static EstrategiaExpImp obtenerEstrategia(File archivo) {
        if (archivo == null) return null;
        
        String nombre = archivo.getName().toLowerCase();

        if (nombre.endsWith(".json")) {
            return new EstrategiaJSON();
        } 
        else if (nombre.endsWith(".yaml") || nombre.endsWith(".yml")) {
            return new EstrategiaYAML();
        } 
        
        return null;
    }
}