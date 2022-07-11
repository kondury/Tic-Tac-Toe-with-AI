package tictactoe.player;

import tictactoe.game.Board;
import tictactoe.game.Cell;
import tictactoe.game.Side;
import tictactoe.io.Messenger;
import tictactoe.manager.GameManager;

import java.util.*;

public class ComputerPlayer extends Player {
    private final PlayerMode mode;
    private final Board board;
    private final Messenger messenger;

    public ComputerPlayer(GameManager manager, Side side, PlayerMode mode) {
        super(manager, side);
        this.mode = mode;
        this.messenger = manager.getMessenger();
        this.board = manager.getGameEngine().getBoard();        
    }
    
    protected void announceMove() {
        messenger.printMessage(Messenger.COMPUTER_PLAYER_MOVE_TMPL, mode.toString());
    }

    protected Cell getRandomMove() {
        ArrayList<Cell> cells = board.getEmptyCells();
        if (cells.size() < 1) return null;
        Random random = new Random();
        int n = random.nextInt(cells.size());
        return cells.get(n);
    }

    public void makeNextMove() {
        if (!isReadyToMove()) return;

        announceMove();
        Cell cell = null;
        if (mode == PlayerMode.COMPUTER_HARD) {
            cell = findBestMove();
        } else if (mode == PlayerMode.COMPUTER_MEDIUM) {
            cell = findUrgentMove();
        }
        if (cell == null) {
            cell = getRandomMove();
        }
        game.registerMove(cell);
    }

    private Cell findBestMove() {
        int[] cellsScores = new int[9];
        Cell move = null;
        if (game.getLastMoveNumber() != 0) {
            move = findBestMove(cellsScores);
        }
        return move;
    }

    private Cell findBestMove(int[] globalCellsScores) {
        Cell lastMove = game.getMovesHistory().getLast();
        int lastMoveIndex = Board.getCellIndex(lastMove);
        if (game.isWinner(side)) {
             globalCellsScores[lastMoveIndex] = 10;
            return null;
        } else if (game.isWinner(Side.getOpposite(side))) {
            globalCellsScores[lastMoveIndex] = -10;
            return null;
        } else if (game.getLastMoveNumber() == 9) {
            globalCellsScores[lastMoveIndex] = 0;
            return null;
        }

        ArrayList<Cell> emptyCells = board.getEmptyCells();
        int[] cellsScores = new int[9];
        for (Cell move : emptyCells) {
            game.registerMove(move);
            findBestMove(globalCellsScores);
            game.undoLastMove();
            cellsScores[Board.getCellIndex(move)] = globalCellsScores[Board.getCellIndex(move)];
        }

        Cell bestMove = null;
        int bestScore = game.getActiveSide() == side ? -1000 : 1000;
        for (Cell move: emptyCells) {
            Side activeSide = game.getActiveSide();
            int currentScore = cellsScores[Board.getCellIndex(move)];
            if (side == activeSide  && currentScore > bestScore
                    || Side.getOpposite(side) == activeSide && currentScore < bestScore) {
                bestScore = cellsScores[Board.getCellIndex(move)];
                bestMove = move;
            }
        }

        globalCellsScores[lastMoveIndex] = bestScore;
        return bestMove;
    }

    private Cell findUrgentMove() {
        ArrayList<Cell> emptyCells = board.getEmptyCells();
        Cell bestMove = null;
        for (Cell move : emptyCells) {
            Side winnerSide = game.testMoveResult(move, side);
            if (winnerSide != Side.NEUTRAL) {
                bestMove = move;
            }
            if (side == winnerSide) {
                break;
            }
        }
        return  bestMove;
    }

}

