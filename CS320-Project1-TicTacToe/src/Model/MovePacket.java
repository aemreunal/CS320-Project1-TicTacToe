package Model;

import java.io.Serializable;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * Eren Sezener
 * Deniz Sokmen
 * Erdi Gultekin
 */

@SuppressWarnings("serial")
public class MovePacket implements Serializable {
    private int buttonID;
    
    public MovePacket(int buttonID) {
        this.buttonID = buttonID;
    }
    
    public int getButtonID() {
        return buttonID;
    }
}
