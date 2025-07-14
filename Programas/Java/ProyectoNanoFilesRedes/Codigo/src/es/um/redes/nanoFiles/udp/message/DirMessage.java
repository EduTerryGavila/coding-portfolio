package es.um.redes.nanoFiles.udp.message;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.StructureViolationException;

import es.um.redes.nanoFiles.application.NanoFiles;
import es.um.redes.nanoFiles.util.FileInfo;

/**
 * Clase que modela los mensajes del protocolo de comunicación entre pares para
 * implementar el explorador de ficheros remoto (servidor de ficheros). Estos
 * mensajes son intercambiados entre las clases DirectoryServer y
 * DirectoryConnector, y se codifican como texto en formato "campo:valor".
 * 
 * @author rtitos
 *
 */
public class DirMessage {
	public static final int PACKET_MAX_SIZE = 65507; // 65535 - 8 (UDP header) - 20 (IP header)

	private static final char DELIMITER = ':'; // Define el delimitador
	private static final char END_LINE = '\n'; // Define el carácter de fin de línea

	/**
	 * Nombre del campo que define el tipo de mensaje (primera línea)
	 */
	private static final String FIELDNAME_OPERATION = "operation";
	/*
	 * TODO hehco: (Boletín MensajesASCII) Definir de manera simbólica los nombres de
	 * todos los campos que pueden aparecer en los mensajes de este protocolo
	 * (formato campo:valor)
	 */
	private static final String PROTOCOL_ID = "protocolid";
	private static final String FIELDNAME_PINGRESPONSE="pingresponse";
	private static final String FIELDNAME_FILE = "file";
	private static final String FIELDNAME_PORT = "port";
	private static final String FIELDNAME_PUBLISHFILELISTRESPONSE = "publishfileresponse";

	/**
	 * Tipo del mensaje, de entre los tipos definidos en PeerMessageOps.
	 */
	private String operation = DirMessageOps.OPERATION_INVALID;
	/**
	 * Identificador de protocolo usado, para comprobar compatibilidad del directorio.
	 */
	private String protocolId;
	/*
	 * TODO hecho: (Boletín MensajesASCII) Crear un atributo correspondiente a cada uno de
	 * los campos de los diferentes mensajes de este protocolo.
	 */
	private String pingokresponse="pingok";
	private String deniedresponse="denied";
	private String invalidresponse="invalid";


	private HashSet<FileInfo> files;	//atributo para guadar los ficheros de filelist response
	private int port;
	private HashMap<String, HashSet<String>> peers;	//atributo que guarda los servidores de cada fichero
	
	/*
	 * TODO: (Boletín MensajesASCII) Crear diferentes constructores adecuados para
	 * construir mensajes de diferentes tipos con sus correspondientes argumentos
	 * (campos del mensaje)
	 */


	// Constructor para mensajes sin argumentos (ej. PING, GET_FILE_LIST)
	public DirMessage(String operation) {
	    this.operation = operation;
	    this.protocolId = NanoFiles.PROTOCOL_ID;
	    this.files = new HashSet<>();
	    this.peers = new HashMap<>();
	    this.port = 0;
	}
	
	public DirMessage(String op, Set<? extends Object> files, Map<String,? extends Object> servers) {
		operation = op;
		this.files = new HashSet<FileInfo>((Set<FileInfo>)files);
		this.peers = (HashMap<String, HashSet<String>>) servers;
	}
	/*public DirMessage(String op, Set<FileInfo> files, Map<String, HashSet<String>> servers) {
	    this.operation = op;
	    this.files = new HashSet<>(files);
	    this.peers = new HashMap<>(servers); 
	}*/

	public DirMessage(String op, int port, Set<FileInfo> publishfiles) {
		operation = op;
		this.port = port;
		files = new HashSet<FileInfo>(publishfiles);
	}
	
	public String getOperation() {
		return operation;
	}

	/*
	 * TODO: (Boletín MensajesASCII) Crear métodos getter y setter para obtener los
	 * valores de los atributos de un mensaje. Se aconseja incluir código que
	 * compruebe que no se modifica/obtiene el valor de un campo (atributo) que no
	 * esté definido para el tipo de mensaje dado por "operation".
	 */
	public void setProtocolID(String protocolIdent) {
		if (!operation.equals(DirMessageOps.OPERATION_PING)) {
			throw new RuntimeException(
					"DirMessage: setProtocolId called for message of unexpected type (" + operation + ")");
					
		}
		protocolId = protocolIdent;
	}

