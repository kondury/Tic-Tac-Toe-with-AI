package tictactoe.game;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private static final char FIRST_SIDE_CELL = 'X';
    private static final char SECOND_SIDE_CELL = 'O';
    private static final char EMPTY_CELL = ' ';

    Side[] board;

    public static char getSideCell(Side side) {
        if (side == Side.FIRST)
            return FIRST_SIDE_CELL;
        else if (side == Side.SECOND)
            return SECOND_SIDE_CELL;
        else
            return EMPTY_CELL;
    }

    public static Side getSide(char ch) {
        if (ch == FIRST_SIDE_CELL) {
            return Side.FIRST;
        } else if (ch == SECOND_SIDE_CELL) {
            return Side.SECOND;
        } else {
            return Side.NEUTRAL;
        }
    }

    public Board() {
        board = new Side[9];
        reset();
    }

    public void reset() {
        Arrays.fill(board, Side.NEUTRAL);
    }

    // return state of the cell in client coordinates [1..3, 1..3]
    public Side getCell(int r, int c) {
        return board[3 * r + c - 4];
    }

    // check cell state of the cell; r, c: row and column in client coordinates [1..3, 1..3]
    public boolean isEmptyCell(int r, int c) {
        return board[3 * r + c - 4] == Side.NEUTRAL;
    }

    public boolean isEmptyCell(Cell cell) {
        return isEmptyCell(cell.r, cell.c);
    }

    public void setCell(Cell cell, Side side) {
        int r = cell.r;
        int c = cell.c;
        board[3 * r + c - 4] = side;
    }

    public ArrayList<Cell> getEmptyCells() {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (board[i] == Side.NEUTRAL) {
                cells.add(new Cell(i / 3 + 1, i % 3 + 1));
            }
        }
        return cells;
    }

    public static int getCellIndex(Cell cell) {
        return cell.r * 3 + cell.c - 4;
    }
}
