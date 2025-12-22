package dsi;

public class ComplejoQRS {
	
	private OndaQ ondaQ;
	private OndaR ondaR;
	private OndaS ondaS;
	private int ciclo;
	
	public ComplejoQRS(OndaQ ondaQ, OndaR ondaR, OndaS ondaS) {
		super();
		this.ondaQ = ondaQ;
		this.ondaR = ondaR;
		this.ondaS = ondaS;
		this.ciclo = ondaQ.getCiclo();
	}
	
	public OndaQ getOndaQ() {
		return ondaQ;
	}

	public OndaR getOndaR() {
		return ondaR;
	}

	public OndaS getOndaS() {
		return ondaS;
	}

	public void setOndaQ(OndaQ ondaQ) {
		this.ondaQ = ondaQ;
	}

	public void setOndaR(OndaR ondaR) {
		this.ondaR = ondaR;
	}

	public void setOndaS(OndaS ondaS) {
		this.ondaS = ondaS;
	}
	
	public int getCiclo() {
		return ciclo;
	}
	
	public int getDuracion() {
		return ondaS.gettFin() - ondaQ.gettInicio();
	}
	

}
