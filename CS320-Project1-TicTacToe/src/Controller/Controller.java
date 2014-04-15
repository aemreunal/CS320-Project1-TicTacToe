package Controller;

import javax.swing.JOptionPane;

import Model.GameLogic;
import Model.GameStatus;
import Model.MovePacket;
import Model.NetworkAdapter;
import Model.Winner;
import View.BoardButton;
import View.GameBoard;
import View.GameWindow;
import View.MainMenuPanel;
import View.NetworkMenuPanel;
import View.Player;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

public class Controller {
    private GameWindow gameWindow;
    private NetworkAdapter netAdapter;
    private GameLogic gameLogic;
    private GameBoard gameBoard;
    private GameStatus status = GameStatus.NOT_RUNNING;
    private int joinedGame = -1;
    
    public static void main(String[] args) {
        new Controller();
    }
    
    public Controller() {
        gameWindow = new GameWindow(this);
        showMainMenu();
    }
    
    private void createNetAdapter() {
        if (netAdapter == null) {
            netAdapter = new NetworkAdapter(this);
        }
    }
    
    private void destroyNetAdapter() {
        if (netAdapter != null) {
            netAdapter.disconnect();
            netAdapter = null;
        }
    }
    
    private void showMainMenu() {
        gameWindow.setCurrentPanel(new MainMenuPanel(this));
    }
    
    private void showNetworkMenu() {
        gameWindow.setCurrentPanel(new NetworkMenuPanel(this));
    }
    
    private void showRemoteAddrDialogue() {
        String ipAddr = JOptionPane.showInputDialog("Please enter the IP address of the host:");
        if (ipAddr != null && (!ipAddr.equals(""))) {
            connectButtonPressed(ipAddr);
        }
    }
    
    public void createGame(GameStatus status) {
        this.status = status;
        gameBoard = new GameBoard(this);
        gameWindow.setCurrentPanel(gameBoard);
        if (joinedGame == -1) {
            gameLogic = new GameLogic(this, netAdapter);
        } else if (joinedGame == 0 /* False, Player 1 */) {
            gameLogic = new GameLogic(this, netAdapter, Player.PLAYER_1);
        } else if (joinedGame == 1 /* True, Player 2 */) {
            gameLogic = new GameLogic(this, netAdapter, Player.PLAYER_2);
            receiveMove();
        }
        updateTurnLabel();
    }
    
    private void updateTurnLabel() {
        gameWindow.setTurn(gameLogic.getPlayer(), gameLogic.getTurn());
    }
    
    public void endGame(Winner winner) {
        destroyNetAdapter();
        if (winner != Winner.NOT_COMPLETED || winner != null) {
            showGameEndDialogue(winner);
        }
        status = GameStatus.NOT_RUNNING;
        gameWindow.clearTurn();
        joinedGame = -1;
        gameBoard = null;
        showMainMenu();
    }
    
    public void showNetworkTimeoutError() {
        JOptionPane.showMessageDialog(gameWindow, "Could not connect to host!");
    }
    
    private void showGameEndDialogue(Winner winner) {
        if (winner == Winner.DRAW) {
            JOptionPane.showMessageDialog(gameWindow, "It's a draw!");
        } else if (winner == Winner.PLAYER1) {
            JOptionPane.showMessageDialog(gameWindow, "Player 1 won!");
        } else if (winner == Winner.PLAYER2) {
            JOptionPane.showMessageDialog(gameWindow, "Player 2 won!");
        }
    }
    
    public void showTurnErrorDialogue() {
        JOptionPane.showMessageDialog(gameWindow, "Please wait for the other player to play.");
    }
    
    public void localGameButtonPressed() {
        createGame(GameStatus.LOCAL_GAME);
    }
    
    public void remoteGameButtonPressed() {
        showNetworkMenu();
    }
    
    public void hostGameButtonPressed() {
        gameWindow.setTitle("Waiting for connection...");
        createNetAdapter();
        if (netAdapter.host()) {
            System.out.println("Hosting game...");
            joinedGame = 0;
            createGame(GameStatus.REMOTE_GAME);
        }
    }
    
    public void joinGameButtonPressed() {
        showRemoteAddrDialogue();
    }
    
    public void connectButtonPressed(String ipAddr) {
        createNetAdapter();
        if (netAdapter.connect(ipAddr)) {
            System.out.println("Joining game...");
            joinedGame = 1;
            createGame(GameStatus.REMOTE_GAME);
        }
    }
    
    public void boardButtonPressed(BoardButton button) {
        if (gameLogic.pressButton(button, false)) {
            if (gameBoard != null) {
                gameBoard.updateUI();
                updateTurnLabel();
                receiveMove();
            }
        }
    }
    
    private void receiveMove() {
        MovePacket packet = netAdapter.receivePacket();
        boardButtonPressedOverNetwork(packet.getButtonID());
    }
    
    public synchronized void boardButtonPressedOverNetwork(int buttonID) {
        BoardButton pressedButton = gameBoard.getButton(buttonID);
        gameLogic.pressButton(pressedButton, true);
        updateTurnLabel();
        gameBoard.updateUI();
    }
    
    public boolean isLocalGame() {
        return status == GameStatus.LOCAL_GAME;
    }
    
    public GameStatus getGameStatus() {
        return status;
    }
}
