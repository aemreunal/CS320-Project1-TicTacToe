package Model;

public class GameLogic {
    private static enum Winner{
        NOT_COMPLETED, DRAW, PLAYER1, PLAYER2
    }

    private int[][] board;
    private int turn;
    private int player;

    public GameLogic(){
    }

    public Winner checkForAWin() {
        return null;
    }

    public void setPiece(int x, int y){
    }

    public int getTurn(){
        return this.turn;
    }

    public void changeTurn(){

    }
}
