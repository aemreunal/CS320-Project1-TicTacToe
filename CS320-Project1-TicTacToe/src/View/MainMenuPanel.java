package View;

import javax.swing.*;
import Controller.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        this.createRemoteGameButton();
        this.createLocalGameButton();
    }

    private void createRemoteGameButton() {
        this.remoteGameButton = new JButton("Start Remote Game");
        this.remoteGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                this.controller.boardButtonPressed(remoteGameButton);
            }
        });
        this.add(remoteGameButton);
    }

    private void createLocalGameButton() {
        this.localGameButton = new JButton("Start Local Game");
        this.localGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                this.controller.boardButtonPressed(localGameButton);
            }
        });
        this.add(localGameButton);
    }
}
