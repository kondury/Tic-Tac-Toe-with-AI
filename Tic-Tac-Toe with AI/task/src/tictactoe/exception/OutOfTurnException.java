package tictactoe.exception;

public class OutOfTurnException extends RuntimeException {
    public OutOfTurnException(String message) {
        super(message);
    }

    public OutOfTurnException(String message, Throwable cause) {
        super(message, cause);
    }
}
