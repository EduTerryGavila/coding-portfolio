package Pruebas;
import geometria.*;

public class PruebaCirculo {
	public static void main(String[] args) {
		Punto punto1 = new Punto(2,3);
		Circulo circulo1 = new Circulo(punto1,3);
		System.out.println("Centro = ("+circulo1.getCentro().getX()+","+circulo1.getCentro().getY()+")\n"
							+"Radio = "+circulo1.getRadio());
		Circulo circulo2 = new Circulo();
		System.out.println("Centro = ("+circulo2.getCentro().getX()+","+circulo2.getCentro().getY()+")\n"
				+"Radio = "+circulo2.getRadio());
		circulo1.desplazar(3, 2);
		System.out.println("Centro = ("+circulo1.getCentro().getX()+","+circulo1.getCentro().getY()+")\n"
				+"Radio = "+circulo1.getRadio());
		circulo2.escalar(150.0);
		System.out.println("Centro = ("+circulo2.getCentro().getX()+","+circulo2.getCentro().getY()+")\n"
				+"Radio = "+circulo2.getRadio());
		Circulo circulo4 = new Circulo(punto1,5);
		circulo1.desplazar(3, 2);
		System.out.println();
		System.out.println("Centro = ("+circulo1.getCentro().getX()+","+circulo1.getCentro().getY()+")\n"
				+"Radio = "+circulo1.getRadio());
		System.out.println("Centro = ("+circulo4.getCentro().getX()+","+circulo4.getCentro().getY()+")\n"
				+"Radio = "+circulo4.getRadio());
		Punto punto2 = new Punto(2,3);
		punto2.desplazar(1, 0);
		System.out.println();
		System.out.println("("+punto2.getX()+","+punto2.getY()+")");
		System.out.println("Centro = ("+circulo4.getCentro().getX()+","+circulo4.getCentro().getY()+")\n"
				+"Radio = "+circulo4.getRadio());
	}
}
