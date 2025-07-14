package es.um.redes.nanoFiles.udp.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.HashSet;
import es.um.redes.nanoFiles.application.NanoFiles;
import es.um.redes.nanoFiles.tcp.server.NFServer;
import es.um.redes.nanoFiles.udp.message.DirMessage;
import es.um.redes.nanoFiles.udp.message.DirMessageOps;
import es.um.redes.nanoFiles.util.FileInfo;
import es.um.redes.nanoFiles.util.FileWithPeers;

public class NFDirectoryServer {
	/**
	 * Número de puerto UDP en el que escucha el directorio
	 */
	public static final int DIRECTORY_PORT = 6868;

	/**
	 * Socket de comunicación UDP con el cliente UDP (DirectoryConnector)
	 */
	private DatagramSocket socket = null;
	 /*
	 * TODO: Añadir aquí como atributos las estructuras de datos que sean necesarias
	 * para mantener en el directorio cualquier información necesaria para la
	 * funcionalidad del sistema nanoFilesP2P: ficheros publicados, servidores
	 * registrados, etc.
	 */
	private HashMap<String, FileWithPeers> database = new HashMap<String, FileWithPeers>();
	private HashMap<InetSocketAddress, HashSet<FileInfo>> peers = new HashMap<InetSocketAddress, HashSet<FileInfo>>();

	

	/**
	 * Probabilidad de descartar un mensaje recibido en el directorio (para simular
	 * enlace no confiable y testear el código de retransmisión)
	 */
	private double messageDiscardProbability;

	public NFDirectoryServer(double corruptionProbability) throws SocketException {
		/*
		 * Guardar la probabilidad de pérdida de datagramas (simular enlace no
		 * confiable)
		 */
		messageDiscardProbability = corruptionProbability;
		/*
		 * TODO hecho: (Boletín SocketsUDP) Inicializar el atributo socket: Crear un
		 * socket UDP ligado al puerto especificado por el argumento directoryPort en la
		 * máquina local,
		 */
		socket = new DatagramSocket(DIRECTORY_PORT); // con la el puerto del diretcorio

		/*
		 * TODO: (Boletín SocketsUDP) Inicializar atributos que mantienen el estado del
		 * servidor de directorio: ficheros, etc.)
		 */

		if (NanoFiles.testModeUDP) {
			if (socket == null) {
				System.err.println("[testMode] NFDirectoryServer: code not yet fully functional.\n"
						+ "Check that all TODOs in its constructor and 'run' methods have been correctly addressed!");
				System.exit(-1);
			}
		}
	}

	public DatagramPacket receiveDatagram() throws IOException {
		DatagramPacket datagramReceivedFromClient = null;
		boolean datagramReceived = false;
		byte[] receptionBuffer = null;
		while (!datagramReceived) {
			/*
			 * TODO hecho: (Boletín SocketsUDP) Crear un búfer para recibir datagramas y un
			 * datagrama asociado al búfer (datagramReceivedFromClient)
			 */
			receptionBuffer = new byte[DirMessage.PACKET_MAX_SIZE]; // Buffer inicializado
			datagramReceivedFromClient = new DatagramPacket(receptionBuffer, receptionBuffer.length); // Datagrama hecho
																										// con el buffer

			/*
			 * TODO hecho: (Boletín SocketsUDP) Recibimos a través del socket un datagrama
			 */
			socket.receive(datagramReceivedFromClient); // Espera el paquete del cliente

			// Vemos si el mensaje debe ser ignorado (simulación de un canal no confiable)
			double rand = Math.random();
			if (rand < messageDiscardProbability) {
				System.err.println("Directory ignored datagram from " + datagramReceivedFromClient.getSocketAddress());
			} else {
				datagramReceived = true;
				System.out.println("Directory received datagram from " + datagramReceivedFromClient.getSocketAddress()
						+ " of size " + datagramReceivedFromClient.getLength() + " bytes.");
			}

		}

		return datagramReceivedFromClient;
	}

	public void runTest() throws IOException {

		System.out.println("[testMode] Directory starting...");

		System.out.println("[testMode] Attempting to receive 'ping' message...");
		DatagramPacket rcvDatagram = receiveDatagram();
		sendResponseTestMode(rcvDatagram);

		System.out.println("[testMode] Attempting to receive 'ping&PROTOCOL_ID' message...");
		rcvDatagram = receiveDatagram();
		sendResponseTestMode(rcvDatagram);
	}

