package es.um.redes.nanoFiles.tcp.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import es.um.redes.nanoFiles.application.NanoFiles;
import es.um.redes.nanoFiles.tcp.message.PeerMessage;
import es.um.redes.nanoFiles.tcp.message.PeerMessageOps;
import es.um.redes.nanoFiles.util.FileInfo;

public class NFServer implements Runnable {

	public static final int PORT = 10000;

	private ServerSocket serverSocket = null;

	private static boolean servervivo = false;
	private int port;
	private static LinkedList<Socket> sockets = new LinkedList<Socket>();	//lista para cerrar todos los sockes con quit. 

	public NFServer() throws IOException {
		/*
		 * TODOhecho: (Boletín SocketsTCP) Crear una direción de socket a partir del
		 * puerto especificado (PORT)
		 */
		//ligar el socket a un puerto aleatorio
			serverSocket = new ServerSocket();
			InetSocketAddress serverSocketAddress = new InetSocketAddress(port);
			serverSocket.bind(serverSocketAddress);
			port = serverSocket.getLocalPort();
			servervivo = true;
	        System.out.println("Servidor ligado al puerto especificado: " + port);
	   
			servervivo=true;
		
	}
	public boolean isVivo() {
		return servervivo;
	}
	public void terminate() {
		servervivo = false;
		sockets.forEach((s) -> {
				try { s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				});		
		try {	
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int getPort() {
		return port;
	}
	/**
	 * Método para ejecutar el servidor de ficheros en primer plano. Sólo es capaz
	 * de atender una conexión de un cliente. Una vez se lanza, ya no es posible
	 * interactuar con la aplicación.
	 * 
	 * @throws IOException
	 * 
	 */
	public void test() {
		if (serverSocket == null || !serverSocket.isBound()) {
			System.err.println(
					"[fileServerTestMode] Failed to run file server, server socket is null or not bound to any port");
			return;
		} else {
			System.out
					.println("[fileServerTestMode] NFServer running on " + serverSocket.getLocalSocketAddress() + ".");
		}

		while (true) {
			/*
			 * TODOhecho: (Boletín SocketsTCP) Usar el socket servidor para esperar
			 * conexiones de otros peers que soliciten descargar ficheros.
			 */
			// Esperar peticiones de conexion
//			Socket socket = null;
//			try {
//				socket = serverSocket.accept();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			// crear socket para la conexion con el cliente
//			System.out
//					.println("\nNew client connected: " + socket.getInetAddress().toString() + ":" + socket.getPort());
//
//			/*
//			 * TODOhecho: (Boletín SocketsTCP) Tras aceptar la conexión con un peer cliente, la
//			 * comunicación con dicho cliente para servir los ficheros solicitados se debe
//			 * implementar en el método serveFilesToClient, al cual hay que pasarle el
//			 * socket devuelto por accept.
//			 */
//			//esperar mensaje de cliente
//			
//			//probar con un mensaje binario que hemos construido
//			PeerMessage msgOut = new PeerMessage();
//			PeerMessage msgIn = new PeerMessage();
//			byte OpcodeDefault=1;
//			String cadenaHashCodeDefault = "hashcode download";
//			long inicioDefault = 123456;
//			long finDefault = 246810;
//			byte OpcodeOut = 0;
//			byte OpcodeIn=0;
//			String cadenaHashCodeOUT = "hola";
//			long inicioOUT = 0;
//			long finOUT = 0;
//			try {
//				DataInputStream dis = new DataInputStream(socket.getInputStream());
//				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
//				
//				msgIn = PeerMessage.readMessageFromInputStream(dis);
//				OpcodeIn = msgIn.getOpcode();
//				String cadenaHashCodeIN = msgIn.getHashCode();
//				long inicioIN = msgIn.getInicioFrag();
//				long finIN = msgIn.getFinFrag();
//				System.out.println("mensaje recibido: ");
//				
//				
//				System.out.println("opcodeIn: " + OpcodeIn);
//				System.out.println("hashcodeIn: " + cadenaHashCodeIN);
//				System.out.println("inicioIn: " + inicioIN);
//				System.out.println("finIn: " + finIN);
//				
//				System.out.println("comprobando si es igual para mandar de vuelta");
//				if (OpcodeDefault != OpcodeIn) {
//					System.err.println("Opcode does not match!");
//				} else {
//					System.out.println("Opcode actually match!");
//					System.out.println("caso: OPCODE_DOWNLOAD en server ");
//					if (cadenaHashCodeIN.equals(cadenaHashCodeDefault))
//						System.out.println("cadena hashcode coincide:" + cadenaHashCodeIN);
//					else {
//						System.err.println("cadena hashcode no coincide:");
//						System.err.println(
//								"cadenaHashCodeDefault: " + cadenaHashCodeDefault + " --- " + "cadenaHashCodeIN: " + cadenaHashCodeIN);
//					}
//					if (inicioIN == inicioDefault)
//						System.out.println("inicio frag coincide:" + inicioIN);
//					if (finIN == finDefault)
//						System.out.println("fin frag coincide:" + finIN);
//				
//				OpcodeOut=OpcodeIn;
//				cadenaHashCodeOUT=cadenaHashCodeIN;
//				inicioOUT=inicioIN;
//				finOUT=finIN;
//					
//					
//				msgOut.setOpcode(OpcodeOut);
//				msgOut.setHashCode(cadenaHashCodeOUT);
//				msgOut.setInicioFrag(inicioOUT);
//				msgOut.setfinFrag(finOUT);
//				
//				
//				msgOut.writeMessageToOutputStream(dos);
//				System.out.println("mandando msgOut to cliente:");
//				
//			}} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			
			
			/*
			System.out.println("\nConnection OK. esperando mensaje de cliente...");
			int dataFromClient = 0;
			int dataToClient = 0;
			//esperar mensaje de cliente
			
			try {
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				dataFromClient = dis.readInt();
				int mensaje= dataFromClient;
				System.out.println("mensaje recibido: "+mensaje);
				// Send response
				
				dataToClient = mensaje;
				dos.writeInt(dataToClient);
				System.out.print("\nEnviando respuesta: "+ dataToClient);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			//para testear lo comento serveFilesToClient(socket);

		}
	}

	/**
	 * Método que ejecuta el hilo principal del servidor en segundo plano, esperando
	 * conexiones de clientes.
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		/*
		 * TODO: (Boletín SocketsTCP) Usar el socket servidor para esperar conexiones de
		 * otros peers que soliciten descargar ficheros
		 */
		/*
		 * TODO: (Boletín SocketsTCP) Al establecerse la conexión con un peer, la
		 * comunicación con dicho cliente se hace en el método
		 * serveFilesToClient(socket), al cual hay que pasarle el socket devuelto por
		 * accept
		 */
		
			Socket clientSocket = null;		
			while (servervivo) {
				try {
					clientSocket = serverSocket.accept();
					sockets.add(clientSocket);
					NFServerThread clientThread = new NFServerThread(clientSocket);
					clientThread.start();
				} catch (IOException e) {
				    System.out.println("Error accepting client - server terminated");
				    try {
				        serverSocket.close();
				    } catch (IOException ex) {
				        System.err.println("Error closing server socket");
				    }
				    return;
				}
			}}

		/*
		 * TODO: (Boletín TCPConcurrente) Crear un hilo nuevo de la clase
		 * NFServerThread, que llevará a cabo la comunicación con el cliente que se
		 * acaba de conectar, mientras este hilo vuelve a quedar a la escucha de
		 * conexiones de nuevos clientes (para soportar múltiples clientes). Si este
		 * hilo es el que se encarga de atender al cliente conectado, no podremos tener
		 * más de un cliente conectado a este servidor.
		 */

	

	/*
	 * TODO: (Boletín SocketsTCP) Añadir métodos a esta clase para: 1) Arrancar el
	 * servidor en un hilo nuevo que se ejecutará en segundo plano 2) Detener el
	 * servidor (stopserver) 3) Obtener el puerto de escucha del servidor etc.
	 */
	
	/**
	 * Método de clase que implementa el extremo del servidor del protocolo de
	 * transferencia de ficheros entre pares.
	 * 
	 * @param socket El socket para la comunicación con un cliente que desea
	 *               descargar ficheros.
	 */
	public static void serveFilesToClient(Socket socket) {
		/*
		 * TODO: (Boletín SocketsTCP) Crear dis/dos a partir del socket
		 */

		DataInputStream dis = null;
		DataOutputStream dos = null;
		try {
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("Unable to read client message - terminate connection");
			return;
		}	
		
		FileInfo reqFileInfo = null;
		FileInputStream fileStream = null;
		RandomAccessFile uploadFile = null;
		
		while (servervivo) {
			PeerMessage peerMessage = null, messageToSend = null;
			try {
				peerMessage = PeerMessage.readMessageFromInputStream(dis);
			} catch (IOException e) {
				System.out.println("Unable to read client message - terminate connection");
				return;
			}
			
			System.out.println("Recibido mensaje del peer " + PeerMessageOps.opcodeToOperation(peerMessage.getOpcode()));
			switch (peerMessage.getOpcode()) {
			case PeerMessageOps.OPCODE_REQUEST_FILE: {
				var files = NanoFiles.db.getFiles();
				reqFileInfo = FileInfo.lookupHashSubstring(files, peerMessage.getReqFileHash());
				if (reqFileInfo == null) {
					messageToSend = new PeerMessage(PeerMessageOps.OPCODE_FILE_NOT_FOUND);
					break;
				}
				File reqFile = new File(reqFileInfo.getFilePath());
				try {
					fileStream = new FileInputStream(reqFile);
				} catch (FileNotFoundException e) {
					System.out.println("File not found" + reqFile);
					messageToSend = new PeerMessage(PeerMessageOps.OPCODE_FILE_NOT_FOUND);
					break;
				}
				messageToSend = new PeerMessage(PeerMessageOps.OPCODE_REQUEST_FILE_ACCEPTED);
				break;
				} 
			case PeerMessageOps.OPCODE_REQUEST_CHUNK: {
				if (fileStream == null) {
					messageToSend = new PeerMessage(PeerMessageOps.OPCODE_REQUEST_CHUNK_OUTOFRANGE);
					break;
				}
				
				byte[] chunk = null;
				try {
					fileStream.getChannel().position(peerMessage.getOffset());
					DataInputStream fdis = new DataInputStream(fileStream);
					// no se como hacer esto sin copiar el bufer 
					chunk = fdis.readNBytes(peerMessage.getSize());
				} catch (IOException e) {
					messageToSend = new PeerMessage(PeerMessageOps.OPCODE_REQUEST_CHUNK_OUTOFRANGE);
					break;
				}
				
				messageToSend = new PeerMessage(PeerMessageOps.OPCODE_CHUNK, peerMessage.getOffset(), chunk.length, chunk);
			} break;
			case PeerMessageOps.OPCODE_UPLOAD:{
				/*if (FileInfo.lookupHashSubstring(NanoFiles.db.getFiles(), peerMessage.getReqFileHash()) != null) {
					messageToSend = new PeerMessage(PeerMessageOps.OPCODE_FILE_ALREADY_EXISTS);
					break;
				}
				*/
				messageToSend = new PeerMessage(PeerMessageOps.OPCODE_REQUEST_FILE_ACCEPTED);
			} break;
			case PeerMessageOps.OPCODE_FILENAME_TO_SAVE:{
				try {
					uploadFile = new RandomAccessFile(NanoFiles.sharedDirname +"/"+ peerMessage.getFileName(), "rw");
				} catch (FileNotFoundException e) {
					messageToSend = new PeerMessage(PeerMessageOps.OPCODE_FILE_NOT_FOUND);
					break;
				}
				messageToSend = new PeerMessage(PeerMessageOps.OPCODE_REQUEST_FILE_ACCEPTED);
			} break;
			case PeerMessageOps.OPCODE_CHUNK:{
				try {
					uploadFile.seek(peerMessage.getOffset());
					uploadFile.write(peerMessage.getChunkData());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} break;
			case PeerMessageOps.OPCODE_STOP:{
				try {
					uploadFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			}
			
			
			if (messageToSend != null) {
				System.out.println("Enviando " + PeerMessageOps.opcodeToOperation(messageToSend.getOpcode()));
				try {
					messageToSend.writeMessageToOutputStream(dos);
				} catch (IOException e) {
					System.out.println("Unable to send message - terminate connection");
				}
			}
		}
		try {
			fileStream.close();
		} catch (IOException e) {}
		
		
	}

}