package Model;

import java.io.EOFException;
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
    private boolean isHost;
    private boolean listening = false;
    
    private InputStream inputStream;
    private OutputStream outputStream;
    private Controller controller;
    
    private boolean gameHasNotStarted = true;
    
    private static final int PORT = 53784;
    
    public NetworkAdapter(Controller controller) {
        this.controller = controller;
    }
    
    public boolean connect(String IP) {
        try {
            clientSocket = new Socket();
            System.out.println("Created socket");
            clientSocket.connect(new InetSocketAddress(InetAddress.getByName(IP), PORT), 5000);
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
    
    public void sendPacket(Packet packet) {
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
    }
    
    public void startNetInterface() {
        gameHasNotStarted = false;
    }
    
    public synchronized void startListening() {
        listening = true;
    }
    
    public synchronized void stopListening() {
        listening = false;
    }
    
    public boolean isListening() {
        return listening;
    }
    
    public boolean receivePacket() {
        try {
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            Packet packet = (Packet) in.readObject();
            MovePacket movePacket = (MovePacket) packet;
            System.out.println("Got move " + movePacket.getButtonID());
            controller.boardButtonPressedOverNetwork(movePacket.getButtonID());
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) {
            // Means the connection is terminated
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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
        while (true) {
            if (listening) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
                System.out.println("Now listening for packet...");
                if (!receivePacket()) {
                    controller.endGame(Winner.NOT_COMPLETED);
                }
                System.out.println("Got packet!");
            }
        }
    }
}
