package es.um.redes.nanoFiles.logic;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import es.um.redes.nanoFiles.tcp.client.NFConnector;
import es.um.redes.nanoFiles.tcp.message.PeerMessage;
import es.um.redes.nanoFiles.tcp.message.PeerMessageOps;
import es.um.redes.nanoFiles.application.NanoFiles;

import es.um.redes.nanoFiles.tcp.server.NFServer;
import es.um.redes.nanoFiles.util.FileDigest;
import es.um.redes.nanoFiles.util.FileInfo;

public class NFControllerLogicP2P {
	/*
	 * TODO: Se necesita un atributo NFServer que actuará como servidor de ficheros
	 * de este peer
	 */
	private NFServer fileServer = null;
	private Thread serverThread = null;

	public final int CHUNK_SIZE = 16384; // 2MiB
	
	protected NFControllerLogicP2P() {
	}

	/**
	 * Método para ejecutar un servidor de ficheros en segundo plano. Debe arrancar
	 * el servidor en un nuevo hilo creado a tal efecto.
	 * 
	 * @return Verdadero si se ha arrancado en un nuevo hilo con el servidor de
	 *         ficheros, y está a la escucha en un puerto, falso en caso contrario.
	 * 
	 */
	protected boolean startFileServer() {
		boolean serverRunning = false;
		/*
		 * Comprobar que no existe ya un objeto NFServer previamente creado, en cuyo
		 * caso el servidor ya está en marcha.
		 */
		if (fileServer != null) {
			System.err.println("File server is already running");
			return false;
		}

			/*
			 * TODO: (Boletín Servidor TCP concurrente) Arrancar servidor en segundo plano
			 * creando un nuevo hilo, comprobar que el servidor está escuchando en un puerto
			 * válido (>0), imprimir mensaje informando sobre el puerto de escucha, y
			 * devolver verdadero. Las excepciones que puedan lanzarse deben ser capturadas
			 * y tratadas en este método. Si se produce una excepción de entrada/salida
			 * (error del que no es posible recuperarse), se debe informar sin abortar el
			 * programa
			 * 
			 */
		try {
		       // Crear el servidor
		       fileServer = new NFServer();

		       // Comprobar que está escuchando en un puerto válido
		       int port = fileServer.getPort(); 
		       if (port <= 0) {
		           System.err.println("Invalid port: " + port);
		           return false;
		       }	
			
		    // Crear y lanzar el hilo
		        serverThread = new Thread(fileServer);
		        serverThread.start();

		        System.out.println("File server started and listening on port " + port);
		        serverRunning = true;

		    } catch (IOException e) {
		        System.err.println("Error starting file server: " + e.getMessage());
		        // No abortamos 
		    } catch (Exception e) {
		        System.err.println("Unexpected error starting file server: " + e.getMessage());
		    }

		    return serverRunning;	
			
	}

	protected void testTCPServer() {
		assert (NanoFiles.testModeTCP);
		/*
		 * Comprobar que no existe ya un objeto NFServer previamente creado, en cuyo
		 * caso el servidor ya está en marcha.
		 */
		assert (fileServer == null);
		try {

			fileServer = new NFServer();
			/*
			 * (Boletín SocketsTCP) Inicialmente, se creará un NFServer y se ejecutará su
			 * método "test" (servidor minimalista en primer plano, que sólo puede atender a
			 * un cliente conectado). Posteriormente, se desactivará "testModeTCP" para
			 * implementar un servidor en segundo plano, que se ejecute en un hilo
			 * secundario para permitir que este hilo (principal) siga procesando comandos
			 * introducidos mediante el shell.
			 */
			fileServer.test();
			// Este código es inalcanzable: el método 'test' nunca retorna...
		} catch (IOException e1) {
			e1.printStackTrace();
			System.err.println("Cannot start the file server");
			fileServer = null;
		}
	}

