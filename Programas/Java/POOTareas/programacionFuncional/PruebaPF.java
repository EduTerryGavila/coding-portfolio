package programacionFuncional;
import java.util.List;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PruebaPF {
	public static void main(String[] args) {
		
		List<Registro> listaRegistros = new LinkedList<>();
		listaRegistros.add(new Registro("Pedro",LocalDate.of(2023, 11, 11)));
		listaRegistros.add(new Registro("Juan",LocalDate.of(2023, 11, 4)));
		listaRegistros.add(new Registro("Martina",LocalDate.of(2023, 11, 12)));
		listaRegistros.add(new Registro("Andrea",LocalDate.of(2023, 11, 4)));
		listaRegistros.add(new Registro("Pedro",LocalDate.of(2023, 11, 12)));
		
		Set<String> usuariosPost10112023 = listaRegistros.stream()
				.filter(registros -> registros.getEntrada().isAfter(LocalDate.of(2023, 11, 10)))
				.map(Registro::getUsuario)
				.collect(Collectors.toSet());
				
		System.out.println(usuariosPost10112023.toString());
				
		List<String> usuariosAlfabeticoDia12 = listaRegistros.stream()
				.filter(registro -> registro.getEntrada().getDayOfMonth() == 12)
				.map(Registro::getUsuario)
				.sorted()
				.collect(Collectors.toList());
		System.out.println(usuariosAlfabeticoDia12.toString());
				
		long numero = listaRegistros.stream()
				.filter(n -> !n.getEntrada().isBefore(LocalDate.of(2023, 11, 4)) && !n.getEntrada().isAfter(LocalDate.of(2023, 11, 12)))
				.count();
		System.out.println(numero);		
		
		LocalDate dia = LocalDate.of(2023, 11, 4);
		boolean consulta = listaRegistros.stream()
				.anyMatch(n -> n.getEntrada().equals(dia));
		System.out.println(consulta);
		
		doIf(listaRegistros, reg -> reg.getEntrada().equals(dia), System.out::println);	
		
		List<String> strings = Arrays.asList("one", "two", "three", "four");
        doIf(strings, str -> str.length() == 3, System.out::println);
	}
		
public static <T> void doIf(Collection<T> collection, Predicate<T> condition, Consumer<T> action) {
    for (T item : collection) {
        if (condition.test(item)) {
            action.accept(item);
        }
    }
}
}
