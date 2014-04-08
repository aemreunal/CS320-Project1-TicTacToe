package Model;

public class GameLogic {
    private int[][] board;
    private int turn;
    private int player;

    public GameLogic(){
    }

    public Winner checkForAWin() {
        for(int i = 0; i < board.length; i++){
            int rowSum = 0;
            for(int j = 0; j < board[0].length; j++){
                rowSum += board[i][j];
            }
            
        }
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
