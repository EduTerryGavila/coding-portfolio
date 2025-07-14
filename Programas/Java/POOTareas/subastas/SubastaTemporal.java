package subastas;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SubastaTemporal extends SubastaLimitada{
	private int periodoPuja;
	private LocalDateTime tiempoTranscurrido;
	
	public SubastaTemporal(String nombreProducto, String propietario, int periodoPuja) {
		super(nombreProducto, propietario, periodoPuja*2);
		this.periodoPuja = periodoPuja;
		this.tiempoTranscurrido = LocalDateTime.now();
		
	}

	public int getPeriodoRestante() {
		return periodoPuja - (int)ChronoUnit.HOURS.between(tiempoTranscurrido,LocalDateTime.now());
	}
	
	public int getPeriodoPuja() {
		return periodoPuja;
	}
	
	public boolean pujar(String pujador, double cantidad) {
		if(getPeriodoRestante() > 0 && super.pujar(pujador, cantidad) == true) {
			return true;
		}
		return false;
	}
	
	public boolean ejecutar() {
		if(getPeriodoRestante() <=0 && super.ejecutar() == true) {
			return true;
		}
		return false;
	}
}
