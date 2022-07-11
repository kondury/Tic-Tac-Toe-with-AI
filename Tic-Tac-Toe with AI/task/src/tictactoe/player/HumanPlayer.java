package tictactoe.player;

import tictactoe.manager.GameManager;
import tictactoe.io.Reader;
import tictactoe.game.Side;

public class HumanPlayer extends Player {

    private final Reader reader;

    public HumanPlayer(GameManager manager, Side side) {
        super(manager, side);
        this.reader = manager.getReader();
    }

    @Override
    public void makeNextMove() {
        if (!isReadyToMove()) return;
        game.registerMove(reader.readCoordinates());
    }

}
