package main;

import dsi.*;
import parser.ECGParser;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.File;
import java.nio.file.*;
import java.util.List;

public class Lanzador {

	public static void main(String[] args) {
		// Determinar directorios
		String dirEnt = (args.length > 0) ? args[0] : "./";
		String dirSal = (args.length > 1) ? args[1] : dirEnt + "/resultados/";

		try {
			Lanzador lanzador = new Lanzador();
			lanzador.ejecutar(dirEnt, dirSal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Método principal del flujo: leer ECGs, parsear e inferir */
	public void ejecutar(String dirEnt, String dirSal) throws Exception {

		File entrada = new File(dirEnt);
		if (!entrada.exists() || !entrada.isDirectory()) {
			throw new IllegalArgumentException("Directorio de entrada inválido: " + dirEnt);
		}

		// Filtrar los .ecg
		File[] ficheros = entrada.listFiles((dir, name) -> name.toLowerCase().endsWith(".ecg"));
		if (ficheros == null || ficheros.length == 0) {
			System.out.println("No se encontraron ficheros .ecg en " + dirEnt);
			return;
		}

		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		ECGParser parser = new ECGParser();

		InfoSalida info = new InfoSalida(dirSal);
		
		for (File fichero : ficheros) {

			String nombreBase = fichero.getName().replace(".ecg", "");
			info.setFicheroLocal(nombreBase, dirSal);

			List<Onda> ondas = parser.parseFile(fichero.toPath());
			// System.out.println("Ondas detectadas: " + ondas.size());
			KieSession kSession = kContainer.newKieSession("ksession-rules-dsi");
			
			// Insertamos como variable global para escribir en ficheros
			
		    kSession.setGlobal("salida", info);

			// Insertar ondas en la base de hechos
			for (Onda onda : ondas) {
				kSession.insert(onda);
			}

			// Primero ejecutamos la agenda de elctrocardiograma para calcular los
			// intervalos, segmentos,etc
			kSession.getAgenda().getAgendaGroup("asignar-ciclos").setFocus();
			kSession.fireAllRules();

			// Primero ejecutamos la agenda de elctrocardiograma para calcular los
			// intervalos, segmentos,etc
			kSession.getAgenda().getAgendaGroup("electrocardiograma").setFocus();
			kSession.fireAllRules();

			// Después ejecutamos la agenda de diagnóstico.
			kSession.getAgenda().getAgendaGroup("diagnostico").setFocus();
			kSession.fireAllRules();

			kSession.dispose();

		}
		
		info.close();
		
	}

}
