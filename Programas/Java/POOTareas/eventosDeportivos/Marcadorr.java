package eventosDeportivos;

public class Marcadorr {
	private final int tanteoLocal;
	private final int tanteoVisitante;
	
	public Marcadorr(int tanteoLocal, int tanteoVisitante) {
		this.tanteoLocal = tanteoLocal;
		this.tanteoVisitante = tanteoVisitante;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Marcadorr other = (Marcadorr) obj;
		return tanteoLocal == other.tanteoLocal && tanteoVisitante == other.tanteoVisitante;
	}
	
	@Override
	public int hashCode() {
		int primo = 31;
		int result = 1;
		
		result = primo * result + tanteoLocal;
		result = primo * result + tanteoVisitante;
		
		return result;
	}
}
