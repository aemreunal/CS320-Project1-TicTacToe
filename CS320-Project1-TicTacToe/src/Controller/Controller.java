package Controller;

import Model.GameLogic;
import Model.GameStatus;
import Model.NetworkAdapter;
import Model.Winner;
import View.BoardButton;
import View.GameBoard;
import View.GameWindow;
import View.MainMenuPanel;
import View.NetworkMenuPanel;

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
    }
    
    private void showMainMenu() {
        netAdapter.disconnect();
        gameLogic = new GameLogic(this);
        status = GameStatus.NOT_RUNNING;
        gameWindow.setCurrentPanel(new MainMenuPanel());
    }
    
    private void showRemoteAddrDialogue() {
        
    }
    
    public void startLocalGame() {
        
    }
    
    public void startHostedGame() {
        
    }
    
    public void startJoinedGame() {
        status = GameStatus.LOCAL_GAME;
        createGame();
    }
    
    private void createGame() {
        gameLogic = new GameLogic(this);
        gameWindow.setCurrentPanel(new GameBoard(this));
    }
    
    private void updateTurnLabel() {
        
    }
    
    public void endGame(Winner winner) {
        showGameEndDialogue(winner);
    }
    
    private void showGameEndDialogue(Winner winner) {
        
    }
    
    public void showTurnErrorDialogue() {
        
    }
    
    public void localGameButtonPressed() {
        startLocalGame();
    }
    
    public void remoteGameButtonPressed() {
        gameWindow.setCurrentPanel(new NetworkMenuPanel(this));
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
        createGame();
    }
    
    public void boardButtonPressed(BoardButton button) {
        gameLogic.pressButton(button.getButtonID());
        button.setButtonState(false);
    }
    
    public boolean isLocalGame() {
        return status == GameStatus.LOCAL_GAME;
    }
}
