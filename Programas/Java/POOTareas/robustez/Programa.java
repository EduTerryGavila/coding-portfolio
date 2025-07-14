package robustez;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class Programa {
	public static void main(String[] args) {
		LocalDate hoy = LocalDate.now();

		// Empleado con 2 años de trabajo en la empresa
		Funcionario e1 = new Funcionario("Andrés", LocalDate.of(1996, hoy.getMonth(), hoy.getDayOfMonth()),
				LocalDate.of(2021, 1, 1), Funcionario.SMI, CategoriaProfesional.C1);

		// Empleado contratado hoy
		Funcionario e2 = new Funcionario("Elvira", LocalDate.of(1998, hoy.getMonth(), hoy.getDayOfMonth()),
				LocalDate.now(), 1500, CategoriaProfesional.B);

		// Empleados con 25 años de trabajo en la empresa
		Funcionario e3 = new Funcionario("Rosario", LocalDate.of(1973, hoy.getMonth(), hoy.getDayOfMonth()),
				LocalDate.of(1998, 1, 1), 2800, CategoriaProfesional.A1);

		Funcionario e4 = new Funcionario("Rogelio", LocalDate.of(1973, Month.MAY, 10), LocalDate.of(1998, 6, 1), 2500,
				CategoriaProfesional.A2);

		// Empleado con 65 años
		Funcionario e5 = new Funcionario("Amaya", LocalDate.of(1958, Month.FEBRUARY, 24), LocalDate.of(1988, 1, 1),
				3500, CategoriaProfesional.A1);

		LinkedList<Funcionario> funcionarios = new LinkedList<>();

		Collections.addAll(funcionarios, e1, e2, e3, e4, e5);

		// Escribe el mensaje "Felicidades {nombre}" para todos
		// los funcionarios que sea hoy su cumpleaños, ordenados
		// por orden alfabético
		
	
		List<String> gg = funcionarios.stream()
				.filter(n -> n.getNacimiento().getDayOfMonth() == LocalDate.now().getDayOfMonth() 
				&& n.getNacimiento().getMonth() == LocalDate.now().getMonth())
				.sorted(Comparator.comparing(Funcionario::getNombre))
				.map(f -> "Felicidades " + f.getNombre())
				.collect(Collectors.toList());
		gg.forEach(System.out::println);
		
		// Sube un 5% el sueldo a todos los funcionarios con al menos 25 años de
		// servicio. Muestra la colección de usuarios para comprobar el cambio.
		
		funcionarios.stream()
				.filter(n -> n.getAnyosServicio() >= 25)
				.forEach(f -> f.subirSueldo(5));
		System.out.println("\nFuncionarios despues del aumento: ");
		funcionarios.forEach(System.out::println);
		
		// Comprueba que no exista ningún funcionario que cobre menos del SMI
		
		List<String> gg2 = funcionarios.stream()
				.filter(n -> n.getSalario() < Funcionario.SMI)
				.map(Funcionario::getNombre)
				.collect(Collectors.toList());
		System.out.println(gg2.toString());

		// Muestra los funcionarios que cobran más del SMI ordenados
		// por categorías y a igualdad de categoría ordenador según
		// los años de servicio (de menos a mas).
		
		List<String> gg3 = funcionarios.stream()
				.filter(n -> n.getSalario() > Funcionario.SMI)
				.sorted(Comparator.comparing(Funcionario::getCategoria)
						.thenComparing(Funcionario::getAnyosServicio))
				.map(Funcionario::getNombre)
				.collect(Collectors.toList());
		System.out.println(gg3.toString());
		
		// Muestra en la consola cuantos empleados pueden jubilarse (65 años)
		
		List<String> gg4 = funcionarios.stream()
				.filter(n -> n.getEdad() >= 65)
				.map(Funcionario::getNombre)
				.collect(Collectors.toList());
		System.out.println(gg4.toString());
	}

}
