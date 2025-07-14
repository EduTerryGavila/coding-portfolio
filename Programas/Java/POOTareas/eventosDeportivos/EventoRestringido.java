package eventosDeportivos;
import java.util.HashSet;

public class EventoRestringido extends Evento{
	
	private HashSet<Marcadorr> opciones;
	
	public EventoRestringido(String nombreEvento, double precioApuesta, HashSet<Marcadorr> opciones) {
		super(nombreEvento, precioApuesta);
		this.opciones = opciones;
	}
	
	public boolean apostar(String usuario, Marcadorr marcador) {
		if(this.opciones.contains(marcador)) {
			this.apuestas.put(usuario, marcador);
			return true;
		}
		return false;
	}
	
	public int getApuestasPorMarcador(Marcadorr marcador) {
        int contador = 0;
        for (Marcadorr m : apuestas.values()) {
            if (m.equals(marcador)) {
            	contador++;
            }
        }
        return contador;
    }
	
	public String toString() {
		return super.toString()
				+ "\nOpciones = " + opciones.toString();
	}
}
