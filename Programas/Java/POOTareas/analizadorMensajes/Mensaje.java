package analizadorMensajes;
import java.util.*;

public class Mensaje implements Comparable<Mensaje> {
    private String autor;
    private String texto;
    private Set<String> temas;

    public Mensaje(String autor, String texto) {
        this.autor = autor;
        this.texto = texto;
        this.temas = extraerTemas(texto);
    }

    public String getAutor() {
        return autor;
    }

    public String getTexto() {
        return texto;
    }

    public Set<String> getTemas() {
        return temas;
    }

    private Set<String> extraerTemas(String texto) {
        Set<String> temas = new HashSet<>();
        String[] palabras = texto.split("\\s+");
        for (String palabra : palabras) {
            if (palabra.startsWith("#")) {
                temas.add(palabra);
            }
        }
        return temas;
    }

    @Override
    public int compareTo(Mensaje otro) {
        int autorComparacion = this.autor.compareTo(otro.autor);
        if (autorComparacion == 0) {
            return this.texto.compareTo(otro.texto);
        }
        return autorComparacion;
    }

    @Override
    public String toString() {
        return "Autor: " + autor + ", Texto: " + texto + ", Temas: " + temas;
    }
}