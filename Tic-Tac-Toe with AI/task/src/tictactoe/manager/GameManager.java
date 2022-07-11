package tictactoe.manager;

import tictactoe.game.TicTacToe;
import tictactoe.io.Reader;
import tictactoe.io.Messenger;
import tictactoe.player.PlayerMode;

import java.util.Properties;

enum GameManagerState { WAIT_COMMAND, PLAY_GAME, EXIT }

public class GameManager {


    private static TicTacToe game;
    private static Reader reader;
    private static Messenger messenger;


    public GameManager() {
        game = new TicTacToe(this);
        messenger = new Messenger(this);
        reader = new Reader(this);
        game.setMessenger(messenger);
    }

    public void run() {
        PlayerMode firstPlayerMode = PlayerMode.UNKNOWN;
        PlayerMode secondPlayerMode = PlayerMode.UNKNOWN;
        GameManagerState managerState = GameManagerState.WAIT_COMMAND;
        while (managerState != GameManagerState.EXIT) {
            //System.out.println("managerState: " + managerState);
            switch (managerState) {
                case WAIT_COMMAND:
                    Properties properties = reader.readCommand();
                    Command command = Command.valueOf(properties.getProperty("command"));
                    if (command == Command.START) {
                        firstPlayerMode = PlayerMode.valueOf(properties.getProperty("firstPlayerMode"));
                        secondPlayerMode = PlayerMode.valueOf(properties.getProperty("secondPlayerMode"));
                        managerState = GameManagerState.PLAY_GAME;
                    } else if (command == Command.EXIT) {
                        managerState = GameManagerState.EXIT;
                    } else {
                        // wrong parsing in Reader, this branch has to be unreachable
                        messenger.printMessage(Messenger.BAD_PARAMETERS);
                    }
                    break;
                case PLAY_GAME:
                    game.start(firstPlayerMode, secondPlayerMode);
                    game.reset();
                    managerState = GameManagerState.WAIT_COMMAND;
                    break;
            }
        }
    }

    public TicTacToe getGameEngine() {
        return game;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public Reader getReader() {
        return reader;
    }
}
