package es.um.redes.nanoFiles.udp.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import es.um.redes.nanoFiles.application.NanoFiles;
import es.um.redes.nanoFiles.udp.message.DirMessage;
import es.um.redes.nanoFiles.udp.message.DirMessageOps;
import es.um.redes.nanoFiles.util.FileInfo;
import es.um.redes.nanoFiles.util.FileWithPeers;

/**
 * Cliente con métodos de consulta y actualización específicos del directorio
 */
public class DirectoryConnector {
	/**
	 * Puerto en el que atienden los servidores de directorio
	 */
	private static final int DIRECTORY_PORT = 6868;
	/**
	 * Tiempo máximo en milisegundos que se esperará a recibir una respuesta por el
	 * socket antes de que se deba lanzar una excepción SocketTimeoutException para
	 * recuperar el control
	 */
	private static final int TIMEOUT = 1000;
	/**
	 * Número de intentos máximos para obtener del directorio una respuesta a una
	 * solicitud enviada. Cada vez que expira el timeout sin recibir respuesta se
	 * cuenta como un intento.
	 */
	private static final int MAX_NUMBER_OF_ATTEMPTS = 5;

	/**
	 * Socket UDP usado para la comunicación con el directorio
	 */
	private DatagramSocket socket;
	/**
	 * Dirección de socket del directorio (IP:puertoUDP)
	 */
	private InetSocketAddress directoryAddress;
	/**
	 * Nombre/IP del host donde se ejecuta el directorio
	 */
	private String directoryHostname;
	private static final int FALLO_SALIDA = 1;
//constante que contiene la cadena welcome
	private static final String WELCOME = new String("welcome");

	public DirectoryConnector(String hostname) throws IOException {
		// Guardamos el string con el nombre/IP del host
		directoryHostname = hostname;
		/*
		 * TODOhecho: (Boletín SocketsUDP) Convertir el string 'hostname' a InetAddress
		 * y guardar la dirección de socket (address:DIRECTORY_PORT) del directorio en
		 * el atributo directoryAddress, para poder enviar datagramas a dicho destino.
		 */
		directoryAddress = new InetSocketAddress(InetAddress.getByName(hostname), DIRECTORY_PORT);

		/*
		 * TODOhecho: (Boletín SocketsUDP) Crea el socket UDP en cualquier puerto para
		 * enviar datagramas al directorio
		 */
		socket = new DatagramSocket();

	}

	/**
	 * Método para enviar y recibir datagramas al/del directorio
	 * 
	 * @param requestData los datos a enviar al directorio (mensaje de solicitud)
	 * @return los datos recibidos del directorio (mensaje de respuesta)
	 */
	private byte[] sendAndReceiveDatagrams(byte[] requestData) {
		byte responseData[] = new byte[DirMessage.PACKET_MAX_SIZE];
		byte response[] = null;
		if (directoryAddress == null) {
			System.err.println("DirectoryConnector.sendAndReceiveDatagrams: UDP server destination address is null!");
			System.err.println(
					"DirectoryConnector.sendAndReceiveDatagrams: make sure constructor initializes field \"directoryAddress\"");
			System.exit(-1);

		}
		if (socket == null) {
			System.err.println("DirectoryConnector.sendAndReceiveDatagrams: UDP socket is null!");
			System.err.println(
					"DirectoryConnector.sendAndReceiveDatagrams: make sure constructor initializes field \"socket\"");
			System.exit(-1);
		}
		/*
		 * TODO hecho: (Boletín SocketsUDP) Enviar datos en un datagrama al directorio y
		 * recibir una respuesta. El array devuelto debe contener únicamente los datos
		 * recibidos, *NO* el búfer de recepción al completo.
		 */
		DatagramPacket packetToServer = new DatagramPacket(requestData, requestData.length, directoryAddress); // Datos
																												// para
																												// enviar
		DatagramPacket packetFromServer = new DatagramPacket(responseData, responseData.length); // Datos recibidos

		/*
		 * TODO hecho: (Boletín SocketsUDP) Una vez el envío y recepción asumiendo un
		 * canal confiable (sin pérdidas) esté terminado y probado, debe implementarse
		 * un mecanismo de retransmisión usando temporizador, en caso de que no se
		 * reciba respuesta en el plazo de TIMEOUT. En caso de salte el timeout, se debe
		 * volver a enviar el datagrama y tratar de recibir respuestas, reintentando
		 * como máximo en MAX_NUMBER_OF_ATTEMPTS ocasiones.
		 */

		/*
		 * TODO hecho: (Boletín SocketsUDP) Las excepciones que puedan lanzarse al
		 * leer/escribir en el socket deben ser capturadas y tratadas en este método. Si
		 * se produce una excepción de entrada/salida (error del que no es posible
		 * recuperarse), se debe informar y terminar el programa.
		 */
		/*
		 * NOTA: Las excepciones deben tratarse de la más concreta a la más genérica.
		 * SocketTimeoutException es más concreta que IOException.
		 */
		int i = 0; // Contador de intentos
		// con estructura timeout

		while (i < MAX_NUMBER_OF_ATTEMPTS) {
			try {
				socket.send(packetToServer);
				socket.setSoTimeout(TIMEOUT);

				try {
					socket.receive(packetFromServer);
					String stringresponse = new String(responseData, 0, packetFromServer.getLength());
					response = stringresponse.getBytes();
					break;
				} catch (SocketTimeoutException e) {
					i++;
					System.out.println("Tiempo de espera superado...");
					if (i == MAX_NUMBER_OF_ATTEMPTS) {
						System.out.println("No se puede acceder al servidor, se ha cerrado el programa");
						System.exit(FALLO_SALIDA);
					}

					continue; // Para continuar si no se envia
				}
			} catch (IOException e) {
				System.out.println("Algo ha fallado en el envio y el programa se ha cerrado");
				System.exit(FALLO_SALIDA);
			}

		}

		if (response != null && response.length == responseData.length) {
			System.err.println("Your response is as large as the datagram reception buffer!!\n"
					+ "You must extract from the buffer only the bytes that belong to the datagram!");
		}
		return response;
	}

