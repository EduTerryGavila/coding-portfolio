package subastas;

public class SubastaMinima extends Subasta{
	private int cantidadMax;
	
	public SubastaMinima(String nombreProducto, String propietario, int cantidadMax) {
		super(nombreProducto, propietario);
		this.cantidadMax = cantidadMax;
	}
	
	public int getCantidadMax() {
		return cantidadMax;
	}
	
	public void setCantidadMax(int cantidadMax) {
		this.cantidadMax = cantidadMax;
	}
	
	public void cerrarSubasta() {
		abierta = false;
	}
	
	public boolean ejecutar() {
		if(pujaMayor != null && pujaMayor.getCantidad() >= cantidadMax && super.ejecutar() == true) {
			return true;
		}
		return false;
	}
}
