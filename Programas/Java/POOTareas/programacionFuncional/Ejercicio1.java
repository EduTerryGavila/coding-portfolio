package programacionFuncional;
import java.util.Random;
import java.util.stream.Collectors;

import robustez.Funcionario;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.util.LinkedHashMap;

public class Ejercicio1 {
	public static void main(String[] args) {
		Random random = new Random();
	    List<Integer> randomNumbers = random.ints(20, 0, 100).boxed().collect(Collectors.toList());
	    
	    List <String> hexNumbers = randomNumbers.stream()
	    .map(Integer::toHexString)
	    .collect(Collectors.toList());
	    System.out.println("Lista de numeros aleatorios en hexadecial:\n" + hexNumbers.toString());
	    
	    List <String> hexNumbersAlfa = hexNumbers.stream()
	    	    .sorted()
	    	    .collect(Collectors.toList());
	    System.out.println("\nLista de numeros aleatorios en hexadecial y orden alfabetico:\n" + hexNumbersAlfa.toString());
	    	   
	    long numElem = randomNumbers.stream()
	    .filter(n -> n%2==0)
	    .count();
	    System.out.println("\nNumero elementos pares: "+ numElem);
	    
	    Map<Integer, String> mapaHex = randomNumbers.stream()
	    .collect(Collectors.toMap(
	    num -> num,
	    Integer::toHexString,
	    (existing, replacement) -> existing, // Keep the existing value in case of duplicate keys
        LinkedHashMap::new 
	    ));
	    System.out.println("\nNumero a mapa hexadecimal: \n" + mapaHex.toString());
	    
	    List<Integer> num5560 = randomNumbers.stream()
	    .filter(nums -> nums > 55 && nums < 60)
	    .collect(Collectors.toList());
	    System.out.println("\nLos numeros entre 55 y 60 son:" + num5560.toString() );
	    
		
	}
}
