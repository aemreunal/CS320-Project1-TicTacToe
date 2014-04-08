package View;

import javax.swing.JButton;
import javax.swing.JPanel;

import Controller.Controller;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

public class GameBoard extends JPanel {
	JButton[] boardButtons;
	Controller controller;
	
	public GameBoard(Controller controller) {
		this.controller = controller;
	}
	
	public void createButton() {
		boardButtons = new BoardButton[9];
		for(int i = 0; i < 9; i++) {
			boardButtons[i] = new BoardButton(controller, i);
			add(boardButtons[i]);
		}
	}
	
	public void createLabel() {
		
	}
	
	public void setTurn(int turn) {
		
	}
	
	
}
