package tictactoe.game;

public class Cell {
    int r;
    int c;

    public Cell(int row, int column) {
        this.c = column;
        this.r = row;
    }


    public static Cell getByIndex(int index) {
        return new Cell(index / 3 + 1, index % 3 + 1);
    }
}
