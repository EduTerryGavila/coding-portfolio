package Pruebas;
import geometria.*;

public class ProgramaSesion3 {
	public static void main(String[] args) {
		Punto punto1 = new Punto(1,1);
		punto1.desplazar(Direccion.ARRIBA);
		punto1.desplazar(Direccion.ABAJO);
		punto1.desplazar(Direccion.DERECHA);
		punto1.desplazar(Direccion.IZQUIERDA);
		Punto punto2 = new Punto(4,2);
		Punto array[] = new Punto[2];
		array[0] = punto1;
		array[1] = punto2;
		
		for(Punto punto : array) {
			System.out.println("("+punto.getX()+","+punto.getY()+")");
		}
		punto1.distancia(punto2);
		System.out.println(punto1.distancia(punto2));
		Punto punto3 = punto1.mayorDistancia(punto1, punto2);
		System.out.println("("+punto3.getX()+","+punto3.getY()+")");
	}
}
