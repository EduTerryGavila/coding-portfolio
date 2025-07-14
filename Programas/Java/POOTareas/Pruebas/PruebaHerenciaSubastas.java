package Pruebas;
import subastas.*;
import java.util.LinkedList;

public class PruebaHerenciaSubastas {
	public static void main(String[] args) {
		String nombre1 = "Juan";
		String nombre2 = "Enrique";
		SubastaLimitada limitada = new SubastaLimitada("Disco duro multimedia", nombre1, 1);
		SubastaTemporal temporal = new SubastaTemporal("Teclado", nombre1, 3);
		SubastaMinima minima = new SubastaMinima("Impresora Laser", nombre1, 100);
		LinkedList<Subasta> lista = new LinkedList<Subasta>();
		lista.add(limitada);
		lista.add(temporal);
		lista.add(minima);
		for(int i = 0; i< lista.size(); i++) {
			System.out.println(lista.get(i).getNombreProducto());
			lista.get(i).pujar(nombre2,10);
			lista.get(i).pujar(nombre2,20);
			System.out.println(lista.get(i).ejecutar());
		}
	}
}
