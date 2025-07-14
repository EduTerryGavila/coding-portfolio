package es.um.redes.nanoFiles.tcp.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import es.um.redes.nanoFiles.tcp.message.PeerMessage;

//Esta clase proporciona la funcionalidad necesaria para intercambiar mensajes entre el cliente y el servidor
public class NFConnector {
	private Socket socket;
	private InetSocketAddress serverAddr;

	private DataInputStream dis;
	private DataOutputStream dos;

	public NFConnector(InetSocketAddress fserverAddr) throws UnknownHostException, IOException {
		serverAddr = fserverAddr;
		/*
		 * TODOhecho: (Boletín SocketsTCP) Se crea el socket a partir de la dirección
		 * del servidor (IP, puerto). La creación exitosa del socket significa que la
		 * conexión TCP ha sido establecida.
		 */
		socket = new Socket(serverAddr.getAddress(), serverAddr.getPort());
		/*
		 * TODOhecho: (Boletín SocketsTCP) Se crean los DataInputStream/DataOutputStream
		 * a partir de los streams de entrada/salida del socket creado. Se usarán para
		 * enviar (dos) y recibir (dis) datos del servidor.
		 */
		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());

	}

	public void test() {
		/*
		 * TODOhecho: (Boletín SocketsTCP) Enviar entero cualquiera a través del socket
		 * y después recibir otro entero, comprobando que se trata del mismo valor.
		 */
		// send message to server
		// creamos mensaje a mandar
		try {
			dos.writeInt(69420);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public InetSocketAddress getServerAddr() {
		return serverAddr;
	}
	//integramos la accion de escribir o leer o escribir en el output para ahorrarnos escribirlo donde se llame a estas funciones(writeMessageToOutputStream y readMessageFromInputStream)
	public void sendMessage(PeerMessage msg) throws IOException {
		msg.writeMessageToOutputStream(dos);
	}
	
	public PeerMessage receiveMessage() throws IOException {
		return PeerMessage.readMessageFromInputStream(dis);
	}
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {}
	}
}
