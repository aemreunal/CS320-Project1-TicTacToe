package Model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import Controller.Controller;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * Eren Sezener
 * Deniz Sokmen
 * Erdi Gultekin
 */

public class NetworkAdapter {
    private static final int TIMEOUT = 5000;
    private static final int PORT = 12345;
    
    private ServerSocket serverSocket;
    private Socket clientSocket;
    
    private Controller controller;
    
    public NetworkAdapter(Controller controller) {
        this.controller = controller;
    }
    
    public boolean host() {
        try {
            serverSocket = new ServerSocket(PORT);
            clientSocket = serverSocket.accept();
            return true;
        } catch (Exception e) {
            controller.showNetworkTimeoutError();
            controller.endGame(null);
        }
        return false;
    }
    
    public boolean connect(String IP) {
        try {
            clientSocket = new Socket();
            clientSocket.connect(new InetSocketAddress(InetAddress.getByName(IP), PORT), TIMEOUT);
            return true;
        } catch (Exception e) {
            controller.showNetworkTimeoutError();
            controller.endGame(null);
        }
        return false;
    }
    
    public boolean sendPacket(MovePacket packet) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.writeObject(packet);
            out.flush();
        } catch (Exception e) {
            controller.showNetworkTimeoutError();
            controller.endGame(null);
        }
        return true;
    }
    
    public MovePacket receivePacket() {
        try {
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            return (MovePacket) in.readObject();
        } catch (Exception e) {
            controller.showNetworkTimeoutError();
            controller.endGame(null);
        }
        return null;
    }
    
    public void disconnect() {
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (Exception e) {
            controller.showNetworkTimeoutError();
        }
    }
}
