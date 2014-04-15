package Model;

import Controller.Controller;
import View.BoardButton;
import View.Player;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * Eren Sezener
 * Deniz Sokmen
 * Erdi Gultekin
 */

public class GameLogic {
    private int[][] board;
    private Player turn = Player.PLAYER_1;
    private Controller controller;
    private NetworkAdapter netAdapter;
    private Player currentPlayer;
    
    public GameLogic(Controller controller, NetworkAdapter netAdapter) {
        board = new int[3][3];
        this.controller = controller;
        this.netAdapter = netAdapter;
    }
    
    public GameLogic(Controller controller, NetworkAdapter netAdapter, Player player) {
        this(controller, netAdapter);
        currentPlayer = player;
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
    
    public boolean pressButton(BoardButton button, boolean remoteMove) {
        if (!controller.isLocalGame() && !remoteMove) {
            // If it is a remote game & not a remote move, then it may be a turn error.
            if (turn != currentPlayer) {
                controller.showTurnErrorDialogue();
                return false;
            }
            if (board[button.getButtonID() / 3][button.getButtonID() % 3] != 0) {
                return false;
            }
            sendButton(button);
        }
        setButtonProperties(button);
        updateModel(button.getButtonID());
        return true;
    }
    
    private void setButtonProperties(BoardButton button) {
        button.setButtonState(false);
        if (turn == Player.PLAYER_1) {
            button.setText("X");
        } else if (turn == Player.PLAYER_2) {
            button.setText("O");
        }
    }
    
    private void updateModel(int buttonID) {
        setPiece(buttonID / 3, buttonID % 3);
        checkForAWin();
        changeTurn();
    }
    
    private void sendButton(BoardButton button) {
        if (controller.getGameStatus() == GameStatus.REMOTE_GAME) {
            netAdapter.sendPacket(new MovePacket(button.getButtonID()));
        }
    }
    
    private void setPiece(int x, int y) {
        board[x][y] = (turn == Player.PLAYER_1 ? -1 : 1);
    }
    
    public void changeTurn() {
        if (turn == Player.PLAYER_1) {
            turn = Player.PLAYER_2;
        } else {
            turn = Player.PLAYER_1;
        }
    }
    
    public Player getTurn() {
        return turn;
    }
    
    public Player getPlayer() {
        return currentPlayer;
    }
}