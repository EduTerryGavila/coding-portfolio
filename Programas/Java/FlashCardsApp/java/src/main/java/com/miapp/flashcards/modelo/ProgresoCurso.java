package com.miapp.flashcards.modelo;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class ProgresoCurso implements Serializable {
    
    private static final long serialVersionUID = 1L;
	
    private int indicePregunta;    
    private int aciertos;           
    private String nombreEstrategia;
    
    public ProgresoCurso() {}

    public ProgresoCurso(int indicePregunta, int aciertos, String nombreEstrategia) {
        this.indicePregunta = indicePregunta;
        this.aciertos = aciertos;
        this.nombreEstrategia = nombreEstrategia;
    }

    public int getIndicePregunta() { 
    	return indicePregunta; 
    }
    
    public void setIndicePregunta(int indicePregunta) { 
    	this.indicePregunta = indicePregunta; 
    }

    public int getAciertos() { 
    	return aciertos; 
    }
    
    public void setAciertos(int aciertos) { 
    	this.aciertos = aciertos; 
    }

    public String getNombreEstrategia() { 
    	return nombreEstrategia; 
    }
    
    public void setNombreEstrategia(String nombreEstrategia) { 
    	this.nombreEstrategia = nombreEstrategia; 
    }
}