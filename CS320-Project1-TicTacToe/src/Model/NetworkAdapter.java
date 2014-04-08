package Model;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
    
    private InputStream inputStream;
    private OutputStream outputStream;
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
            inputStream = clientSocket.getInputStream();
            System.out.println("Input stream created");
            outputStream = clientSocket.getOutputStream();
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
            Packet packet = (Packet) in.readObject();
            MovePacket movePacket = (MovePacket) packet;
            return movePacket;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
