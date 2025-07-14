package subastas;
import java.util.LinkedList;

public class Subasta {
	protected final String nombreProducto;
	protected final String propietario;
	protected boolean abierta;
	protected final LinkedList<Puja> pujas;
	protected Puja pujaMayor;
	
	public Subasta(String nombreProducto, String propietario) {
		this.nombreProducto = nombreProducto;
		this.propietario = propietario;
		this.abierta = true;
		this.pujas = new LinkedList<Puja>();
		this.pujaMayor = null;		
	}
	
	public String getNombreProducto() {
		return nombreProducto;
	}
	
	public String getPropietario() {
		return propietario;
	}
	
	public boolean isAbierta() {
		return abierta;
	}
	
	public LinkedList<Puja> getPujas(){
		return new LinkedList<>(pujas);
	}
	
	public Puja getPujaMayor() {
		return pujaMayor;
	}
	
	public boolean pujar(String pujador, double cantidad) {
		if((abierta == true) && (propietario != pujador) && ((pujaMayor == null) || (cantidad > pujaMayor.getCantidad()))) {
			Puja puja = new Puja(pujador, cantidad);
			pujaMayor = new Puja(pujador, cantidad);
			pujas.add(puja);
			return true;
		}
		return false;
	}
	
	public boolean pujar(String pujador) {
		if(pujaMayor!=null) {
			Puja puja = new Puja(pujador, pujaMayor.getCantidad()+1);
			pujaMayor = new Puja(pujador, pujaMayor.getCantidad()+1);
			pujas.add(puja);
			return true;
		}
		if(pujaMayor == null) {
			Puja puja = new Puja(pujador,1);
			pujaMayor = new Puja(pujador,1);
			pujas.add(puja);
			return true;
		}
		return false;
	}
	
	public boolean ejecutar() {
		if((pujas != null) && (abierta == true)) {
			abierta = false;
			return true;
		}
		return false;
	}
	
}
