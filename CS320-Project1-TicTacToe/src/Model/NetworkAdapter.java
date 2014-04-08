package Model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkAdapter {
	private ServerSocket serverSocket;
	private Socket clientSocket;
	boolean isHost;
	
	private InputStream inputStream;
	private OutputStream outputStream;
	
	
	public void connect(String IP) throws UnknownHostException, IOException {
		clientSocket = new Socket(InetAddress.getByName( IP ), 12345 );
		inputStream = clientSocket.getInputStream();
		outputStream = clientSocket.getOutputStream();
	}
	
	public boolean sendPacket(Packet packet) {
		
		
		return true;
	}
	
	public Packet receivePacket() {
		
		
		
		return null;
	}
	
	public void host() {
		
	}
	
	public void disconnect() {
		
	}
	
}
