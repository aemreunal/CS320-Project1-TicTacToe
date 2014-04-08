package Model;

import Controller.Controller;
import View.BoardButton;
import View.Player;

public class GameLogic {
    private int[][] board;
    private int turn;
    private int current_player;
    private Controller controller;
    private Player player;

    public GameLogic(Controller controller) {
        board = new int[3][3];
        turn = -1; // Player 1 starts the game
        current_player = -1;
        this.controller = controller;
    }
    
    public GameLogic(Controller controller, Player player) {
        this(controller);
        this.player = player;
    }
    
    public void checkForAWin() {
        boolean allCellAreOccupied = true;
        
        // Check each row and column for a win
        for (int i = 0; i < board.length; i++) {
            int rowSum = 0;
            int columnSum = 0;
            for (int j = 0; j < board[0].length; j++) {
                rowSum += board[i][j];
                columnSum += board[j][i];
            }
            if (rowSum == 3 || columnSum == 3) {
                controller.endGame(Winner.PLAYER2);
                return;
            } else if (rowSum == -3 || columnSum == -3) {
                controller.endGame(Winner.PLAYER1);
                return;
            }
        }
        
        // Check diagonals for a win
        int diagonalSum1 = board[0][0] + board[1][1] + board[2][2];
        int diagonalSum2 = board[2][0] + board[1][1] + board[0][2];
        if (diagonalSum1 == 3 || diagonalSum2 == 3) {
            controller.endGame(Winner.PLAYER2);
            return;
        } else if (diagonalSum1 == -3 || diagonalSum2 == -3) {
            controller.endGame(Winner.PLAYER1);
            return;
        }
        
        // Check if the game is a draw
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    allCellAreOccupied = false;
                }
            }
        }
        if (allCellAreOccupied) {
            controller.endGame(Winner.DRAW);
            return;
        }
    }
    
    public void pressButton(BoardButton button) {
        if (!controller.isLocalGame()) {
            current_player = ((player.equals(Player.PLAYER_1))? -1 : 1);
            if(turn != current_player) {
                controller.showTurnErrorDialogue();
                return;
            }
        } else {
            int id = button.getButtonID();
            if (turn == -1) {
                button.setText("X");
            } else {
                button.setText("O");
            }
            setPiece(id / 3, id % 3);
            checkForAWin();
        }
    }
    
    private void setPiece(int x, int y) {
        board[x][y] = turn;
        changeTurn();
    }
    
    public void changeTurn() {
        if (turn == 1) {
            turn = -1;
            current_player = -1;
        } else {
            turn = 1;
            current_player = 1;
        }
    }
    
    public Player getTurn() {
        return turn == -1 ? Player.PLAYER_1 : Player.PLAYER_2;
    }
    
    public Player getPlayer() {
        return player;
    }
}