	/**
	 * Método para probar la comunicación con el directorio mediante el envío y
	 * recepción de mensajes sin formatear ("en crudo")
	 * 
	 * @return verdadero si se ha enviado un datagrama y recibido una respuesta
	 */
	public boolean testSendAndReceive() {
		/*
		 * TODO hecho: (Boletín SocketsUDP) Probar el correcto funcionamiento de
		 * sendAndReceiveDatagrams. Se debe enviar un datagrama con la cadena "ping" y
		 * comprobar que la respuesta recibida empieza por "pingok". En tal caso,
		 * devuelve verdadero, falso si la respuesta no contiene los datos esperados.
		 */
		boolean success = false;
		String ping = new String(DirMessageOps.OPERATION_PING); // cadena que contiene "ping"
		String pingok = new String(DirMessageOps.OPERATION_PINGOK);// cadena que contiene "pingok"

		byte[] dataToClient = ping.getBytes(); // convertimos la cadena a bytes

		byte[] respuesta = sendAndReceiveDatagrams(dataToClient); // mandamos los bytes de "ping" y recibimos respuesta
																	// en bytes (que tiene que ser "pingok")
		String stringrespuesta = new String(respuesta, 0, respuesta.length);// pasamos la respuesta que ha venido en
																			// bytes a string

		if (pingok.equals(stringrespuesta)) {// comparamos lo que nos tiene que salir("pingok") con lo que nos ha
												// salido(la respuesta que hemos recibido)
			/*
			 * DEPURACION System.out.println("mensaje de vuelta coincide");
			 * System.out.println("contenido mensajeFromClientTRUE cadena: " +
			 * stringrespuesta); System.out.println("contenido bytepingok cadenaTRUE: " +
			 * pingok);
			 */

			success = true;
		} else {
			System.out.println("mensaje de vuelta NO coincide");
			/*
			 * DEPURACION System.out.println("mensaje de vuelta NO coincide");
			 * System.out.println("contenido mensajeFromClientFALSE cadena: " +
			 * stringrespuesta); System.out.println("contenido bytepingok cadenaFALSE: " +
			 * pingok);
			 */
			success = false;
		}
		return success;
	}

	public String getDirectoryHostname() {
		return directoryHostname;
	}

