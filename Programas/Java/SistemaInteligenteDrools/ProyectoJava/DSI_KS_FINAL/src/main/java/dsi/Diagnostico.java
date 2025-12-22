package dsi;

public class Diagnostico {

	public enum TipoDiagnostico {
		TAQUICARDIA,
		BRADICARDIA,
		HIPOCALCEMIA,
		HIPOPOTASEMIA,
		CONTRACCION_VENTRICULAR_PREMATURA,
		ISQUEMIA_CORONARIA,
		INFARTO_AGUDO_MIOCARDIO,
		SANO
	}

	private TipoDiagnostico tipo;
	private String razon;

	public Diagnostico(TipoDiagnostico tipo) {
		this.tipo = tipo;
	}

	public Diagnostico(TipoDiagnostico tipo, String razon ) {
		this.tipo = tipo;
		this.razon = razon;
	}

	public TipoDiagnostico getTipo() {
		return tipo;
	}

	public String getRazon() {
		return razon;
	}

	@Override
	public String toString() {
		if (tipo == TipoDiagnostico.SANO) return "El paciente está sano";
		String s = tipo + " debido a " + razon;
		return s;
	}
}
