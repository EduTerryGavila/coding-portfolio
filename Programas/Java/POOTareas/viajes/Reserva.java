package viajes;
import java.time.LocalDateTime;
import java.util.UUID;

public class Reserva {
	private final String codigoReserva;
	private final String usuario;
	private final int numPlazas;
	private final LocalDateTime fechaReserva;
	
	public Reserva(String usuario, int numPlazas) {
		this.usuario = usuario;
		this.numPlazas = numPlazas;
		codigoReserva = UUID.randomUUID().toString();
		fechaReserva = LocalDateTime.now();
	}
	
	public int getNumPlazas() {
		return numPlazas;
	}
	
	public String getCodigoReserva() {
		return codigoReserva;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public LocalDateTime getFechaReserva() {
		return fechaReserva;
	}
	
	public String toString() {
		return getClass().getName()
				+ "\nCodigo reserva = " + codigoReserva
				+ "\nusuario = " + usuario
				+ "\nnumPlazas = " + numPlazas
				+ "\nfechaReserva = " + fechaReserva.toString();
	}
}
