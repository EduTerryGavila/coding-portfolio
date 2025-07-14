package maquinas;

public class MaquinaExpendedora {
	private String producto;
	private double precioProducto;
	private int stock;
	private Dinero dineroAcumulado;
	private Dinero dineroTransaccion;
	
	public MaquinaExpendedora(String producto, double precioProducto, int stock) {
		this.producto = producto;
		this.precioProducto = precioProducto;
		this.stock = stock;
		dineroAcumulado = Dinero.NADA;
		dineroTransaccion = Dinero.NADA;
	}
	
	public void setProducto(String producto) {
		this.producto = producto;
	}
	
	public void setPrecioProducto(double precioProducto) {
		this.precioProducto = precioProducto;
	}
	
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public int getStock() {
		return stock;
	}
	
	public Dinero getDineroTransaccion() {
		return dineroTransaccion;
	}
	
	public Dinero getDineroAcumulado() {
		return dineroAcumulado;
	}
	
	public boolean Isvacia() {
		return stock == 0;
	}
	
	public String getProducto() {
		return producto;
	}
	
	public void insertarDinero(Dinero dinero) {
		dineroTransaccion = dineroTransaccion.sumar(dinero);
	}
	
	public Dinero devolverDinero() {
		Dinero devuelto = dineroTransaccion;
		dineroTransaccion = Dinero.NADA;
		return devuelto;
	}
	
	public boolean comprar() {
		if((Isvacia() == false) && (dineroTransaccion.getValorTotal() >= precioProducto)) {
			dineroAcumulado = dineroAcumulado.sumar(dineroTransaccion);
			stock = stock - 1;
			dineroTransaccion = Dinero.NADA;
			return true;
		}
		return false;
	}
	
	}
