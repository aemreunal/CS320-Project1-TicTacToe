package Controller;

import javax.swing.JOptionPane;

import Model.GameLogic;
import Model.GameStatus;
import Model.NetworkAdapter;
import Model.Winner;
import View.BoardButton;
import View.GameBoard;
import View.GameWindow;
import View.MainMenuPanel;

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
    private GameStatus status = GameStatus.NOT_RUNNING;
    
    public static void main(String[] args) {
        new Controller();
    }
    
    public Controller() {
        netAdapter = new NetworkAdapter(this);
        gameWindow = new GameWindow(this);
        showMainMenu();
    }
    
    private void showMainMenu() {
        netAdapter.disconnect();
        gameLogic = new GameLogic(this);
        status = GameStatus.NOT_RUNNING;
        gameWindow.setCurrentPanel(new MainMenuPanel(this));
    }
    
    private void showRemoteAddrDialogue() {
        String ipAddr = JOptionPane.showInputDialog("Please enter the IP address of the host:");
        System.out.println(ipAddr);
        connectButtonPressed(ipAddr);
    }
    
    public void createGame(GameStatus status) {
        this.status = status;
        gameLogic = new GameLogic(this);
        gameWindow.setCurrentPanel(new GameBoard(this));
    }
    
    private void updateTurnLabel() {
        gameWindow.setTurn(gameLogic.getPlayer(), gameLogic.getTurn());
    }
    
    public void endGame(Winner winner) {
        if (winner != Winner.NOT_COMPLETED || winner != null) {
            showGameEndDialogue(winner);
        }
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
        showRemoteAddrDialogue();
    }
    
    public void hostGameButtonPressed() {
        // Update graphic
        netAdapter.host();
    }
    
    public void joinGameButtonPressed() {
        showRemoteAddrDialogue();
    }
    
    public void connectButtonPressed(String ipAddr) {
        netAdapter.connect(ipAddr);
    }
    
    public void boardButtonPressed(BoardButton button) {
        gameLogic.pressButton(button);
        button.setButtonState(false);
    }
    
    public boolean isLocalGame() {
        return status == GameStatus.LOCAL_GAME;
    }
}
