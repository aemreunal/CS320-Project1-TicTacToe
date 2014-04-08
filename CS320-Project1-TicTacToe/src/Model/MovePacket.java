package Model;

public class MovePacket extends Packet {
    private int buttonID;
    
    public MovePacket(int buttonID) {
        this.buttonID = buttonID;
    }
    
    public int getButtonID() {
        return buttonID;
    }
}
