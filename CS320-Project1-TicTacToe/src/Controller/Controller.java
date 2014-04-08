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
    private GameStatus status = GameStatus.NOT_RUNNING;
    private int joinedGame = -1;
    
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
        if (joinedGame == -1) {
            gameLogic = new GameLogic(this);
        } else if (joinedGame == 0 /* False, Player 1 */) {
            gameLogic = new GameLogic(this, Player.PLAYER_1);
        } else if (joinedGame == 1 /* True, Player 2 */) {
            gameLogic = new GameLogic(this, Player.PLAYER_2);
        }
        status = GameStatus.NOT_RUNNING;
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
        gameLogic = new GameLogic(this);
        gameWindow.setCurrentPanel(new GameBoard(this));
        updateTurnLabel();
    }
    
    private void updateTurnLabel() {
        gameWindow.setTurn(gameLogic.getPlayer(), gameLogic.getTurn());
    }
    
    public void endGame(Winner winner) {
        if (winner != Winner.NOT_COMPLETED || winner != null) {
            showGameEndDialogue(winner);
        }
        showMainMenu();
        status = GameStatus.NOT_RUNNING;
        gameWindow.clearTurn();
        joinedGame = -1;
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
        if (netAdapter.connect(ipAddr)) {
            System.out.println("Joining game...");
            joinedGame = 1;
            createGame(GameStatus.REMOTE_GAME);
        }
    }
    
    public void boardButtonPressed(BoardButton button) {
        gameLogic.pressButton(button);
        button.setButtonState(false);
        updateTurnLabel();
    }
    
    public boolean isLocalGame() {
        return status == GameStatus.LOCAL_GAME;
    }
    
    public GameStatus getGameStatus() {
        return status;
    }
}
