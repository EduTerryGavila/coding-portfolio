package eventosDeportivos;

public class EventoLibre extends Evento{

	public EventoLibre(String nombreEvento, double precioApuesta) {
		super(nombreEvento, precioApuesta);
	}
	
	public boolean apostar(String usuario, Marcadorr marcador) {
		if(!this.apuestas.containsValue(marcador)) {
			this.apuestas.put(usuario, marcador);
			return true;
		}
		return false;
	}
	
	public String toString() {
		return super.toString();
	}
}
