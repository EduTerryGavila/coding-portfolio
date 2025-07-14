package maquinas;

public class Dinero {
	private final int cent5;
	private final int cent10;
	private final int cent20;
	private final int cent50;
	private final int eur1;
	private final int eur2;
	private final double[] cantidad = {0.05, 0.1, 0.2, 0.5, 1, 2};
	private double valorTotal;
	
	public Dinero(int cent5, int cent10, int cent20, int cent50, int eur1, int eur2) {
		this.cent5 = cent5;
		this.cent10 = cent10;
		this.cent20 = cent20;
		this.cent50 = cent50;
		this.eur1 = eur1;
		this.eur2 = eur2;
		valorTotal = valorTotal();
	}
	
	public double valorTotal() {
		return cent5*cantidad[0] + cent10*cantidad[1] + cent20*cantidad[2] 
				+ cent50*cantidad[3] + eur1*cantidad[4] + eur2*cantidad[5]; 
	}
	
	public double getValorTotal() {
		return valorTotal;
	}

	public Dinero sumar(Dinero otro) {
		return new Dinero(this.cent5+otro.cent5,this.cent10+otro.cent10,this.cent20+otro.cent20,
		this.cent50+otro.cent50,this.eur1+otro.eur1,this.eur2+otro.eur2);
	}
	
	public static final Dinero CENT5 = new Dinero(1, 0, 0, 0, 0, 0);
	public static final Dinero CENT10 = new Dinero(0, 1, 0, 0, 0, 0);
	public static final Dinero CENT20 = new Dinero(0, 0, 1, 0, 0, 0);
	public static final Dinero CENT50 = new Dinero(0, 0, 0, 1, 0, 0);
	public static final Dinero EUR1 = new Dinero(0, 0, 0, 0, 1, 0);
	public static final Dinero EUR2 = new Dinero(0, 0, 0, 0, 0, 1);
	public static final Dinero NADA = new Dinero(0, 0, 0, 0, 0, 0);
}