	public void testTCPClient() {

		assert (NanoFiles.testModeTCP);
		/*
		 * (Boletín SocketsTCP) Inicialmente, se creará un NFConnector (cliente TCP)
		 * para conectarse a un servidor que esté escuchando en la misma máquina y un
		 * puerto fijo. Después, se ejecutará el método "test" para comprobar la
		 * comunicación mediante el socket TCP. Posteriormente, se desactivará
		 * "testModeTCP" para implementar la descarga de un fichero desde múltiples
		 * servidores.
		 */

		try {
			NFConnector nfConnector = new NFConnector(new InetSocketAddress(NFServer.PORT));
			nfConnector.test();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Método para descargar un fichero del peer servidor de ficheros
	 * 
	 * @param serverAddressList       La lista de direcciones de los servidores a
	 *                                los que se conectará
	 * @param targetFileNameSubstring Subcadena del nombre del fichero a descargar
	 * @param localFileName           Nombre con el que se guardará el fichero
	 *                                descargado
	 */

	protected boolean downloadFileFromServers(InetSocketAddress[] serverAddressList, FileInfo targetFileInfo,
	        String localFileName) {
	    boolean downloaded = false;
	    //si no hay servidores 
	    if (serverAddressList.length == 0) {
	        System.err.println("* Cannot start download - No list of server addresses provided");
	        return false;
	    }
	 // Ruta completa del fichero descargado
	    String filePath = NanoFiles.sharedDirname + "/" + localFileName;
	    File localFile = new File(filePath);
	 // Si el fichero ya existe en el directorio
	    if (localFile.exists()) {
	        System.out.println("File already exists - not downloading");
	        return false;
	    }

	    RandomAccessFile file = null;
	    try {
	        file = new RandomAccessFile(localFile, "rw");
	    } catch (IOException e) {
	        System.err.println("Could not create file: " + e.getMessage());
	        return false;
	    }

	    ArrayList<NFConnector> connectors = new ArrayList<>(); // Lista de conexiones a peers activos
	    HashMap<NFConnector, Integer> chunkCounter = new HashMap<>();// Contador que actualiza los trozos enviados por cada peer
	 //  conectamos con cada dirección de la lista

	    for (InetSocketAddress addr : serverAddressList) {
	        try {
	            NFConnector nfc = new NFConnector(addr);
	            connectors.add(nfc);
	            chunkCounter.put(nfc, 0);
	        } catch (IOException e) {
	            System.out.println("Could not contact peer: " + addr);
	        }
	    }

	    if (connectors.isEmpty()) {
	        System.out.println("No available peers to connect.");
	        return false;
	    }

	    // Comprobar que los peers tienen el archivo
	    PeerMessage fileRequest = new PeerMessage(PeerMessageOps.OPCODE_REQUEST_FILE, targetFileInfo.getFileHash());
	    Iterator<NFConnector> it = connectors.iterator();

	    while (it.hasNext()) {
	        NFConnector conn = it.next();
	        try {
	            conn.sendMessage(fileRequest);
	            PeerMessage response = conn.receiveMessage();

	            if (response.getOpcode() != PeerMessageOps.OPCODE_REQUEST_FILE_ACCEPTED) {
	                System.out.println("Peer does not have file: " + conn.getServerAddr());
	                it.remove();
	            }
	        } catch (IOException e) {
	            System.out.println("Peer failed during handshake: " + conn.getServerAddr());
	            it.remove();
	        }
	    }

	    if (connectors.size() > 0) {
	        System.out.println("The following peers have the file:");
	        for (NFConnector conn : connectors) {
	            System.out.println("- " + conn.getServerAddr());
	    } } else {
	        System.out.println("No peers left with the file - stopping.");
	        return false;
	    }
	    //trozos que tendra el fichero
	    int totalChunks = (int) (targetFileInfo.getFileSize() / CHUNK_SIZE);
	    if (targetFileInfo.getFileSize() % CHUNK_SIZE != 0) {
	        totalChunks += 1;
	    }
	    // Descargar cada chunk uno a uno
	    System.out.println("Downloading " + totalChunks + " chunks from " + connectors.size() + " peers");
	    // si el ultimo trozo es menor que los demas 
	    for (int chunk = 0; chunk < totalChunks;) {
	        NFConnector conn = connectors.get(chunk % connectors.size());

	        int chunkSize = (chunk == totalChunks - 1)
	            ? (int) (targetFileInfo.getFileSize() % CHUNK_SIZE)
	            : CHUNK_SIZE;

	        PeerMessage chunkRequest = new PeerMessage(PeerMessageOps.OPCODE_REQUEST_CHUNK,
	            (long) chunk * CHUNK_SIZE, chunkSize);

	        PeerMessage response = null;
	        try {
	            conn.sendMessage(chunkRequest);
	            response = conn.receiveMessage();
	        } catch (IOException e) {
	            System.out.println("Peer failed during chunk transfer: " + conn.getServerAddr());
	            connectors.remove(conn);
	            if (connectors.isEmpty()) {
	                System.out.println("All peers failed. Aborting download.");
	                return false;
	            }
	            continue;
	        }

	        if (response.getOpcode() != PeerMessageOps.OPCODE_CHUNK) {
	            System.out.println("Unexpected response from peer. Skipping.");
	            connectors.remove(conn);
	            continue;
	        }
	        // Escribir trozo recibido en su posición en el fichero
	        try {
	            file.seek(response.getOffset());
	            file.write(response.getChunkData());
	        } catch (IOException e) {
	            System.err.println("Error writing chunk: " + e.getMessage());
	            return false;
	        }

	        chunk++;
	        chunkCounter.put(conn, chunkCounter.get(conn) + 1);
	    }

	    try {
	        file.close();
	    } catch (IOException e) {
	        System.err.println("Error closing file");
	        return false;
	    }

	    // Verificar integridad del fichero descargado
	    String downloadedHash = FileDigest.computeFileChecksumString(filePath);
	    if (!downloadedHash.equals(targetFileInfo.getFileHash())) {
	        System.out.println("File corrupted - deleting...");
	        localFile.delete();
	        return false;
	    }

	    System.out.println("File downloaded successfully.");
	    System.out.println("client\t\tchunks");
	    for (NFConnector conn : chunkCounter.keySet()) {
	        System.out.println(conn.getServerAddr() + "\t" + chunkCounter.get(conn));
	    }

	    downloaded = true;
	    return downloaded;
	}

	/**
	 * Método para obtener el puerto de escucha de nuestro servidor de ficheros
	 * 
	 * @return El puerto en el que escucha el servidor, o 0 en caso de error.
	 */
	protected int getServerPort() {
		/*
		 * TODO: Devolver el puerto de escucha de nuestro servidor de ficheros
		 */

		return fileServer.getPort();
		}

	/**
	 * Método para detener nuestro servidor de ficheros en segundo plano
	 * 
	 */
	protected void stopFileServer() {
		/*
		 * TODO: Enviar señal para detener nuestro servidor de ficheros en segundo plano
		 */
		fileServer.terminate();


	}

	protected boolean serving() {
		if (fileServer == null)
			return false;
		
		return fileServer.isVivo();

	}

	protected boolean uploadFileToServer(FileInfo matchingFile, String uploadToServer) {
		boolean result = false;

		return result;
	}

}
