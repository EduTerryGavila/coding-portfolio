package viajes;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class ViajeSelectivo extends Viaje{
	private LinkedList<String> vetados;
	
	public ViajeSelectivo(String propietarioVehiculo, String modeloVehiculo, String ruta, LocalDateTime fechaSalida, int plazasOfrecidas) {
		super(propietarioVehiculo, modeloVehiculo, ruta, fechaSalida, plazasOfrecidas);
		this.vetados = new LinkedList<String>();
	}
	
	public LinkedList<String> getVetados() {
		return new LinkedList<String>(vetados);
	}
	
	public void añadirVeto(String usuario) {
		vetados.add(usuario);
	}
	
	public void eliminarVeto(String usuario) {
		for(int i = 0; i < vetados.size(); i++) {
			if(vetados.get(i) == usuario) {
				vetados.remove(i);
			}
		}
	}
	
	public Reserva realizarReserva(String usuario, int numPlazas) {
		for(int i = 0; i < vetados.size(); i++) {
			if(vetados.get(i) == usuario) {
				return null;
			}
		}
		Reserva reserva = super.realizarReserva(usuario, numPlazas);
		return reserva;
	}
	
	public String toString() {
		return super.toString()
				+ "\nlista vetados = " + vetados.toString();
	}
	
	public ViajeSelectivo  clone() {
		return (ViajeSelectivo)super.clone();
	}
}
