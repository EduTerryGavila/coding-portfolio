package dsi;

public class IntervaloQT extends Intervalo {

	public IntervaloQT(OndaQ ondaInicio, OndaT ondaFin) {
		super(ondaInicio, ondaFin);
	}
	
	@Override
	public int getDuracion() {
		int a = getOndaFin().gettFin() - getOndaInicio().gettInicio();
		//System.out.println("Duracion QT: " + a);
		return a;
	}

	@Override
	public String toString() {
		return "IntervaloQT [getDuracion()=" + getDuracion() + ", getCiclo()=" + getCiclo() + "]";
	}

	
}
