package eventosDeportivos;
import java.util.HashMap;

public abstract class Evento {
	protected String nombreEvento;
	protected double precioApuesta;
	protected HashMap<String, Marcadorr> apuestas;
	
	public Evento(String nombreEvento, double precioApuesta) {
		this.nombreEvento = nombreEvento;
		this.precioApuesta = precioApuesta;
		this.apuestas = new HashMap<>();
	}
	
	public String getNombreEvento() {
		return nombreEvento;
	}
	
	public double getPrecioApuestas() {
		return precioApuesta;
	}
	
	public int getNumApuestasRealizadas() {
		return apuestas.size();
	}
	
	public double getRecaudacion() {
		return apuestas.size() * precioApuesta;
	}
	
	public abstract boolean apostar(String usuario, Marcadorr marcador);
	
	public String toString() {
		return getClass().getName() +
				"\nNombre evento = " + nombreEvento
				+ "\nPrecio apuestas = " + precioApuesta
				+ "\nApuestas = " + apuestas.toString()
				+ "\nNumApuestasRealizadas = " + getNumApuestasRealizadas()
				+ "\nRecaudacion = " + getRecaudacion();
	}
}