	public String getProtocolId() {
		if(!operation.equals(DirMessageOps.OPERATION_PING)) {
			throw new StructureViolationException(
					"getProtocolId: this message doesn't have the protocol's Id. Check \'getOperation() == DirMessageOps.OPERATION_PING\' first ");
		}
		return protocolId;
	}
	
	public Set<FileInfo> getFiles() {
		if(!operation.equals(DirMessageOps.OPERATION_GETFILELISTRESPONSE) && !operation.equals(DirMessageOps.OPERATION_PUBLISHFILELIST)) {
			throw new StructureViolationException(
					"getFiles: this message is not able to contain files. Check \'getOperation() == DirMessageOps.OPERATION_FILELIST_RES\' first ");
		}
		return Collections.unmodifiableSet(this.files);
	}
	
	public void insertFile(FileInfo newFile) {
		if(!operation.equals(DirMessageOps.OPERATION_GETFILELISTRESPONSE) && !operation.equals(DirMessageOps.OPERATION_PUBLISHFILELIST)) {
			throw new StructureViolationException(
					"insertFile: this message is not able to contain files. Check \'getOperation() == DirMessageOps.OPERATION_FILELIST_RES\' first ");
		}
		files.add(newFile);
	}


	public int getPort() {
		if(!operation.equals(DirMessageOps.OPERATION_PUBLISHFILELIST)) {
			throw new StructureViolationException(
					"getPort: this message is not able to contain reqfile. Check \'getOperation() == DirMessageOps.OPERATION_PUBLISH\' first ");
		}
		return this.port;
	}
	
	public void setPort(int port) {
		if(!operation.equals(DirMessageOps.OPERATION_PUBLISHFILELIST)) {
			throw new StructureViolationException(
					"setPort: this message is not able to contain reqfile. Check \'getOperation() == DirMessageOps.OPERATION_PUBLISH\' first ");
		}
		this.port = port;
	}
	
	
	public Map<String, HashSet<String>> getPeers() {
		if(!operation.equals(DirMessageOps.OPERATION_GETFILELISTRESPONSE)) {
			throw new StructureViolationException(
					"getPeers: this message is not able to contain reqfile. Check \'getOperation() == DirMessageOps.OPERATION_FILELISTRESPONSE\' first ");
		}
		return Collections.unmodifiableMap(this.peers);
	}
	
	private void insertPeer(String fileHash, String peer) {
		if(!operation.equals(DirMessageOps.OPERATION_GETFILELISTRESPONSE)) {
			throw new StructureViolationException(
					"insertPeer: this message is not able to contain a list of peers. Check \'getOperation() == DirMessageOps.OPERATION_OPERATION_FILELISTRESPONSE\' first ");
		}
		if (peers.containsKey(fileHash))
			peers.get(fileHash).add(peer);
		else {
			peers.put(fileHash, new HashSet<>());
			peers.get(fileHash).add(peer);
		}
	}


