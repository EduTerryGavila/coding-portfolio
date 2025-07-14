package Pruebas;
import geometria.*;

public class PruebaRectangulo {
	public static void main(String[] args) {
		Punto verticeII1 = new Punto(3,1);
		Rectangulo rectangulo1 = new Rectangulo(verticeII1,2,5);
		System.out.println("VerticeII = ("+rectangulo1.getVerticeII().getX()+","+rectangulo1.getVerticeII().getY()+")"
							+ " VerticeSI = ("+rectangulo1.getVerticeSI().getX()+","+rectangulo1.getVerticeSI().getY()+")"
							+ " VerticeSD = ("+rectangulo1.getVerticeSD().getX()+","+rectangulo1.getVerticeSD().getY()+")"
							+ " VerticeID = ("+rectangulo1.getVerticeID().getX()+","+rectangulo1.getVerticeID().getY()+")");
		rectangulo1.desplazar(0, -7);
		System.out.println("VerticeII = ("+rectangulo1.getVerticeII().getX()+","+rectangulo1.getVerticeII().getY()+")"
				+ " VerticeSI = ("+rectangulo1.getVerticeSI().getX()+","+rectangulo1.getVerticeSI().getY()+")"
				+ " VerticeSD = ("+rectangulo1.getVerticeSD().getX()+","+rectangulo1.getVerticeSD().getY()+")"
				+ " VerticeID = ("+rectangulo1.getVerticeID().getX()+","+rectangulo1.getVerticeID().getY()+")");
		rectangulo1.escalar(50);
		System.out.println("VerticeII = ("+rectangulo1.getVerticeII().getX()+","+rectangulo1.getVerticeII().getY()+")"
				+ " VerticeSI = ("+rectangulo1.getVerticeSI().getX()+","+rectangulo1.getVerticeSI().getY()+")"
				+ " VerticeSD = ("+rectangulo1.getVerticeSD().getX()+","+rectangulo1.getVerticeSD().getY()+")"
				+ " VerticeID = ("+rectangulo1.getVerticeID().getX()+","+rectangulo1.getVerticeID().getY()+")");
	}
}
