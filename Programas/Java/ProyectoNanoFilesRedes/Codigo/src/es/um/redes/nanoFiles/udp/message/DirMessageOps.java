package es.um.redes.nanoFiles.udp.message;

public class DirMessageOps {

	/*
	 * TODO: (Boletín MensajesASCII) Añadir aquí todas las constantes que definen
	 * los diferentes tipos de mensajes del protocolo de comunicación con el
	 * directorio (valores posibles del campo "operation").
	 */
	public static final String OPERATION_INVALID = "invalid_operation";
	public static final String OPERATION_PING = "ping";
	public static final String OPERATION_PINGOK = "pingok";
	public static final String OPERATION_WELCOME = "welcome";
	public static final String OPERATION_DENIED = "denied";
	public static final String OPERATION_QUIT= "quit";

	public static final String OPERATION_GETFILELIST= "getfilelist";
	public static final String OPERATION_GETCOMMONSERVERFILELIST = "getcommonserverfilelist"; 
	public static final String OPERATION_PUBLISHFILELIST = "publishfilelist";
	public static final String OPERATION_GETFILELISTOK= "getfilelistOK";
	public static final String OPERATION_GETCOMMONSERVERFILELISTOK = "getcommonserverfilelistOK"; 
//	public static final String OPERATION_PUBLISHFILELISTOK = "publishfilelistOK";
	public static final String OPERATION_GETFILELISTBAD= "getfilelistBAD";
	public static final String OPERATION_GETCOMMONSERVERFILELISTBAD = "getcommonserverfilelistBAD"; 
//	public static final String OPERATION_PUBLISHFILELISTBAD = "publishfilelistBAD";

	public static final String OPERATION_GETFILELISTRESPONSE= "getfilelistresponce";
	public static final String OPERATION_PUBLISHFILELISTRESPONSE = "publishfilelistresponse";

}