	/**
	 * Método para "hacer ping" al directorio, comprobar que está operativo y que
	 * usa un protocolo compatible. Este método no usa mensajes bien formados.
	 * 
	 * @return Verdadero si
	 */
	public boolean pingDirectoryRaw() {
		boolean success = false;

		/*
		 * TODO hecho: (Boletín EstructuraNanoFiles) Basándose en el código de
		 * "testSendAndReceive", contactar con el directorio, enviándole nuestro
		 * PROTOCOL_ID (ver clase NanoFiles). Se deben usar mensajes "en crudo" (sin un
		 * formato bien definido) para la comunicación.
		 * 
		 * PASOS: 1.Crear el mensaje a enviar (String "ping&protocolId"). 2.Crear un
		 * datagrama con los bytes en que se codifica la cadena : 4.Enviar datagrama y
		 * recibir una respuesta (sendAndReceiveDatagrams). : 5. Comprobar si la cadena
		 * recibida en el datagrama de respuesta es "welcome", imprimir si éxito o
		 * fracaso. 6.Devolver éxito/fracaso de la operación.
		 */

		String mensajeEnvio = new String("ping&" + NanoFiles.PROTOCOL_ID);
		byte[] dataToClient = mensajeEnvio.getBytes();

		byte[] respuesta = sendAndReceiveDatagrams(dataToClient); // mandamos los bytes de "ping" y recibimos respuesta
																	// en bytes (que tiene que ser "pingok")
		String stringrespuesta = new String(respuesta, 0, respuesta.length);// pasamos la respuesta que ha venido en
																			// bytes a string

		if (WELCOME.equals(stringrespuesta)) {// comparamos lo que nos tiene que salir("pingok") con lo que nos ha
												// salido(la respuesta que hemos recibido)
//	    	DEPURACION
			/*
			 * System.out.println("mensaje de vuelta coincide");
			 * System.out.println("contenido mensajeFromClientTRUE cadena: " +
			 * stringrespuesta); System.out.println("contenido bytepingok cadenaTRUE: " +
			 * WELCOME);
			 */

			success = true;
		} else {
//	    	DEPURACION
			/*
			 * System.out.println("mensaje de vuelta NO coincide");
			 * System.out.println("contenido mensajeFromClientFALSE cadena: " +
			 * stringrespuesta); System.out.println("contenido bytepingok cadenaFALSE: " +
			 * WELCOME);
			 */

			success = false;
		}

		return success;
	}

	/**
	 * Método para "hacer ping" al directorio, comprobar que está operativo y que es
	 * compatible.
	 * 
	 * @return Verdadero si el directorio está operativo y es compatible
	 */
	public boolean pingDirectory() {
		boolean success = false;
		/*
		 * TODO hecho: (Boletín MensajesASCII) Hacer ping al directorio 1.Crear el
		 * mensaje a enviar (objeto DirMessage) con atributos adecuados (operation,
		 * etc.) NOTA: Usar como operaciones las constantes definidas en la clase
		 * DirMessageOps : 2.Convertir el objeto DirMessage a enviar a un string (método
		 * toString) 3.Crear un datagrama con los bytes en que se codifica la cadena :
		 * 4.Enviar datagrama y recibir una respuesta (sendAndReceiveDatagrams). :
		 * 5.Convertir respuesta recibida en un objeto DirMessage (método
		 * DirMessage.fromString) 6.Extraer datos del objeto DirMessage y procesarlos
		 * 7.Devolver éxito/fracaso de la operación
		 */
		DirMessage dm = new DirMessage(DirMessageOps.OPERATION_PING);
		dm.setProtocolID(NanoFiles.PROTOCOL_ID);
		byte[] datosEnvio = dm.toString().getBytes();
		byte[] datosRespuesta = sendAndReceiveDatagrams(datosEnvio);
		String cadenaRespuesta = new String(datosRespuesta);
		System.out.println(
				"la cadena recibida como respuesta en directoryconector.pingdirectory es : " + cadenaRespuesta);

		DirMessage dmReply = DirMessage.fromString(cadenaRespuesta);
		System.out.println("operacion menaje recibido: " + dmReply.getOperation());
		/* Ver si es compatible */
		if (dmReply.getOperation().equals(DirMessageOps.OPERATION_PINGOK)) { // si la rewspuesta recibida es igual a
																				// WELCOME(contiene "welcome"),
			// devolver true
			// DEPURACION
			// System.out.println("cadena es welcome " + dmReply.getWelcome());
			success = true;
		} else {
			// DEPURACION
			// System.out.println("cadena NO es welcome " + dmReply.getWelcome());

			success = false;
		}
		return success;
	}

