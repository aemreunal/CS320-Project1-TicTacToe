package Controller;

import javax.swing.JPanel;

import Model.GameStatus;
import Model.NetworkAdapter;

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
	private GameModel gameModel;
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
	
	private void showGameEndDialogue() {
		
	}
	
	public void endGame(Winner winner) {
		
	}
}