	/**
	 * Método que convierte un mensaje codificado como una cadena de caracteres, a
	 * un objeto de la clase PeerMessage, en el cual los atributos correspondientes
	 * han sido establecidos con el valor de los campos del mensaje.
	 * 
	 * @param message El mensaje recibido por el socket, como cadena de caracteres
	 * @return Un objeto PeerMessage que modela el mensaje recibido (tipo, valores,
	 *         etc.)
	 */
	public static DirMessage fromString(String message) {
		/*
		 * TODO hecho: (Boletín MensajesASCII) Usar un bucle para parsear el mensaje línea a
		 * línea, extrayendo para cada línea el nombre del campo y el valor, usando el
		 * delimitador DELIMITER, y guardarlo en variables locales.
		 */
		//DEPURACION
		/*System.out.println("DirMessage read from socket:");
		System.out.println(message);*/
		String[] lines = message.split(END_LINE + "");
		// Local variables to save data during parsing
		DirMessage m = null;
		for (String line : lines) {
			//DEPURACION
			//System.out.println("linea actual" + line);
			int idx = line.indexOf(DELIMITER); // Posición del delimitador
			String fieldName = line.substring(0, idx).toLowerCase(); // minúsculas
			//DEPURACION
			//System.out.println("fieldname:"+ fieldName);
			String value = line.substring(idx + 1).trim();
			//DEPURACION
			//System.out.println("value:" + value);
			/*System.out.println("operacion leida en FromString: " + fieldName);
			System.out.println("valor leido en FromString: " + value);*/

			switch (fieldName) {
			case FIELDNAME_OPERATION: {
				assert (m == null);
				m = new DirMessage(value);
				break;
			
			}

			case PROTOCOL_ID: {
				assert (m == null);
				m = new DirMessage(DirMessageOps.OPERATION_PING);
				m.setProtocolID(value);
				break;
			}
			case FIELDNAME_PINGRESPONSE: {
				assert (m == null);
				String a= new String();
				if (value.equals(DirMessageOps.OPERATION_DENIED))
					a = DirMessageOps.OPERATION_DENIED;
				else if (value.equals(DirMessageOps.OPERATION_PINGOK))
					a = DirMessageOps.OPERATION_PINGOK;
				m = new DirMessage(a);
				break;
			}
			case FIELDNAME_FILE: {
				String[] filefields = value.split(";");
				if (filefields.length < 3 || filefields.length > 4) {
					System.err.println("Malformed file field: \"" + value + "\"");
					break;
				}
				
				m.insertFile(new FileInfo(filefields[0].trim(), filefields[1].trim(), Integer.parseInt(filefields[2].trim()), filefields[1].trim()));
				
				if(filefields.length == 4) {
					String[] servers = filefields[3].split(",");
					for (var s : servers)
						m.insertPeer(filefields[0].trim(), s.trim());
				}
				break;}
			case FIELDNAME_PORT: {
				m.setPort(Integer.parseInt(value));
				break;}
			case FIELDNAME_PUBLISHFILELISTRESPONSE: {
				break;
			}
			default:
				System.err.println("PANIC: DirMessage.fromString - message with unknown field name " + fieldName);
				System.err.println("Message was:\n" + message);
				System.exit(-1);
			}
		}




		return m;
	}
	

	/**
	 * Método que devuelve una cadena de caracteres con la codificación del mensaje
	 * según el formato campo:valor, a partir del tipo y los valores almacenados en
	 * los atributos.
	 * 
	 * @return La cadena de caracteres con el mensaje a enviar por el socket.
	 */
	public String toString() {

		StringBuffer sb = new StringBuffer();
		sb.append(FIELDNAME_OPERATION + DELIMITER + operation + END_LINE); // Construimos el campo
		/*
		 * TODO: (Boletín MensajesASCII) En función de la operación del mensaje, crear
		 * una cadena la operación y concatenar el resto de campos necesarios usando los
		 * valores de los atributos del objeto.
		 */
//		System.out.println("operacion leida en toString: " + operation);
		switch (operation) {
		case DirMessageOps.OPERATION_PING: {
			sb.append(PROTOCOL_ID + DELIMITER + protocolId + END_LINE);
			break;
		}
		case DirMessageOps.OPERATION_PINGOK: {
			sb.append(FIELDNAME_PINGRESPONSE + DELIMITER + pingokresponse + END_LINE);
			break;
		}
		case DirMessageOps.OPERATION_DENIED: {
			sb.append(FIELDNAME_PINGRESPONSE + DELIMITER + deniedresponse + END_LINE);
			break;
		}
		case DirMessageOps.OPERATION_INVALID: {
			sb.append(FIELDNAME_PINGRESPONSE + DELIMITER + invalidresponse + END_LINE);
			break;
		}
		case DirMessageOps.OPERATION_GETFILELISTRESPONSE:
			for (var file : files) {
				sb.append(FIELDNAME_FILE + DELIMITER + " " + file.getFileHash() + "; " + file.getFileName() + "; " + file.getFileSize() + "; ");
				peers.get(file.getFileHash()).forEach(p -> sb.append(p + ", "));
				sb.replace(sb.lastIndexOf(","), sb.length(), END_LINE + "");
			}
			break;
		case DirMessageOps.OPERATION_PUBLISHFILELIST:
			if (port != 0)
				sb.append(FIELDNAME_PORT + DELIMITER + " " + port + END_LINE);
			files.forEach(file -> sb.append(FIELDNAME_FILE + DELIMITER + " " + file.getFileHash() + "; " + file.getFileName() + "; " + file.getFileSize() + END_LINE));
			break;	
		case DirMessageOps.OPERATION_PUBLISHFILELISTRESPONSE:
			break;
		case DirMessageOps.OPERATION_GETFILELIST: {
			break;
		}
		default:
			System.err.println("PANIC: DirMessage.toString - message with unknown operation name " + operation);
			System.exit(-1);
		}


		sb.append(END_LINE); // Marcamos el final del mensaje
		return sb.toString();
	}

}
