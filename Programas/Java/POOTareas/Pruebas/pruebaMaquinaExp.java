package Pruebas;

import maquinas.Dinero;
import maquinas.MaquinaExpendedora;

public class pruebaMaquinaExp {
	public static void main(String[] args) {
		MaquinaExpendedora maquina1 = new MaquinaExpendedora("Agua", 0.60, 10);
		maquina1.insertarDinero(Dinero.EUR1);
		maquina1.devolverDinero();
		maquina1.insertarDinero(Dinero.CENT50);
		maquina1.comprar();
		System.out.println(maquina1.comprar());
		maquina1.insertarDinero(Dinero.CENT20);
		System.out.println(maquina1.comprar());
		System.out.println(maquina1.getStock());
		System.out.println(maquina1.getDineroAcumulado().getValorTotal());
		System.out.println(maquina1.getDineroTransaccion().getValorTotal());
		
		
	}
}
