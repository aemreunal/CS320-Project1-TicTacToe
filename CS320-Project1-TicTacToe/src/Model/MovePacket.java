package Model;

import java.io.Serializable;

public class MovePacket implements Serializable {
    private int buttonID;
    
    public MovePacket(int buttonID) {
        this.buttonID = buttonID;
    }
    
    public int getButtonID() {
        return buttonID;
    }
}
