package Model;

import Controller.Controller;

public class GameLogic {
    private int[][] board;
    private int turn;
    private int player;
    private Controller controller;

    public GameLogic(Controller controller){
        this.board = new int[3][3];
        this.turn = - 1;
        this.player = -1;
        this.controller = controller;
    }

    public Winner checkForAWin() {
        boolean allCellAreOccupied = true;

        //Check each row and column for a win
        for(int i = 0; i < board.length; i++){
            int rowSum = 0;
            int columnSum = 0;
            for(int j = 0; j < board[0].length; j++){
                rowSum += board[i][j];
                columnSum += board[j][i];
            }
            if (rowSum == 3 || columnSum == 3){
                return Winner.PLAYER2;
            } else if (rowSum == -3 || columnSum == -3){
                return Winner.PLAYER1;
            }
        }

        //Check diagonals for a win
        int diagonalSum1 = board[0][0] + board[1][1] + board[2][2];
        int diagonalSum2 = board[2][0] + board[1][1] + board[0][2];
        if(diagonalSum1 == 3 || diagonalSum2 == 3){
            return Winner.PLAYER1;
        } else if (diagonalSum1 == -3 || diagonalSum2 == -3) {
            return Winner.PLAYER2;
        }

        //Check if the game is a draw
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0){
                    allCellAreOccupied = false;
                }
            }
        }
       return allCellAreOccupied ? Winner.DRAW : Winner.NOT_COMPLETED;
    }

    public void setPiece(int x, int y){

        if(!controller.isLocalGame() && turn != player){
            //TODO show button or do nothing
        } else {
            board[x][y] = turn;
            changeTurn();
        }
    }

    public void changeTurn(){
        if(turn == 1){
            turn = -1;
            player = -1;
        } else {
            turn = 1;
            player = 1;
        }
    }

    public int getTurn(){
        return this.turn;
    }
}