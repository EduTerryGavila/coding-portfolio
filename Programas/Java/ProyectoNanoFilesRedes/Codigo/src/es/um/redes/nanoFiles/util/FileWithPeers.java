package es.um.redes.nanoFiles.util;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * 		Clase descendiente de FileInfo que permite almacenar mas informacion sobre los ficheros,
 * 		como los servidores de ficheros que los tienen publicados. 
 */
public class FileWithPeers extends FileInfo{
	private HashSet<String> servers;
	
	public FileWithPeers() {
	}
	
	public FileWithPeers(String hash, String name, long size, String path) {
		super(hash, name, size, path);
		servers = new HashSet<String>(1);
		servers.add("Unknown Origin");
	}
	
	public FileWithPeers(String hash, String name, long size, String path, String... servers ) {
		super(hash, name, size, path);
		this.servers = new HashSet<String>();
		for (var s : servers)
			this.servers.add(s);		
	}
	
	public FileWithPeers(FileInfo file ) {
		super(file.getFileHash(), file.getFileName(), file.getFileSize(), file.getFilePath());
		this.servers = new HashSet<String>();		
	}
	
	public FileWithPeers(FileInfo file, Set<String> servers ) {
		super(file.getFileHash(), file.getFileName(), file.getFileSize(), file.getFilePath());
		this.servers = (HashSet<String>) servers;		
	}
	
	/**
	 * Obtiene los hosts que sirven este archivo.
	 */
	public InetSocketAddress[] getServers() {
		HashSet<InetSocketAddress> peerSet = new HashSet<InetSocketAddress>();
		for (var peer : servers) {
			String[] peerfields = peer.split(":");
			InetAddress addr = null;
			try {
				addr = InetAddress.getByName(peerfields[0]);
			} catch (UnknownHostException e) {
				continue;
			}
			int port = Integer.parseInt(peerfields[1]);
			peerSet.add(new InetSocketAddress(addr, port));
		}

		return peerSet.toArray(new InetSocketAddress[0]);
	}
	
	/**
	 * Añade un nuevo host a la lista de servidores actual.
	 */
	public void insertServer(String hostname, int port) {
		servers.add(hostname + ":" + port);
	}
	
	public void insertServer(String socket) {
		if (socket.matches("[\\w.-]+:\\d{1,5}")) {
			servers.add(socket);
		}
		else
			System.err.println("La cadena aportada no cumple con el formato \"hostname:puerto\"");
	}
	
	/**
	 * Elimina un host de la lista de servidores de este fichero.
	 * @param host
	 */
	public void deleteServer(String host) {
		servers.remove(host);
	}
	
	/**
	 * Elimina un host de la lista de servidores de este fichero.
	 */
	public void deleteServer(String hostName, int port ) {
		servers.remove(hostName + ":" + port);
	}
	
	public String toString() {
		StringBuffer cad = new StringBuffer(super.toString());
		cad.append("at ");
		servers.forEach(s -> cad.append(s + ", "));
		cad.replace(cad.lastIndexOf(","), cad.length(), "");
		return cad.toString();
	}
}