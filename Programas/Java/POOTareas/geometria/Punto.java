package geometria;

public class Punto {
	private int x;
	private int y;
	
	public Punto(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Punto() {
		this(0,0);
	}
	
	public Punto(Punto punto) {
		x = punto.getX();
		y = punto.getY();
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void desplazar(int x, int y) {
		this.x = this.x + x;
		this.y = this.y + y;
	}
	
	public double distancia(Punto punto) {
		double resta1 = this.x - punto.getX();
		double resta2 = this.y - punto.getY();
		double suma = Math.pow(resta1, 2) + Math.pow(resta2, 2);
		double dist = Math.sqrt(suma);
		return dist;
	}
	
	public void desplazar(Direccion direccion) {
		if(direccion == Direccion.DERECHA) {
			this.x = this.x + 1;
		}
		if(direccion == Direccion.IZQUIERDA) {
			this.x = this.x - 1;
		}
		if(direccion == Direccion.ARRIBA) {
			this.y = this.y + 1;
		}
		if(direccion == Direccion.ABAJO) {
			this.y = this.y - 1;
		}
	}
	
	public Punto mayorDistancia (Punto...puntos) {
		Punto origen = new Punto();
		for(Punto punto : puntos) {
			if(punto.getX()+punto.getY() > origen.getX()+origen.getY()) {
				origen = new Punto(punto.getX(),punto.getY());
			}
		}
		return origen;
	}
	
	
}
