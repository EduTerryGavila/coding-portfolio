package viajes;

import java.util.Comparator;

public class ComparadorReserva implements Comparator<Reserva> {

    @Override
    public int compare(Reserva reserva1, Reserva reserva2) {
        int criterioUsuario = reserva1.getUsuario().compareTo(reserva2.getUsuario());
        if (criterioUsuario != 0) {
            return criterioUsuario;
        }
        return reserva1.getFechaReserva().compareTo(reserva2.getFechaReserva());
    }
}
