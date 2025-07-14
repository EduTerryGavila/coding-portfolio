package Pruebas;
import geometria.Punto;

public class PruebaPunto {
	public static void main(String[] args) {
		Punto punto1 = new Punto(1,3);
		System.out.println("("+punto1.getX()+","+punto1.getY()+")");
		Punto punto2 = new Punto();
		System.out.println("("+punto2.getX()+","+punto2.getY()+")");
		Punto punto3 = new Punto(punto1);
		System.out.println("("+punto3.getX()+","+punto3.getY()+")");
		Punto punto4 = punto2;
		System.out.println("("+punto4.getX()+","+punto4.getY()+")");
		punto2.desplazar(1, 1);
		System.out.println("("+punto2.getX()+","+punto2.getY()+")");
		System.out.println("("+punto4.getX()+","+punto4.getY()+")");
	}
}
