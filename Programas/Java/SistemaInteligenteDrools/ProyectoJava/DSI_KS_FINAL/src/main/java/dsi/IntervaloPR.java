package dsi;

public class IntervaloPR extends Intervalo {

	public IntervaloPR(OndaP ondaInicio, OndaQ ondaFin) {
		super(ondaInicio, ondaFin);
	}
	
	@Override
	public int getDuracion() {
		return getOndaFin().gettInicio() - getOndaInicio().gettInicio();
	}
}
