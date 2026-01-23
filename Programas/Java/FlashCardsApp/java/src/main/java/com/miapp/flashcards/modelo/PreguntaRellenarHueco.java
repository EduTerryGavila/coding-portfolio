package com.miapp.flashcards.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "preguntas_rellenar")
public class PreguntaRellenarHueco extends Pregunta {

    private static final long serialVersionUID = 1L;

    @Column(length = 500)
    private String respuestaCorrecta;

    public PreguntaRellenarHueco() {
        super();
    }

    public PreguntaRellenarHueco(String enunciado, String respuestaCorrecta) {
        super(enunciado);
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }
}