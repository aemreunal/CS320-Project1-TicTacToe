package Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
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
    
    public boolean connect(String IP) {
        try {
            clientSocket = new Socket();
            System.out.println("Created socket");
            clientSocket.connect(new InetSocketAddress(InetAddress.getByName(IP), 12345), 5000);
            System.out.println("Connected to socket");
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            System.out.println("Input stream created");
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            System.out.println("Output stream created");
            System.out.println("Connection success!");
            return true;
        } catch (SocketTimeoutException e) {
            System.err.println("Timeout error!");
            controller.showNetworkTimeoutError();
        } catch (UnknownHostException e) {
            System.err.println("Unknown host error!");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO error!");
            e.printStackTrace();
        }
        System.err.println("Unable to connect!");
        return false;
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
    
    public boolean host() {
        try {
            serverSocket = new ServerSocket(12345);
            clientSocket = serverSocket.accept();
            
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void disconnect() {
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
    public void run() {
        
    }
    
}
