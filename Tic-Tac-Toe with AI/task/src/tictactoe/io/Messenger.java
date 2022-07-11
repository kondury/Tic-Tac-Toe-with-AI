package tictactoe.io;

import tictactoe.game.Board;
import tictactoe.manager.GameManager;
import tictactoe.game.Side;

public class Messenger {
    public static final String ENTER_CELLS = "Enter the cells:";
    public static final String ENTER_THE_COORDINATES = "Enter the coordinates:";
    public static final String ERROR_CELL_IS_OCCUPIED = "This cell is occupied! Choose another one!";
    public static final String ERROR_NOT_NUMBERS = "You should enter numbers!";
    public static final String ERROR_OUT_OF_RANGE = "Coordinates should be from 1 to 3!";
    // public static final String GAME_NOT_FINISHED_RESULT = "Game not finished";
    public static final String WINNER_TMPL_RESULT = "%s wins";
    public static final String DRAW_RESULT = "Draw";
    public static final String COMPUTER_PLAYER_MOVE_TMPL = "Making move level \"%s\"";
    public static final String INPUT_COMMAND = "Input command: ";
    public static final String BAD_PARAMETERS = "Bad parameters!";
    private final Board board;

    //private final TicTacToe game;

    public Messenger(GameManager gameManager) {
        this.board = gameManager.getGameEngine().getBoard();
    }

    public void printPrompt(String prompt) {
        System.out.print(prompt);
    }

    public void refreshBoard() {
        System.out.println("---------");
        for (int r = 1; r <= 3; r++) {
            System.out.print("|");
            for (int c = 1; c <= 3; c++) {
                System.out.print(" " + Board.getSideCell(board.getCell(r, c)));
            }
            System.out.println(" |");
        }
        System.out.println("---------");
    }

    public void printWinner(Side side) {
        char ch = Board.getSideCell(side);
        System.out.printf(WINNER_TMPL_RESULT + "%n",ch);
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printMessage(String message, String param) {
        System.out.printf(message + "%n",param);
    }
}
