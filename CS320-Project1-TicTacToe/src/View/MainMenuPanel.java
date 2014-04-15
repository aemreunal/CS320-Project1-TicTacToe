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

@SuppressWarnings("serial")
public class MainMenuPanel extends JPanel {
    private JButton localGameButton;
    private JButton remoteGameButton;
    private Controller controller;
    
    public MainMenuPanel(Controller controller) {
        this.controller = controller;
        createLocalGameButton();
        createRemoteGameButton();
    }
    
    private void createRemoteGameButton() {
        remoteGameButton = new JButton("Start Remote Game");
        remoteGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.remoteGameButtonPressed();
            }
        });
        this.add(remoteGameButton);
    }
    
    private void createLocalGameButton() {
        localGameButton = new JButton("Start Local Game");
        localGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.localGameButtonPressed();
            }
        });
        this.add(localGameButton);
    }
}
