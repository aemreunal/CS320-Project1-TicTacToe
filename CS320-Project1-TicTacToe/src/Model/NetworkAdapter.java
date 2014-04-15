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

public class NetworkAdapter {
    private static final int TIMEOUT = 5000;
    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    boolean isHost;
    
    private Controller controller;
    
    public NetworkAdapter(Controller controller) {
        this.controller = controller;
    }
    
    public boolean connect(String IP) {
        try {
            clientSocket = new Socket();
            System.out.println("Created socket");
            clientSocket.connect(new InetSocketAddress(InetAddress.getByName(IP), PORT), TIMEOUT);
            System.out.println("Connected to socket");
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
    
    public boolean sendPacket(MovePacket packet) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.writeObject(packet);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Unable to create the ObjectOutputStream!");
            e.printStackTrace();
        }
        return true;
    }
    
    public MovePacket receivePacket() {
        try {
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            return (MovePacket) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean host() {
        try {
            serverSocket = new ServerSocket(PORT);
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
    
}