	/**
	 * Método para dar de alta como servidor de ficheros en el puerto indicado y
	 * publicar los ficheros que este peer servidor está sirviendo.
	 * 
	 * @param serverPort El puerto TCP en el que este peer sirve ficheros a otros
	 * @param files      La lista de ficheros que este peer está sirviendo.
	 * @return Verdadero si el directorio tiene registrado a este peer como servidor
	 *         y acepta la lista de ficheros, falso en caso contrario.
	 */
	public boolean registerFileServer(int serverPort, FileInfo[] files) {
		boolean success = false;
		Set<FileInfo> fileset = Set.copyOf(Arrays.asList(files));
		DirMessage dm = new DirMessage(DirMessageOps.OPERATION_PUBLISHFILELIST, serverPort, fileset);
		byte[] datosEnvio = dm.toString().getBytes();
		byte[] datosRespuesta = sendAndReceiveDatagrams(datosEnvio);
		
		if (datosRespuesta == null) {
			System.out.println("No response");
			return success;
		}
		
		
		String cadenaRespuesta = new String(datosRespuesta);
		
		
		
		
		DirMessage dmReply = DirMessage.fromString(cadenaRespuesta);
		if (!dmReply.getOperation().equals(DirMessageOps.OPERATION_PUBLISHFILELISTRESPONSE)) {
			System.out.println("Bad response: " + dmReply.getOperation());
			return false;
		}
		// TODO: Ver TODOs en pingDirectory y seguir esquema similar
		success = true;
		return success;
	}

	/**
	 * Método para obtener la lista de ficheros que los peers servidores han
	 * publicado al directorio. Para cada fichero se debe obtener un objeto FileInfo
	 * con nombre, tamaño y hash. Opcionalmente, puede incluirse para cada fichero,
	 * su lista de peers servidores que lo están compartiendo.
	 * 
	 * @return Los ficheros publicados al directorio, o null si el directorio no
	 *         pudo satisfacer nuestra solicitud
	 */
	// con la info solo de los ficheros publicados
	/*
	 * public FileInfo[] getFileList() { FileInfo[] filelist = new FileInfo[0]; //
	 * TODO: Ver TODOs en pingDirectory y seguir esquema similar
	 * 
	 * DirMessage dm = new DirMessage(DirMessageOps.OPERATION_GETFILELIST); byte[]
	 * datosEnvio = dm.toString().getBytes(); byte[] datosRespuesta =
	 * sendAndReceiveDatagrams(datosEnvio);
	 * 
	 * if (datosRespuesta == null) return filelist;
	 * 
	 * 
	 * String cadenaRespuesta = new String(datosRespuesta);
	 * 
	 * DirMessage dmReply = DirMessage.fromString(cadenaRespuesta);
	 * 
	 * filelist= dmReply.getFiles().toArray(filelist);
	 * 
	 * return filelist; }
	 */
//	con la info de que servidor contiene que ficheros
	public FileInfo[] getFileList() {
		FileInfo[] filelist = new FileInfo[0];
		// TODO: Ver TODOs en pingDirectory y seguir esquema similar

		DirMessage dm = new DirMessage(DirMessageOps.OPERATION_GETFILELIST);
		byte[] datosEnvio = dm.toString().getBytes();
		byte[] datosRespuesta = sendAndReceiveDatagrams(datosEnvio);

		if (datosRespuesta == null)
			return filelist;

		String cadenaRespuesta = new String(datosRespuesta);

		DirMessage dmReply = DirMessage.fromString(cadenaRespuesta);

		Map<String, ? extends Set<String>> peers = dmReply.getPeers();

		Set<FileWithPeers> filesWithPeers = new HashSet<>();

		for (FileInfo file : dmReply.getFiles()) {
			FileWithPeers fwp = new FileWithPeers(file);
			Set<String> peerSet = peers.get(file.getFileHash());
			if (peerSet != null) {
				for (String peer : peerSet) {
					fwp.insertServer(peer);
				}
			}
			filesWithPeers.add(fwp);
		}

		filelist = filesWithPeers.toArray(new FileInfo[0]);
		return filelist;
	}

