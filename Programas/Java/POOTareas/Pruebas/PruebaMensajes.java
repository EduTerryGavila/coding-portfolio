package Pruebas;
import java.util.Comparator;

import analizadorMensajes.*;

public class PruebaMensajes {
	 public static void main(String[] args) {
	        Rastreador rastreador = new Rastreador();

	        Mensaje m1 = new Mensaje("Ernesto", "Los rivales también juegan, no sólo el #Barça");
	        Mensaje m2 = new Mensaje("Manolete", "El #Barça es poco competitivo esta temporada");
	        Mensaje m3 = new Mensaje("Manolete", "#Barça y #RealMadrid han roto el mercado de fichajes");

	        rastreador.registrarMensaje(m1);
	        rastreador.registrarMensaje(m2);
	        rastreador.registrarMensaje(m3);

	        System.out.println("Todos los mensajes registrados:");
	        for (Mensaje m : rastreador.getMensajes()) {
	            System.out.println(m);
	        }

	        System.out.println("\nMensajes que incluyen el tema #Barça:");
	        for (Mensaje m : rastreador.obtenerMensajesPorTema("#Barça")) {
	            System.out.println(m);
	        }

	        System.out.println("\nAutores que han publicado mensajes:");
	        for (String autor : rastreador.getAutores()) {
	            System.out.println(autor);
	        }

	        System.out.println("\nMensajes del autor Manolete:");
	        for (Mensaje m : rastreador.obtenerMensajesPorAutor("Manolete")) {
	            System.out.println(m);
	        }

	        System.out.println("\nMensajes ordenados por orden natural:");
	        for (Mensaje m : rastreador.obtenerMensajesOrdenados()) {
	            System.out.println(m);
	        }

	        System.out.println("\nMensajes ordenados por orden inverso al natural:");
	        for (Mensaje m : rastreador.obtenerMensajesOrdenados(Comparator.reverseOrder())) {
	            System.out.println(m);
	        }

	        System.out.println("\nMensajes ordenados por tamaño del texto (de menor a mayor):");
	        for (Mensaje m : rastreador.obtenerMensajesOrdenados(Comparator.comparingInt(m -> m.getTexto().length()))) {
	            System.out.println(m);
	        }
	    }
}
