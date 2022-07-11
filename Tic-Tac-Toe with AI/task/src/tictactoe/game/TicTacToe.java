package tictactoe.game;

import tictactoe.manager.GameManager;
import tictactoe.io.Messenger;
import tictactoe.player.ComputerPlayer;
import tictactoe.player.HumanPlayer;
import tictactoe.player.IPlayer;
import tictactoe.player.PlayerMode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;


// todo 1) Refactor using Clear architecture principles and design patterns

enum GameState { FIRST_PLAYER_MOVE, SECOND_PLAYER_MOVE, FIRST_WINS, SECOND_WINS, DRAW }

public class TicTacToe {
    GameManager manager;
    private Messenger messenger;
    //private final Reader reader;
    private final Board board;
    private IPlayer firstPlayer;
    private IPlayer secondPlayer;

    public LinkedList<Cell> getMovesHistory() {
        return moves;
    }

    //private int lastMoveNumber;
    private final LinkedList<Cell> moves;

    public TicTacToe(GameManager manager) {
        this.manager = manager;
        this.board = new Board();
        moves = new LinkedList<>();
        //this.lastMoveNumber = 0;
    }

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    public Board getBoard() {
        return board;
    }

    public void reset() {
        firstPlayer = null;
        secondPlayer = null;
        //lastMoveNumber = 0;
        board.reset();
        moves.clear();
    }

    public int getLastMoveNumber() {
        return moves.size();
        //return lastMoveNumber;
    }

    public void initBoard() {
        Scanner scanner = new Scanner(System.in);
        messenger.printPrompt(Messenger.ENTER_CELLS);
        String fieldStr = scanner.nextLine();
        for (int r = 1; r <= 3; r++) {
            for (int c = 1; c <= 3; c++) {
                char ch = fieldStr.charAt(3 * r + c - 4);
                if (ch != '_') {
                    Cell cell = new Cell(r, c);
                    board.setCell(cell, Board.getSide(ch));
                    moves.addLast(cell);
                }
            }
        }
    }

    private IPlayer getPlayer(Side side, PlayerMode mode) {
        switch (mode) {
            case HUMAN: return new HumanPlayer(manager, side);
            case COMPUTER_EASY:
            case COMPUTER_MEDIUM:
            case COMPUTER_HARD:
                return new ComputerPlayer(manager, side, mode);
            default: return null;
        }
    }

    public void start(PlayerMode firstPlayerMode, PlayerMode secondPlayerMode) {
        firstPlayer = getPlayer(Side.FIRST, firstPlayerMode);
        secondPlayer = getPlayer(Side.SECOND, secondPlayerMode);
        boolean isFinished = false;
        messenger.refreshBoard();
        GameState state = GameState.FIRST_PLAYER_MOVE;
        Cell cell = null;
        while (!isFinished) {
            switch (state) {
                case FIRST_PLAYER_MOVE: {
                    firstPlayer.makeNextMove();
                    break;
                }
                case SECOND_PLAYER_MOVE: {
                    secondPlayer.makeNextMove();
                    break;
                }
            }
            messenger.refreshBoard();
            state = calcState();
            isFinished = (state != GameState.FIRST_PLAYER_MOVE && state != GameState.SECOND_PLAYER_MOVE);
        }
        endWithResult(state);
    }

    private GameState calcState() {
        if (isWinner(Side.FIRST)) {
            return GameState.FIRST_WINS;
        } else if (isWinner(Side.SECOND)) {
            return GameState.SECOND_WINS;
        } else if (getLastMoveNumber() == 9) {
            return GameState.DRAW;
        } else if (getActiveSide() == Side.FIRST)
            return GameState.FIRST_PLAYER_MOVE;
        else {
            return GameState.SECOND_PLAYER_MOVE;
        }

    }

    public void registerMove(Cell cell) {
        board.setCell(cell, getActiveSide());
        moves.addLast(cell);
    }

    public void undoLastMove() {
        board.setCell(moves.getLast(), Side.NEUTRAL);
        moves.removeLast();
    }
    
    public Side getActiveSide() {
        if (getLastMoveNumber() % 2 == 0)
            return Side.FIRST;
        else
            return Side.SECOND;
    }

    public boolean isWinner(Side side) {
        return board.getCell(1, 1) == side && board.getCell(1, 2) == side && board.getCell(1, 3) == side
                || board.getCell(2, 1) == side && board.getCell(2, 2) == side && board.getCell(2, 3) == side
                || board.getCell(3, 1) == side && board.getCell(3, 2) == side && board.getCell(3, 3) == side
                || board.getCell(1, 1) == side && board.getCell(2, 1) == side && board.getCell(3, 1) == side
                || board.getCell(1, 2) == side && board.getCell(2, 2) == side && board.getCell(3, 2) == side
                || board.getCell(1, 3) == side && board.getCell(2, 3) == side && board.getCell(3, 3) == side
                || board.getCell(1, 1) == side && board.getCell(2, 2) == side && board.getCell(3, 3) == side
                || board.getCell(3, 1) == side && board.getCell(2, 2) == side && board.getCell(1, 3) == side;
    }

    private void endWithResult(GameState state) {
        switch (state) {
            case FIRST_WINS: {
                messenger.printWinner(Side.FIRST);
                break;
            }
            case SECOND_WINS: {
                messenger.printWinner(Side.SECOND);
                break;
            }
            case DRAW: {
                messenger.printMessage(Messenger.DRAW_RESULT);
                break;
            }
            default:
                break;
        }
    }

    public Side testMoveResult(Cell cell, Side testingSide) {
        if (!board.isEmptyCell(cell)) {
            return Side.NEUTRAL; // no need to test move as the cell is occupied already
        }

        Side result = Side.NEUTRAL;
        board.setCell(cell, testingSide); // temporary occupy the chosen cell
        if (isWinner(testingSide)) {
            result = testingSide;
        } else {
            Side oppositeSite = Side.getOpposite(testingSide);
            board.setCell(cell, oppositeSite);
            if (isWinner(oppositeSite)) {
                result = oppositeSite;
            }
        }
        board.setCell(cell, Side.NEUTRAL);
        return result;
    }
}