package dsi;

import java.io.*;

public class InfoSalida implements AutoCloseable {

	private File ficheroLocal;
	private File ficheroGlobal;

	private BufferedWriter writerLocal;
	private BufferedWriter writerGlobal;

	private String nombreECG;

	public InfoSalida(String dirSalida) throws Exception {

		// Crear directorio si no existe
		new File(dirSalida).mkdirs();

		this.ficheroGlobal = new File(dirSalida + "/todo.salida.txt");
		this.writerGlobal = new BufferedWriter(new FileWriter(ficheroGlobal, false));

	}

	public void setFicheroLocal(String nombreECG, String dirSalida) throws Exception {

		this.nombreECG = nombreECG;

		if (writerLocal != null) {
			writerLocal.close();
		}

		this.ficheroLocal = new File(dirSalida + "/" + nombreECG + ".salida.txt");

		this.writerLocal = new BufferedWriter(new FileWriter(ficheroLocal));
	}

	private void escribirLocal(String texto) throws Exception {
		writerLocal.write(texto);
		writerLocal.newLine();
		writerLocal.flush();
	}

	private void escribirGlobal(String texto) throws Exception {
		writerGlobal.write(texto != "\n" ? nombreECG + ".ecg -> " + texto : "");
		writerGlobal.newLine();
		writerGlobal.flush();
	}

	public void escribir(String texto) throws Exception {
		escribirLocal(texto);
		escribirGlobal(texto);
	}

	@Override
	public void close() throws Exception {

		if (writerLocal != null)
			writerLocal.close();

		if (writerGlobal != null)
			writerGlobal.close();
	}
}
