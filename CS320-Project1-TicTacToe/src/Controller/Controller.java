package Controller;

import Model.GameLogic;
import Model.GameStatus;
import Model.NetworkAdapter;
import Model.Winner;
import View.GameWindow;

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
		gameLogic = new GameLogic(this);
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
