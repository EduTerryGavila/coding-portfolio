package geometria;

public class Circulo {
	private static final double PI = Math.PI;
	private static final Punto ORIGEN_COORDENADAS = new Punto(0,0);
	private static final int RADIO_POR_DEFECTO = 5;
	private Punto centro;
	private int radio;
	
	public Circulo(Punto centro, int radio) {
		this.centro = centro;
		this.radio = radio;
	}
	
	public Circulo() {
		this(ORIGEN_COORDENADAS,RADIO_POR_DEFECTO);
	}
	
	public Punto getCentro() {
		return centro;
	}
	
	public int getRadio() {
		return radio;
	}
	
	public double getPerimetro() {
		return 2*PI*radio;
	}
	
	public void desplazar(int dx, int dy) {
		centro = new Punto(dx+centro.getX(),dy+centro.getY());
	}
	
	public void escalar(double cantidad) {
		double resultado = cantidad/100.0;
		radio = (int)(resultado*radio);
		
	}
}
