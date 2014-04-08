package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import Controller.Controller;
/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

public class NetworkMenuPanel extends JPanel {
	JButton[] boardButtons;
	Controller controller;
	
	public NetworkMenuPanel(Controller controller) {
		this.controller = controller;
		
		createButtons();
		setSize(300,300);
	}
	
	public void createButtons() {
		boardButtons = new JButton[2];
		boardButtons[0].setText("Host Game");
		boardButtons[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.hostGameButtonPressed();
			}
		});
		
		boardButtons[1].setText("Join Game");
		boardButtons[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.joinGameButtonPressed();
			}
		});
	}
}