	/**
	 * Método para obtener la lista de servidores que tienen un fichero cuyo nombre
	 * contenga la subcadena dada.
	 * 
	 * @filenameSubstring Subcadena del nombre del fichero a buscar
	 * 
	 * @return La lista de direcciones de los servidores que han publicado al
	 *         directorio el fichero indicado. Si no hay ningún servidor, devuelve
	 *         una lista vacía.
	 */
	/*
	 * public InetSocketAddress[] getServersSharingThisFile(String
	 * filenameSubstring) { // TODO: Ver TODOs en pingDirectory y seguir esquema
	 * similar InetSocketAddress[] serversList = new InetSocketAddress[0];
	 * 
	 * DirMessage dm = new
	 * DirMessage(DirMessageOps.OPERATION_GETCOMMONSERVERFILELIST); byte[]
	 * datosEnvio = dm.toString().getBytes(); byte[] datosRespuesta =
	 * sendAndReceiveDatagrams(datosEnvio); String cadenaRespuesta = new
	 * String(datosRespuesta);
	 * 
	 * DirMessage dmReply = DirMessage.fromString(cadenaRespuesta); //copiar esa
	 * estructura pero con getservercmomon if
	 * (dmReply.getOperation().equals(DirMessageOps.
	 * OPERATION_GETCOMMONSERVERFILELISTBAD)) return serversList; else if
	 * (dmReply.getOperation().equals(DirMessageOps.
	 * OPERATION_GETCOMMONSERVERFILELISTOK)) { HashSet<InetSocketAddress> peerSet =
	 * new HashSet<>();
	 * 
	 * // Iterar sobre todos los sets de peers del mapa for (HashSet<String> peers :
	 * dmReply.getPeers().values()) { for (String peer : peers) { String[]
	 * peerfields = peer.split(":"); InetAddress addr; try { addr =
	 * InetAddress.getByName(peerfields[0]); } catch (UnknownHostException e) {
	 * continue; }
	 * 
	 * int port = NFServer.PORT; if (peerfields.length == 2) { port =
	 * Integer.parseInt(peerfields[1]); }
	 * 
	 * peerSet.add(new InetSocketAddress(addr, port)); } }
	 * 
	 * serversList = peerSet.toArray(new InetSocketAddress[0]); } return
	 * serversList; }
	 */
	// comentamos la funcion de arriba porque no la vamos a usar
//	ya que hemos usado la clase de apoyo a fileinfo, 
//	filewithpeers que amplia la informacion relativa a los ficheros, 
//	entre otras informaciones adicionales,
//	esta la de la lista de los servidores que comparten en comun ese fichero 
	public FileWithPeers getFilenameServersInfo(String filenameSubstring) {
		DirMessage filelistRequest = new DirMessage(DirMessageOps.OPERATION_GETFILELIST);
		byte[] responseData = sendAndReceiveDatagrams(filelistRequest.toString().getBytes());
		if (responseData == null)
			return null;

		ArrayList<FileInfo> matches = new ArrayList<>();
		DirMessage filelistResponse = DirMessage.fromString(new String(responseData));
		for (var file : filelistResponse.getFiles()) {
			if (file.getFileName().toLowerCase().equals(filenameSubstring.toLowerCase())) {
				return new FileWithPeers(file, filelistResponse.getPeers().get(file.getFileHash()));
			}//Si encontramos una coincidencia exacta entre el nombre del fichero y el que buscamos, devolvemos directamente ese fichero y su lista de peers.
			if (file.getFileName().contains(filenameSubstring)) {

				matches.add(new FileWithPeers(file, filelistResponse.getPeers().get(file.getFileHash())));
			}//Si contiene la subcadena, se guarda como posible candidato
		}

		if (matches.size() > 1) {
			System.err.println("Ambiguous substring");
			return null;// Si hay más de una coincidencia
		}
		if (matches.isEmpty()) {
			System.out.println("File not found");
			return null;//Si no hay ninguna
		}

		return (FileWithPeers) matches.get(0);
	}

	/**
	 * Método para darse de baja como servidor de ficheros.
	 * 
	 * @return Verdadero si el directorio tiene registrado a este peer como servidor
	 *         y ha dado de baja sus ficheros.
	 */
	public boolean unregisterFileServer(int port) {
		boolean success = false;

		DirMessage dm = new DirMessage(DirMessageOps.OPERATION_PUBLISHFILELIST, port, new HashSet<FileInfo>());
		byte[] datosEnvio = dm.toString().getBytes();
		byte[] datosRespuesta = sendAndReceiveDatagrams(datosEnvio);

		if (datosRespuesta == null)
			return success;

		String cadenaRespuesta = new String(datosRespuesta);

		DirMessage dmReply = DirMessage.fromString(cadenaRespuesta);

		if (!dmReply.getOperation().equals(DirMessageOps.OPERATION_PUBLISHFILELISTRESPONSE))
			return false;
		success = true;
		return success;
	}

}
