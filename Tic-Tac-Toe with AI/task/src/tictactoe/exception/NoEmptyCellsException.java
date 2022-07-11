package tictactoe.exception;

public class NoEmptyCellsException extends RuntimeException {

    public NoEmptyCellsException(String message) {
        super(message);
    }

    public NoEmptyCellsException(String message, Throwable cause) {
        super(message, cause);
    }
}
