package viajes;

import java.time.LocalDateTime;

public class ViajePremium extends Viaje{
	
	public ViajePremium(String propietarioVehiculo, String modeloVehiculo, String ruta, LocalDateTime fechaSalida, int plazasOfrecidas) {
		super(propietarioVehiculo, modeloVehiculo, ruta, fechaSalida, plazasOfrecidas);
	}
	
	public boolean cancelarReserva(String codigoReserva) {
		if(LocalDateTime.now().isBefore(fechaSalida.minusDays(1))) {
			for(int i = 0; i < listaReservas.size(); i++) {
				if(listaReservas.get(i).getCodigoReserva() == codigoReserva) {
					listaReservas.remove(i);
					return true;
				}
			}	
		}
		return false;
	}
	
	public String toString() {
		return super.toString();
	}
	
	public ViajePremium clone() {
		return (ViajePremium)super.clone();
	}
}
