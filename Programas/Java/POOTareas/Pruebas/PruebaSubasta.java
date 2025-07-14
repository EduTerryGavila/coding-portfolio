package Pruebas;
import subastas.*;

public class PruebaSubasta {
	public static void main(String[] args) {
		Subasta subasta1 = new Subasta("Telefono_movil", "Juan");
		subasta1.pujar("Pedro",100);
		System.out.println("Cantidad = "+subasta1.getPujaMayor().getCantidad()
							+ "\nNombre pujador: "+subasta1.getPujaMayor().getPujador());
		subasta1.pujar("Enrique",50);
		System.out.println("Cantidad = "+subasta1.getPujaMayor().getCantidad()
				+ "\nNombre pujador: "+subasta1.getPujaMayor().getPujador());
		subasta1.ejecutar();
		subasta1.pujar("Enrique",200);
		System.out.println("Cantidad = "+subasta1.getPujaMayor().getCantidad()
				+ "\nNombre pujador: "+subasta1.getPujaMayor().getPujador());
		}
}
