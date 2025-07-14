package subastas;

public final class Puja {
	private final String pujador;
	private final double cantidad;
	
	public Puja(String pujador, double cantidad) {
		this.pujador = pujador;
		this.cantidad = cantidad;
	}
	
	public String getPujador() {
		return pujador;
	}
	
	public double getCantidad() {
		return cantidad;
	}
}
