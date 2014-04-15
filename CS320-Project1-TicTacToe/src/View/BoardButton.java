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

@SuppressWarnings("serial")
public class BoardButton extends JButton {
    private int buttonID;
    private Controller controller;
    private BoardButton button = this;
    
    public BoardButton(Controller cont, int id) {
        controller = cont;
        buttonID = id;
        
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.boardButtonPressed(button);
            }
        });
        setSize(100, 100);
    }
    
    public void setButtonState(boolean state) {
        setEnabled(state);
    }
    
    public void setButtonText(String text) {
        setText(text);
    }
    
    public int getButtonID() {
        return buttonID;
    }
}