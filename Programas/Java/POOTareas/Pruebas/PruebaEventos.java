package Pruebas;
import java.util.HashSet;
import java.util.LinkedList;

import eventosDeportivos.*;

public class PruebaEventos {
	public static void main(String[] args) {
		EventoLibre eventoLibre1 = new EventoLibre("Real Madrid - F.C. Barcelona" , 1);
		eventoLibre1.apostar("Juan", new Marcadorr(5,0));
		eventoLibre1.apostar("Pepe", new Marcadorr(1,3));
		
		HashSet<Marcadorr> marcadores1 = new HashSet<Marcadorr>();
		marcadores1.add(new Marcadorr(2,0));
		marcadores1.add(new Marcadorr(2,1));
		marcadores1.add(new Marcadorr(0,2));
		marcadores1.add(new Marcadorr(1,2));
		EventoRestringido eventoRestringido1 = new EventoRestringido("Alcaraz vs Djokovic", 3, marcadores1);
		eventoRestringido1.apostar("Juan", new Marcadorr(2,0));
		eventoRestringido1.apostar("Pedro", new Marcadorr(2,1));
		eventoRestringido1.apostar("Pepe", new Marcadorr(2,0));
		
		LinkedList<Evento> listaEventos = new LinkedList<>();
		listaEventos.add(eventoLibre1);
		listaEventos.add(eventoRestringido1);
		
		for(int i = 0; i < listaEventos.size(); i++) {
			System.out.println(listaEventos.get(i).apostar("Enrique", new Marcadorr(5,0)));
			System.out.println(listaEventos.get(i).toString());
			if(listaEventos.get(i).getClass() == EventoRestringido.class) {
				EventoRestringido evento = (EventoRestringido)listaEventos.get(i);
				System.out.println(evento.getApuestasPorMarcador(new Marcadorr(2,0)));
			}
		}
	}
}
