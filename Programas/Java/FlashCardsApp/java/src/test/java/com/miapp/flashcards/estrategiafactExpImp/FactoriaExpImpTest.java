package com.miapp.flashcards.estrategiafactExpImp;

import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class FactoriaExpImpTest {

    @Test
    void testDetectarJson() {
        File archivoJson = new File("curso.json");
        EstrategiaExpImp estrategia = FactoriaExpImp.obtenerEstrategia(archivoJson);
        
        assertNotNull(estrategia, "Debería devolver una estrategia para .json");
    }

    @Test
    void testDetectarYaml() {
        File archivoYaml = new File("curso.yaml");
        EstrategiaExpImp estrategia = FactoriaExpImp.obtenerEstrategia(archivoYaml);
        
        assertNotNull(estrategia, "Debería devolver una estrategia para .yaml");
    }

    @Test
    void testExtensionDesconocida() {
        File archivoRaro = new File("curso.txt");
        EstrategiaExpImp estrategia = FactoriaExpImp.obtenerEstrategia(archivoRaro);
        
        assertNull(estrategia, "Debería devolver null para extensiones no soportadas");
    }
}