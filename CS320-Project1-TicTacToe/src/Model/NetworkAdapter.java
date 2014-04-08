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
	
	public NetworkAdapter(Controller controller) {
		this.controller = controller;
	}
	
	
	public void connect(String IP){
		try {
			clientSocket = new Socket(InetAddress.getByName( IP ), 12345 );
			controller.createGame(GameStatus.REMOTE_GAME);
			inputStream = new ObjectInputStream(clientSocket.getInputStream());
			outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean sendPacket(Packet packet) {
		try {
			outputStream.writeObject(packet);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public Packet receivePacket() {
		Packet packet = null;
		try {
			packet = (Packet) inputStream.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return packet;
	}
	
	public void host() {
		try {
			serverSocket = new ServerSocket(12345);
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		try {
			if (clientSocket != null && !clientSocket.isClosed())
				clientSocket.close();
			if (serverSocket != null && !serverSocket.isClosed())
				serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	@Override
	public void run() {
		
	}
	
}
