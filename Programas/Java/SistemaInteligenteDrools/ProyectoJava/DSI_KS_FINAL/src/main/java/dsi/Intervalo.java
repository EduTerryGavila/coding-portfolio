package dsi;

public abstract class Intervalo {

	private Onda ondaInicio, ondaFin;
	private int ciclo;

	public Intervalo(Onda ondaInicio, Onda ondaFin) {
		this.ondaInicio = ondaInicio;
		this.ondaFin = ondaFin;
		this.ciclo = ondaInicio.getCiclo();
	}
	
	public abstract int getDuracion();

	public Onda getOndaInicio() {
		return ondaInicio;
	}

	public Onda getOndaFin() {
		return ondaFin;
	}

	public int getCiclo() {
		return ciclo;
	}
}
