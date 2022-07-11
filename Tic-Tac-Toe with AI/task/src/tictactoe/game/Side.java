package tictactoe.game;

public enum Side {FIRST, SECOND, NEUTRAL;
    public static Side getOpposite(Side side) {
        if (side == NEUTRAL) return NEUTRAL;
        if (side == FIRST) return SECOND;
        return FIRST;
    }
}
