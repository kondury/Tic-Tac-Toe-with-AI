package tictactoe.player;

import tictactoe.manager.GameManager;
import tictactoe.game.Side;
import tictactoe.game.TicTacToe;
import tictactoe.exception.NoEmptyCellsException;
import tictactoe.exception.OutOfTurnException;

public abstract class Player implements IPlayer {
    protected final TicTacToe game;
    protected Side side;

    public Side getSide() {
        return side;
    }

    public Player(GameManager manager, Side side) {
        this.game = manager.getGameEngine();
        this.side = side;
    }

    protected boolean isReadyToMove() {
        if (game.getLastMoveNumber() >= 9)
            throw new NoEmptyCellsException("There are no empty cells to make next move!");
        else if (game.getActiveSide() != side)
            throw new OutOfTurnException("Move is out of turn!");
        return true;
    }

}
