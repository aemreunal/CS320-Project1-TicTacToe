package Model;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import Controller.Controller;

public class NetworkAdapter implements Runnable {
	private ServerSocket serverSocket;
	private Socket clientSocket;
	boolean isHost;

	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private Controller controller;
	
	public static void main(String args[]) throws IOException {
		NetworkAdapter adapter = new NetworkAdapter(null);
	}
	public NetworkAdapter(Controller controller) {
		this.controller = controller;
	}
	
	
	public void connect(String IP) throws UnknownHostException, IOException {
		clientSocket = new Socket(InetAddress.getByName( IP ), 12345 );
		inputStream = new ObjectInputStream(clientSocket.getInputStream());
		outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
	}
	
	public boolean sendPacket(Packet packet) throws IOException {
		outputStream.writeObject(packet);
		outputStream.flush();
		return true;
	}
	
	public Packet receivePacket() throws ClassNotFoundException, IOException {
		Packet packet = (Packet) inputStream.readObject();
		return packet;
	}
	
	public void host() throws IOException {
		serverSocket = new ServerSocket(12345);
		clientSocket = serverSocket.accept();
	}
	
	public void disconnect() throws IOException {
		clientSocket.close();
		serverSocket.close();
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
