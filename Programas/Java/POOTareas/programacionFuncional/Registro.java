package programacionFuncional;
import java.time.LocalDate;

public class Registro {
	private final String usuario;
	private final LocalDate entrada; 
	
	public Registro(String usuario, LocalDate entrada) {
		this.entrada = entrada;
		this.usuario = usuario;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public LocalDate getEntrada() {
		return entrada;
	}
}
