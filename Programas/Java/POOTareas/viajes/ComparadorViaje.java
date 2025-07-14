package viajes;
import java.util.Comparator;

public class ComparadorViaje implements Comparator<Viaje>{
	
	@Override
	public int compare(Viaje arg0, Viaje arg1) {
	int criterio1 = arg0.getRuta().compareTo(arg1.getRuta());
	
	return criterio1;
	}

}