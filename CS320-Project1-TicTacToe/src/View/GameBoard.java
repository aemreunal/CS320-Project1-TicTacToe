package View;

import java.awt.GridLayout;

import javax.swing.JPanel;

import Controller.Controller;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

public class GameBoard extends JPanel {
    BoardButton[] boardButtons;
    Controller controller;
    
    public GameBoard(Controller controller) {
        this.controller = controller;
        setLayout(new GridLayout(3, 3));
        createButtons();
        setSize(300, 300);
    }
    
    public void createButtons() {
        boardButtons = new BoardButton[9];
        for (int i = 0; i < 9; i++) {
            boardButtons[i] = new BoardButton(controller, i);
            add(boardButtons[i]);
        }
    }
    
    public BoardButton getButton(int buttonID) {
        for (BoardButton button : boardButtons) {
            if (button.getButtonID() == buttonID) {
                return button;
            }
        }
        return null;
    }
}