	private void sendResponseTestMode(DatagramPacket pkt) throws IOException {// MODIFICAR

		/*
		 * TODO hecho: (Boletín SocketsUDP) Construir un String partir de los datos
		 * recibidos en el datagrama pkt. A continuación, imprimir por pantalla dicha
		 * cadena a modo de depuración.
		 */
		String messageFromClient = new String(pkt.getData(), 0, pkt.getLength()); // construimos cadena con datos desde
		System.out.println("datos recibdos en sendResponseTestMode: " + messageFromClient);															// 0 hasta la longitud del mensaje
																					// eso se hace para no coger todo el
																					// buffer sino que solo el mensaje

		String welcome = new String("welcome");
		String denied = new String("denied");

		/*
		 * TODO hecho: (Boletín SocketsUDP) Después, usar la cadena para comprobar que
		 * su valor es "ping"; en ese caso, enviar como respuesta un datagrama con la
		 * cadena "pingok". Si el mensaje recibido no es "ping", se informa del error y
		 * se envía "invalid" como respuesta.
		 */

		if (messageFromClient.equals(DirMessageOps.OPERATION_PING)) {// si contiene "ping"
			
			 //DEPURACION 
			System.out.println("mensaje es ping");
			System.out.println("mandando mensaje de vuelta... (pingok)");
			 
			String messageToClient = new String(DirMessageOps.OPERATION_PINGOK);// creamos cadena con pingok
			byte[] dataToClient = messageToClient.getBytes();// generamos los bytes de la cadena
			DatagramPacket packetToClient = new DatagramPacket(dataToClient, dataToClient.length,
					pkt.getSocketAddress()); // creamos un datagrama de respuesta con el contenido, la longitud y la
												// direccion del que nos ha mandado el mensaje
			socket.send(packetToClient);// y lo mandamos
		}
		
		else if (messageFromClient.startsWith("ping&")) {// si contiene "ping"
			//DEPURACION 
			System.out.println("mensaje empieza por ping$");
			System.out.println("comprobando protocolID... ");
			 
			String[] subcadenas = messageFromClient.split("&");// separamos la cadena indicando como separador el "&"
			String protocolIDrecibido = subcadenas[1];
			if (protocolIDrecibido.equals(NanoFiles.PROTOCOL_ID)) {
				//DEOURACION 
				System.out.println("mensaje contiene 'PROTOCOL_ID' y coincide");
				System.out.println("mandando mensaje de vuelta... (welcome)"); // si coincide con el
																								// PROTOCOL_ID una vez
																								// este mismo extraido
				String messageToClient = new String(welcome);// creamos cadena con pingok
				byte[] dataToClient = messageToClient.getBytes();// generamos los bytes de la cadena
				DatagramPacket packetToClient = new DatagramPacket(dataToClient, dataToClient.length,
						pkt.getSocketAddress()); // creamos un datagrama de respuesta con el contenido, la longitud y la
													// direccion del que nos ha mandado el mensaje
				socket.send(packetToClient);// y lo mandamos
			} else {
				 //DEPURACION
				System.out.println("mensaje contiene 'PROTOCOL_ID' y NO coincide");
				System.out.println("mandando mensaje de vuelta... (denied)"); // si no coincide con el
																								// PROTOCOL_ID una vez
																								// este mismo extraido
				String messageToClient = new String(denied);// creamos cadena con pingok
				byte[] dataToClient = messageToClient.getBytes();// generamos los bytes de la cadena
				DatagramPacket packetToClient = new DatagramPacket(dataToClient, dataToClient.length,
						pkt.getSocketAddress()); // creamos un datagrama de respuesta con el contenido, la longitud y la
													// direccion del que nos ha mandado el mensaje
				socket.send(packetToClient);// y lo mandamos
			}

		} else {// si no coincide con "ping"
			
			 //DEPURACION 
			System.out.println("mensaje NO es ping ni ping&PROTOCOL_ID");
			System.out.println("mandando mensaje de Error...(invalid)");
			 
			String messageToClientErr = new String("invalid");// creamos cadena con invalid
			byte[] dataToClientErr = messageToClientErr.getBytes();// generamos los bytes de la cadena
			DatagramPacket packetToClientErr = new DatagramPacket(dataToClientErr, dataToClientErr.length,
					pkt.getSocketAddress());// creamos un datagrama de respuesta
			socket.send(packetToClientErr);// y lo mandamos
		}

		/*
		 * TODO hecho: (Boletín Estructura-NanoFiles) Ampliar el código para que, en el
		 * caso de que la cadena recibida no sea exactamente "ping", comprobar si
		 * comienza por "ping&" (es del tipo "ping&PROTOCOL_ID", donde PROTOCOL_ID será
		 * el identificador del protocolo diseñado por el grupo de prácticas (ver
		 * NanoFiles.PROTOCOL_ID). Se debe extraer el "protocol_id" de la cadena
		 * recibida y comprobar que su valor coincide con el de NanoFiles.PROTOCOL_ID,
		 * en cuyo caso se responderá con "welcome" (en otro caso, "denied").
		 */

	}

