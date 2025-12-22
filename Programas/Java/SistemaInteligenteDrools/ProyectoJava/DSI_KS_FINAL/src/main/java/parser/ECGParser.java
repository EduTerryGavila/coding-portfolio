package parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dsi.*;

public class ECGParser {

	private static final Pattern PATRON_ONDA = Pattern.compile("([PQRST])\\((\\d+),(\\d+),(-?\\d+(?:\\.\\d+)?(?:[Ee][+-]?\\d+)?)\\)");

	public List<Onda> parseFile(Path filePath) throws IOException {
		List<Onda> ondas = new ArrayList<>();
		List<String> lines = Files.readAllLines(filePath);

		for (String line : lines) {
			line = line.trim();
			
			// ignoramos las lineas de la cabecera
			if (line.isEmpty() || line.startsWith("#")) {
				continue;
			}

			Matcher matcher = PATRON_ONDA.matcher(line);
			if (matcher.matches()) {
				
				String tipo = matcher.group(1);
				int inicio = Integer.parseInt(matcher.group(2));
				int fin = Integer.parseInt(matcher.group(3));
				float pico = Float.parseFloat(matcher.group(4));

				Onda nueva = crearOndaConcreta(tipo, inicio, fin, pico);
				ondas.add(nueva);
			}
		}

		return ondas;
	}

	private Onda crearOndaConcreta(String tipo, int inicio, int fin, float pico) {
		switch (tipo) {
		case "P": return new OndaP(inicio, fin, pico);
		case "Q": return new OndaQ(inicio, fin, pico);
		case "R": return new OndaR(inicio, fin, pico);
		case "S": return new OndaS(inicio, fin, pico);
		case "T": return new OndaT(inicio, fin, pico);
		default:
			throw new IllegalArgumentException("Tipo de onda desconocido: " + tipo);
		}
	}
}
