package tictactoe.io;

import tictactoe.manager.*;
import tictactoe.game.*;
import tictactoe.player.PlayerMode;

import java.util.Properties;
import java.util.Scanner;

public class Reader {
    private final Messenger messenger;
    //private final TicTacToe game;
    private final Board board;

    public Reader(GameManager gameManager) {
        //this.game = game;
        this.messenger = gameManager.getMessenger();
        this.board = gameManager.getGameEngine().getBoard();
    }

    public Cell readCoordinates() {
        Scanner sc = new Scanner(System.in);
        int row = 0;
        int column = 0;

        boolean waitCoordinates = true;
        while (waitCoordinates) {
            messenger.printPrompt(Messenger.ENTER_THE_COORDINATES);

            if (!sc.hasNextInt()) {
                sc.nextLine();
                messenger.printMessage(Messenger.ERROR_NOT_NUMBERS);
                continue;
            }
            row = sc.nextInt();

            if (!sc.hasNextInt()) {
                sc.nextLine();
                messenger.printMessage(Messenger.ERROR_NOT_NUMBERS);
                continue;
            }
            column = sc.nextInt();

            if (row < 1 || row > 3 || column < 1 || column > 3) {
                messenger.printMessage(Messenger.ERROR_OUT_OF_RANGE);
                continue;
            } else if (!board.isEmptyCell(row, column)) {
                messenger.printMessage(Messenger.ERROR_CELL_IS_OCCUPIED);
                continue;
            }

            waitCoordinates = false;
        }

        return new Cell(row, column);
    }

    public Properties readCommand() {
        Properties properties = new Properties();
        boolean waitCommand = true;
        Scanner sc = new Scanner(System.in);
        Command command;
        while (waitCommand) {
            messenger.printPrompt(Messenger.INPUT_COMMAND);
            String[] input = sc.nextLine().split(" ");
            command = Command.getByCode(input[0]);
            switch (command) {
                case EXIT:
                    if (input.length == 1) {
                        properties.setProperty("command", command.name());
                        waitCommand = false;
                    }
                    break;
                case START:
                    if (input.length == 3) {
                        PlayerMode firstPlayerMode = PlayerMode.getByCode(input[1]);
                        PlayerMode secondPlayerMode = PlayerMode.getByCode(input[2]);
                        if (firstPlayerMode != PlayerMode.UNKNOWN &&
                                secondPlayerMode != PlayerMode.UNKNOWN){
                            properties.setProperty("command", command.name());
                            properties.setProperty("firstPlayerMode", firstPlayerMode.name());
                            properties.setProperty("secondPlayerMode", secondPlayerMode.name());
                            waitCommand = false;
                        }
                    }
                    break;
            }

            if (waitCommand) {
                messenger.printMessage(Messenger.BAD_PARAMETERS);
            }
        }
        return properties;
    }

//    public PlayerMode readPlayerMode() {
//        Scanner sc = new Scanner(System.in);
//        if (!sc.hasNextLine()) {
//            messenger.printMessage(Messenger.BAD_PARAMETERS + " 3");
//            return PlayerMode.UNKNOWN;
//        }
//
//        String code = sc.next();
//        PlayerMode playerMode = PlayerMode.getByCode(code);
//        if (playerMode == PlayerMode.UNKNOWN) {
//            messenger.printMessage(Messenger.BAD_PARAMETERS + " 4");
//        }
//        return playerMode;
//    }
}