	public void run() throws IOException {

		System.out.println("Directory starting...");

		while (true) { // Bucle principal del servidor de directorio
			DatagramPacket rcvDatagram = receiveDatagram();

			sendResponse(rcvDatagram);

		}
	}

	private void sendResponse(DatagramPacket pkt) throws IOException {
		/*
		 * TODO: (Boletín MensajesASCII) Construir String partir de los datos recibidos
		 * en el datagrama pkt. A continuación, imprimir por pantalla dicha cadena a
		 * modo de depuración. Después, usar la cadena para construir un objeto
		 * DirMessage que contenga en sus atributos los valores del mensaje. A partir de
		 * este objeto, se podrá obtener los valores de los campos del mensaje mediante
		 * métodos "getter" para procesar el mensaje y consultar/modificar el estado del
		 * servidor.
		 */
		InetSocketAddress clientAddr = (InetSocketAddress) pkt.getSocketAddress();	//se extrae la dierección del cliente.

		String cadena_pkt = new String(pkt.getData(), 0, pkt.getLength());
		System.out.println("--------------------------------------------");

		System.out.println("Message received from client: " + cadena_pkt);
		DirMessage dm = DirMessage.fromString(cadena_pkt);
//		depuracion 
		// System.out.println("cadena a partir del pkt: " + dm.toString());

		/*
		 * TODO: Una vez construido un objeto DirMessage con el contenido del datagrama
		 * recibido, obtener el tipo de operación solicitada por el mensaje y actuar en
		 * consecuencia, enviando uno u otro tipo de mensaje en respuesta.
		 */
		// String operation = DirMessageOps.OPERATION_INVALID; // TODO: Cambiar!
		String operation = dm.getOperation();
		/*
		 * TODO: (Boletín MensajesASCII) Construir un objeto DirMessage (msgToSend) con
		 * la respuesta a enviar al cliente, en función del tipo de mensaje recibido,
		 * leyendo/modificando según sea necesario el "estado" guardado en el servidor
		 * de directorio (atributos files, etc.). Los atributos del objeto DirMessage
		 * contendrán los valores adecuados para los diferentes campos del mensaje a
		 * enviar como respuesta (operation, etc.)
		 */
		DirMessage dmReply = null;
		switch (operation) {
		case DirMessageOps.OPERATION_PING: {

			System.out.println("switch: operacion es : " + operation);

			/*
			 * TODO: (Boletín MensajesASCII) Comprobamos si el protocolId del mensaje del
			 * cliente coincide con el nuestro.
			 */
			if (dm.getProtocolId() != null && dm.getProtocolId().equals(NanoFiles.PROTOCOL_ID)) {
				dmReply = new DirMessage(DirMessageOps.OPERATION_PINGOK);// welcome
				/*System.out.println("switch/IF: protocolID valido : " + dm.getProtocolId());
				System.out.println("Enviando: "+ dmReply.getOperation() + "...");
			*/
			}

			/*
			 * TODO: (Boletín MensajesASCII) Construimos un mensaje de respuesta que indique
			 * el éxito/fracaso del ping (compatible, incompatible), y lo devolvemos como
			 * resultado del método.
			 */
			else {
				dmReply = new DirMessage(DirMessageOps.OPERATION_DENIED);// invalid
				/*System.out.println("switch/ELSE: protocolID invalido : " + dm.getProtocolId());
				System.out.println("Enviando: "+ DirMessageOps.OPERATION_DENIED + "...");			
				*/
				}

			/*
			 * TODO: (Boletín MensajesASCII) Imprimimos por pantalla el resultado de
			 * procesar la petición recibida (éxito o fracaso) con los datos relevantes, a
			 * modo de depuración en el servidor
			 */

			break;
		}
		case DirMessageOps.OPERATION_GETFILELIST: {
		    //System.out.println("switch: operacion es : " + DirMessageOps.OPERATION_GETFILELIST);

		    // Estructura para contener la lista de peers por fichero
		    HashMap<String, HashSet<String>> peersForFile = new HashMap<>();

		    if (database != null) {
		        for (var fileHash : database.keySet()) {//recorrer base de datos
		            FileWithPeers fileData = database.get(fileHash);// obtiene la información asociada a ese fichero con los peers q lo contienen

		            if (fileData != null) {
		                peersForFile.put(fileHash, new HashSet<>());

		                InetSocketAddress[] servers = fileData.getServers(); //Obtiene la lista de servidores que tienen el fichero
		                if (servers != null) {
		                    for (var s : servers) {
		                        if (s != null) {//si no es nulo se añade el servidor al hashset
		                            peersForFile.get(fileHash).add(s.getHostName() + ":" + s.getPort());
		                        }
		                    }
		                }
		            }
		        }

		        // Crea el mensaje de respuesta con la lista de ficheros y sus peers
		        dmReply = new DirMessage(
		            DirMessageOps.OPERATION_GETFILELISTRESPONSE,
		            new HashSet<>(database.values()),
		            peersForFile
		        );
		    } else {
		        System.err.println("Error: database es null");
		        dmReply = new DirMessage(DirMessageOps.OPERATION_INVALID);
		    }

		    break;
		}

		case DirMessageOps.OPERATION_PUBLISHFILELIST: {
			//System.out.println("switch: operacion es : " + DirMessageOps.OPERATION_PUBLISHFILELIST);
			var files = dm.getFiles();
			int port = NFServer.PORT;
			if (dm.getPort() != 0)
				port = dm.getPort();

			if (files.isEmpty()) {
				var list = peers.get(clientAddr); 
				if (list != null) {
					for (var file : list) {
						if (database.containsKey(file.getFileHash())) {
							database.get(file.getFileHash()).deleteServer(clientAddr.getHostName(), port);
							if (database.get(file.getFileHash()).getServers().length == 0) {
								database.remove(file.getFileHash());
							}
						}
					}
					peers.remove(clientAddr);
				}
			} else {
				for (var file : files) {
					if (database.containsKey(file.getFileHash())) {
						database.get(file.getFileHash()).insertServer(clientAddr.getHostName(), port);
					} else {
						FileWithPeers newFile = new FileWithPeers(file); 
						newFile.insertServer(clientAddr.getHostName(), port);
						database.put(file.getFileHash(), newFile);
					}
				}

				peers.put(
					new InetSocketAddress(clientAddr.getHostName(), clientAddr.getPort()), 
					new HashSet<FileInfo>(files)
				);
			}

			dmReply = new DirMessage(DirMessageOps.OPERATION_PUBLISHFILELISTRESPONSE);
			System.out.println("Sending: " + DirMessageOps.OPERATION_PUBLISHFILELISTRESPONSE + "...");
			break;
		}

		
		
		
		default:
			System.err.println("Unexpected message operation: \"" + operation + "\"");
			System.exit(-1);
		}

		/*
		 * TODO: (Boletín MensajesASCII) Convertir a String el objeto DirMessage
		 * (msgToSend) con el mensaje de respuesta a enviar, extraer los bytes en que se
		 * codifica el string y finalmente enviarlos en un datagrama
		 */
		byte[] dataToClient = dmReply.toString().getBytes();	// Se transforma el mensaje a String y de String a bytes.
		DatagramPacket packetToClient = new DatagramPacket(dataToClient, dataToClient.length, clientAddr);
		socket.send(packetToClient);
	}

}
