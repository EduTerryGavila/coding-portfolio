package es.um.redes.nanoFiles.tcp.message;

//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
import java.io.IOException;

public class PeerMessageTest {

	public static void main(String[] args) throws IOException {
		//String nombreArchivo = "peermsg.bin";
		//DataOutputStream fos = new DataOutputStream(new FileOutputStream(nombreArchivo));

		/*
		 * TODO: Probar a crear diferentes tipos de mensajes (con los opcodes válidos
		 * definidos en PeerMessageOps), estableciendo los atributos adecuados a cada
		 * tipo de mensaje. Luego, escribir el mensaje a un fichero con
		 * writeMessageToOutputStream para comprobar que readMessageFromInputStream
		 * construye un mensaje idéntico al original.
		 */
//		PeerMessage msgOut = new PeerMessage();
//		byte Opcode = 10;// probando download// cambiar a 1,2,3...,10 para ir probando todos los mensajes
//		msgOut.setOpcode(Opcode);
//		String cadenaHashCodeOUT = null;
//		long inicioOUT = 0;
//		long finOUT = 0;
//		String cadenaOUT = null;
//		long longDatos = 0;
//		byte[] datosArrayOUT = null;
//		byte lastOUT = 11;
//		switch (Opcode) {
//		case 1: {
//			// caso 1 download
//			cadenaHashCodeOUT = "hashcode download";
//			inicioOUT = 123456;
//			finOUT = 246810;
//			// crear mensaje con datos para comprobarlos luego
//			msgOut.setHashCode(cadenaHashCodeOUT);
//			msgOut.setInicioFrag(inicioOUT);
//			msgOut.setfinFrag(finOUT);
//			break;
//		}
//		case 2: {
//			// caso 2
//			break;
//		}
//		case 3: {
//			// caso 3 file
//			// TODO hacer lo mismo q para el download pero con los datos q se necesiten
//			cadenaOUT = new String("haschode file");
//			longDatos = 123456;
//			datosArrayOUT = new byte[(int) longDatos];
//			System.out.println("inicializacion datosArrayOut: " + datosArrayOUT);
//			lastOUT = 11;
//			msgOut.setHashCode(cadenaOUT);
//			msgOut.setDatos(datosArrayOUT);
//			msgOut.setUltimoTrozo(lastOUT);
//		}
//		case 4: {
//			// caso 4
//			break;
//		}
//		case 5: {
//			// caso 5
//			break;
//		}
//		case 6: {
//			// caso 6
//			break;
//		}
//		case 7: {
//			// caso 7
//			break;
//		}
//		case 8: {
//			// caso 8
//			break;
//		}
//		case 9: {
//			// caso 9
//			break;
//		}
//		case 10: {
//			// caso 10
//			break;
//		}
//		}
//
//		// caso 2 filenotfound
//		// no hacer nada, opcode ya puesto y es mensaje de control(solo lleva opcode)
//
//		// caso 4
//		// no hacer nada, opcode ya puesto y es mensaje de control(solo lleva opcode)
//
//		// caso 5
//		// no hacer nada, opcode ya puesto y es mensaje de control(solo lleva opcode)
//
//		// caso 6
//
//		// caso 7
//		// no hacer nada, opcode ya puesto y es mensaje de control(solo lleva opcode)
//		// caso 8
//
//		// caso 9
//		// no hacer nada, opcode ya puesto y es mensaje de control(solo lleva opcode)
//
//		// caso 10
//		// no hacer nada, opcode ya puesto y es mensaje de control(solo lleva opcode)
//
//		msgOut.writeMessageToOutputStream(fos);
//
//		DataInputStream fis = new DataInputStream(new FileInputStream(nombreArchivo));
//		PeerMessage msgIn = PeerMessage.readMessageFromInputStream((DataInputStream) fis);
//		/*
//		 * TODO: Comprobar que coinciden los valores de los atributos relevantes al tipo
//		 * de mensaje en ambos mensajes (msgOut y msgIn), empezando por el opcode.
//		 */
//		if (msgOut.getOpcode() != msgIn.getOpcode()) {
//			System.err.println("Opcode does not match!");
//		} else {
//			System.out.println("Opcode actually match!");
//			switch (msgIn.getOpcode()) {
//			case 1: {
//				System.out.println("caso: OPCODE_DOWNLOAD");
//				String cadenaHashCodeIN = msgIn.getHashCode();
//				long inicioIN = msgIn.getInicioFrag();
//				long finIN = msgIn.getFinFrag();
//				if (cadenaHashCodeIN.equals(cadenaHashCodeOUT))
//					System.out.println("cadena hashcode coincide:" + cadenaHashCodeIN);
//				else {
//					System.err.println("cadena hashcode no coincide:");
//					System.err.println("cadenaHashCodeOUT: " + cadenaHashCodeOUT + " --- " + "cadenaHashCodeIN: "
//							+ cadenaHashCodeIN);
//				}
//				if (inicioIN == inicioOUT)
//					System.out.println("inicio frag coincide:" + inicioIN);
//				if (finIN == finOUT)
//					System.out.println("fin frag coincide:" + finIN);
//
//				break;
//			}
//			case 2: {
//				System.out.println("caso: OPCODE_FILE_NOT_FOUND");
//				System.out.println("opcode coincide:" + msgIn.getOpcode());
//
//				break;
//			}
//			case 3: {
//				System.out.println("caso: OPCODE_FILE");
//				String cadenaIN = msgIn.getHashCode();
//				byte[] datosArrayIN = msgIn.getDatos();
//				byte lastIN = msgIn.getUltimoTrozo();
//				if (cadenaOUT.equals(cadenaIN))
//					System.out.println("cadena hashcode coincide:" + cadenaIN);
//				else {
//					System.err.println("cadena hashcode no coincide:");
//					System.err.println("cadenaOUT: " + cadenaOUT + " --- " + "cadenaIN: " + cadenaIN);
//				}
//				if ((datosArrayOUT.toString()).equals(datosArrayIN.toString()))
//					System.out.println("datos array coincide:" + datosArrayIN.toString());
//				else {
//					System.err.println("datos array no coincide:");
//					System.err.println("datosArrayOUT: " + datosArrayOUT.toString() + "--- " + "datosArrayIN: " + datosArrayIN.toString());
//				}
//				if (lastOUT == lastIN)
//					System.out.println("last coincide:" + lastIN);
//				else {
//					System.err.println("last no coincide:");
//					System.err.println("lastOUT: " + lastOUT + "--- " + "lastIN: " + lastIN);
//				}
//				break;
//			}
//			case 4: {
//				System.out.println("caso: OPCODE_FILE_HASH_AMBIGUOUS");
//				System.out.println("opcode coincide:" + msgIn.getOpcode());
//				break;
//			}
//			case 5: {
//				System.out.println("caso: OPCODE_FILE_REQUEST_FILELIST");
//				System.out.println("opcode coincide:" + msgIn.getOpcode());
//
//				break;
//			}
//			case 6: {
//				System.out.println("caso: OPCODE_FILELIST");
//				System.out.println("opcode coincide:" + msgIn.getOpcode());
//
//				break;
//			}
//			case 7: {
//				System.out.println("caso: OPCODE_FILELIST_BAD");
//				System.out.println("opcode coincide:" + msgIn.getOpcode());
//				break;
//			}
//			case 8: {
//				System.out.println("caso: OPCODE_PUBLISH_FILES");
//				System.out.println("opcode coincide:" + msgIn.getOpcode());
//
//				break;
//			}
//			case 9: {
//				System.out.println("caso: OPCODE_PUBLISH_FILES_BAD");
//				System.out.println("opcode coincide:" + msgIn.getOpcode());
//				break;
//			}
//			case 10: {
//				System.out.println("caso: OPCODE_PUBLISH_FILES_OK");
//				System.out.println("opcode coincide:" + msgIn.getOpcode());
//				break;
//			}
//			default:
//				throw new IllegalArgumentException("Unexpected value: " + msgIn.getOpcode());
//			}
//		}
	}

}