package View;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Controller.Controller;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

public class GameWindow extends JFrame {
    private JPanel currentPanel = null;
    private Controller controller;
    
    public GameWindow(Controller controller) {
        this.controller = controller;
        setWindowProperties();
    }
    
    private void setWindowProperties() {
        setSize(300, 500);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }
    
    public void setCurrentPanel(JPanel nextPanel) {
        if (currentPanel != null) {
            remove(currentPanel);
        }
        currentPanel = nextPanel;
        add(nextPanel);
        currentPanel.updateUI();
    }
    
    public void setTurn(Player thisPlayer, Player turn) {
        setTitle("Player: " + getPlayerText(thisPlayer) + " | Turn: " + getPlayerText(turn));
    }
    
    private String getPlayerText(Player player) {
        return (player == Player.PLAYER_1 ? "Player 1" : "Player 2");
    }
}
