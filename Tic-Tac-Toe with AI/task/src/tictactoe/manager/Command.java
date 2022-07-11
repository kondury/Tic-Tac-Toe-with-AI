package tictactoe.manager;

public enum Command {
    START, EXIT, UNKNOWN;

    public static Command getByCode(String code) {
        if ("start".equals(code)) {
            return Command.START;
        } else if ("exit".equals(code)) {
            return Command.EXIT;
        } else {
            return Command.UNKNOWN;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case START: return "start";
            case EXIT: return "exit";
            default: return "unknown";
        }
    }
}
