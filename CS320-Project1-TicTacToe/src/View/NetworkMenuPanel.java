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
    private JButton hostGameButton;
    private JButton joinGameButton;
    private Controller controller;
    
    public NetworkMenuPanel(Controller controller) {
        this.controller = controller;
        createJoinGameButton();
        createHostGameButton();
    }
    
    private void createJoinGameButton() {
        joinGameButton = new JButton("Join Game");
        joinGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.joinGameButtonPressed();
            }
        });
        this.add(joinGameButton);
    }
    
    private void createHostGameButton() {
        hostGameButton = new JButton("Host Game");
        hostGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.hostGameButtonPressed();
            }
        });
        this.add(hostGameButton);
    }
}
