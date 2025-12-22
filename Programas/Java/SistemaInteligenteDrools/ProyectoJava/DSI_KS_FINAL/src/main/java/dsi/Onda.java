package dsi;

public abstract class Onda {

	private int tInicio, tFin, ciclo;
	private float picoMaximo;
	private boolean procesada;
	
	public Onda(int tInicio, int tFin, float picoMaximo) {
		super();
		this.tInicio = tInicio;
		this.tFin = tFin;
		this.picoMaximo = picoMaximo;
		this.procesada = false;
	}

	public int gettInicio() {
		return tInicio;
	}

	public void settInicio(int tInicio) {
		this.tInicio = tInicio;
	}

	public int gettFin() {
		return tFin;
	}

	public void settFin(int tFin) {
		this.tFin = tFin;
	}

	public int getCiclo() {
		return ciclo;
	}

	public void setCiclo(int ciclo) {
		this.ciclo = ciclo;
	}

	public float getPicoMaximo() {
		return picoMaximo;
	}

	public void setPicoMaximo(float picoMaximo) {
		this.picoMaximo = picoMaximo;
	}
	
	public void setProcesada(boolean procesada) {
		this.procesada = procesada;
	}
	
	public boolean getProcesada() {
		return procesada;
	}

	@Override
	public String toString() {
		return "Onda [tInicio=" + tInicio + ", tFin=" + tFin + ", ciclo=" + ciclo + ", picoMaximo=" + picoMaximo + "]";
	}
	
	
	
}
