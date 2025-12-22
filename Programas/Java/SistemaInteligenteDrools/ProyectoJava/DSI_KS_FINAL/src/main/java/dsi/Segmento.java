package dsi;

public abstract class Segmento {

	private Onda ondaInicio, ondaFin;
	private int ciclo;

	public Segmento(Onda ondaInicio, Onda ondaFin) {
		this.ondaInicio = ondaInicio;
		this.ondaFin = ondaFin;
		this.ciclo = ondaInicio.getCiclo();
	}
	
	public int getDuracion() {
		return ondaFin.gettInicio() - ondaInicio.gettFin();
	}

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
