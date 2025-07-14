package subastas;

public class SubastaLimitada extends Subasta{
	
	private final int numMaxPujas;
	private int numPujasPendientes;
	
	public SubastaLimitada(String nombreProducto, String propietario, int numMaxPujas) {
		super(nombreProducto, propietario);
		this.numMaxPujas = numMaxPujas;
		this.numPujasPendientes = numMaxPujas;
	}
	
	public boolean pujar(String pujador, double cantidad) {
		if(numPujasPendientes < 0 && super.pujar(pujador, cantidad) == true) {
			numPujasPendientes --;
			if(numPujasPendientes == 0) {
				ejecutar();
			}
			return true;
		}
		if(numPujasPendientes == 0) {
			ejecutar();
		}
		return false;
	}
	
	public int getNumPujasPendientes() {
		return numPujasPendientes;
	}
	
	public int getNumMaxPujas() {
		return numMaxPujas;
	}
}
