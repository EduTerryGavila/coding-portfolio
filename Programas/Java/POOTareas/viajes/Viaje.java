package viajes;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;

public class Viaje implements Cloneable, Comparable<Viaje>{
	private final String propietarioVehiculo;
	private final String modeloVehiculo;
	private final String ruta;
	protected final LocalDateTime fechaSalida;
	private final int plazasOfrecidas;
	protected LinkedList<Reserva> listaReservas;
	private int numeroPlazasReservadas;
	private int plazasDisponibles;
	
	public Viaje(String propietarioVehiculo, String modeloVehiculo, String ruta, LocalDateTime fechaSalida, int plazasOfrecidas) {
		this.propietarioVehiculo = propietarioVehiculo;
		this.modeloVehiculo = modeloVehiculo;
		this.ruta = ruta;
		this.fechaSalida = fechaSalida;
		this.plazasOfrecidas = plazasOfrecidas;
		this.listaReservas = new LinkedList<Reserva>();
		this.numeroPlazasReservadas = getNumeroPlazasReservadas();
		this.plazasDisponibles = plazasOfrecidas - numeroPlazasReservadas;
	}
	
	public Viaje(String propietarioVehiculo, String modeloVehiculo, String ruta, LocalDateTime fechaSalida) {
		this(propietarioVehiculo, modeloVehiculo, ruta, fechaSalida, 1);
		this.listaReservas = new LinkedList<Reserva>();
		this.numeroPlazasReservadas = getNumeroPlazasReservadas();
		this.plazasDisponibles = plazasOfrecidas - numeroPlazasReservadas;
	}
	
	public String getRuta() {
		return ruta;
	}
	
	public LinkedList<Reserva> getReservas(){
		return new LinkedList<>(listaReservas);
	}
	
	public LinkedList<Reserva> getReservas(Comparator<Reserva> comparator) {
        LinkedList<Reserva> reservasOrdenadas = new LinkedList<>(listaReservas);
        reservasOrdenadas.sort(comparator);
        return reservasOrdenadas;
    }
	
	public Reserva realizarReserva(String usuario, int numPlazas) {
		if(plazasDisponibles >= numPlazas  && LocalDateTime.now().isBefore(fechaSalida)) {
			Reserva reserva = new Reserva(usuario, numPlazas);
			listaReservas.add(reserva);
			numeroPlazasReservadas += numPlazas;
	        plazasDisponibles = plazasOfrecidas - numeroPlazasReservadas;
			return reserva;
		}
		return null;
	}
	
	public Reserva consultarReserva(String codigoReserva) {
		for(int i = 0; i < listaReservas.size(); i++) {
			if(listaReservas.get(i).getCodigoReserva() == codigoReserva) {
				return listaReservas.get(i);
			}
		}
		return null;
	}
	
	public int getNumeroPlazasReservadas() {
		int num = 0;
		for(int i = 0; i<listaReservas.size(); i++) {
			num = num + listaReservas.get(i).getNumPlazas();
		}
		return num;
	}
	
	public String toString() {
		return getClass().getName() 
			+ "\nPropietario vehiculo = " + propietarioVehiculo
			+ "\nnmodelo vehiculo = " + modeloVehiculo
			+ "\nRuta = " + ruta
			+ "\nfecha de salida = " + fechaSalida.toString()
			+ "\nplazas ofrecidas = " + plazasOfrecidas
			+ "\nlista de reservas = " + listaReservas.toString()
			+ "\nplazas reservadas = " + numeroPlazasReservadas
			+ "\nplazas disponibles = " + plazasDisponibles;				
	}
	

	private Viaje copiaProfunda() {
		try {
			Viaje copiaProfunda = (Viaje) super.clone();
			copiaProfunda.listaReservas = new LinkedList<>(listaReservas);
			return copiaProfunda;
		}
		catch (CloneNotSupportedException e){
			System.err.println("La clase no es cloneable");
		}
		return null;
	}
	
	public Viaje clone() {
		return copiaProfunda();
	}
	
	@Override
	public int compareTo(Viaje otro) {
	return this.fechaSalida.compareTo(otro.fechaSalida);
	}

}
