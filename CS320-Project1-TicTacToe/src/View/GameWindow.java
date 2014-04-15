package View;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Controller.Controller;
import Model.GameStatus;

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
        setSize(400, 500);
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
        add(currentPanel);
        currentPanel.updateUI();
    }
    
    public void clearTurn() {
        setTitle("");
    }
    
    public void setTurn(Player thisPlayer, Player turn) {
        if (controller.getGameStatus() != GameStatus.NOT_RUNNING) {
            if (controller.isLocalGame()) {
                setTitle("Turn: " + getPlayerText(turn));
            } else {
                setTitle("Player: " + getPlayerText(thisPlayer) + " | Turn: " + getPlayerText(turn));
            }
        }
    }
    
    public void indicateWaiting() {
        setTitle(getTitle() + " (Waiting net...)");
    }
    
    private String getPlayerText(Player player) {
        return (player == Player.PLAYER_1 ? "Player 1" : "Player 2");
    }
}
