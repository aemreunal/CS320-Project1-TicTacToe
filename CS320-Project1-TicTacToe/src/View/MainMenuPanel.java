package View;

import javax.swing.*;
import Controller.Controller;
/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

public class MainMenuPanel extends JPanel {
    private JButton localGameButton;
    private JButton remoteGameButton;
    private Controller controller;

    public MainMenuPanel(Controller controller){
        this.controller = controller;
        this.localGameButton = createButton("Start Local Game");
        this.remoteGameButton = createButton("Start Remote Game");
    }

    private JButton createButton(String buttonString){
        JButton button = new JButton(buttonString);
        this.add(button);
        return button;
    }
}
