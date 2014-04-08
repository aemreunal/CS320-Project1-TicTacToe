package Controller;

import javax.swing.JPanel;

import Model.GameLogic;
import Model.GameStatus;
import Model.NetworkAdapter;
import Model.Winner;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

public class Controller {
	private JPanel mainMenuPanel;
	private JPanel remoteGameChoicePanel;
	private JPanel gameBoard;
	private NetworkAdapter netAdapter;
	private GameLogic gameModel;
	private GameStatus status;
	
	public static void main(String[] args) {
		new Controller();
	}
	
	public Controller() {
		netAdapter = new NetworkAdapter(this);
	}
	
	private void showMainMenu() {
		
	}
	
	private void showRemoteAddrDialogue() {
		
	}
	
	private void createGame() {
		
	}
	
	private void updateTurnLabel() {
		
	}
	
	public void endGame(Winner winner) {
		
	}
	
	private void showGameEndDialogue() {
		
	}
	
	public void showTurnErrorDialogue() {
		
	}
	
	public void localGameButtonPressed() {
		
	}
	
	public void remoteGameButtonPressed() {
		
	}
	
	public void hostGameButtonPressed() {
		
	}
	
	public void joinGameButtonPressed() {
		
	}
	
	public void connectButtonPressed(String ipAddr) {
		
	}
	
	public void boardButtonPressed(int buttonID) {
		
	}
	
	public boolean isLocalGame() {
		return status == GameStatus.LOCAL_GAME;
	}
}
