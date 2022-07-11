package tictactoe;

import tictactoe.manager.GameManager;

public class Main {

    public static void main(String[] args) {
        GameManager game = new GameManager();
        //game.getGameEngine().initBoard();
        game.run();
    }

}
