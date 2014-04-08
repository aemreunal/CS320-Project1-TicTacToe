package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Controller.Controller;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

public class BoardButton extends JButton {
	private int buttonID;
	private Controller controller;
	
	public BoardButton(Controller cont, int id) {
		controller = cont;
		buttonID = id;
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.boardButtonPressed(buttonID);
			}
		});
	}
	
	public int getButtonID() {
		return buttonID;
	}
}