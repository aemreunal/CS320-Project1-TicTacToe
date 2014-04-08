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
        setSize(300, 300);
    }
    
    private void createJoinGameButton() {
        joinGameButton = new JButton("Join Game");
        joinGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.joinGameButtonPressed();
            }
        });
        add(joinGameButton);
    }
    
    private void createHostGameButton() {
        joinGameButton = new JButton("Host Game");
        joinGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.hostGameButtonPressed();
            }
        });
        add(hostGameButton);
    }
}
