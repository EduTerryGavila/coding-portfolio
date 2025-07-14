package Pruebas;
import java.time.LocalDateTime;
import viajes.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Comparator;

public class PruebaViajes {
	public static void main(String[] args) {
		String nombre1 = "José Antonio";
		String modelo1 = "Seat León";
		LocalDateTime fecha1 = LocalDateTime.of(2023, 12, 9, 0, 0);
		Viaje viaje1 = new Viaje(nombre1, modelo1, "Murcia-Cartagena",fecha1);
		LocalDateTime fecha2 = LocalDateTime.of(2026, 12, 11, 0, 0);
		ViajeSelectivo viajeS1 = new ViajeSelectivo(nombre1, modelo1, "Murcia–Campus", fecha2, 4);
		viajeS1.añadirVeto("Beatriz");
		LocalDateTime fecha3 = LocalDateTime.of(2025, 12, 15, 0, 0);
		ViajePremium viajeP1 = new ViajePremium(nombre1, modelo1, "Murcia-Barcelona", fecha3, 6);
		viaje1.realizarReserva("Alberto", 2);
		viajeS1.realizarReserva("Enrique", 3);
		viajeS1.realizarReserva("Beatriz", 1);
		viajeP1.realizarReserva("Ana", 2);
		viajeP1.cancelarReserva("Ana");
		
		LinkedList<Viaje> viajes = new LinkedList<Viaje>();
		viajes.add(viaje1);
		viajes.add(viajeS1);
		viajes.add(viajeP1);
		for(int i = 0; i < viajes.size(); i++) {
			if(viajes.get(i).getClass() == ViajeSelectivo.class) {
				((ViajeSelectivo) viajes.get(i)).eliminarVeto("Beatriz");
			}
			//System.out.println(viajes.get(i).toString());
		}
		LinkedList<Viaje> copias = new LinkedList<Viaje>();
		for(int i = 0; i < viajes.size(); i++) {
			copias.add(viajes.get(i).clone());
			//System.out.println(copias.get(i).toString());
		}
		
		Collections.sort(viajes);
		
		for(Viaje viaje : viajes) {
			System.out.println(viaje.getClass());
		}
		
		viajes.sort(new ComparadorViaje());
		
		for(Viaje viaje : viajes) {
			System.out.println(viaje.getClass());
		}
		
		viajeS1.realizarReserva("Antonio", 1);
		
		Comparator<Reserva> comparadorReserva = new ComparadorReserva();
		
		 LinkedList<Reserva> reservasOrdenadasPorUsuarioFecha = viajeS1.getReservas(comparadorReserva);
	        System.out.println("Reservas ordenadas por usuario y fecha:");
	        for (Reserva reserva1 : reservasOrdenadasPorUsuarioFecha) {
	            System.out.println(reserva1.toString());
	        }
	}
}
