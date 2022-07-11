package tictactoe.player;

public enum PlayerMode {
    HUMAN, COMPUTER_EASY, COMPUTER_MEDIUM, COMPUTER_HARD, UNKNOWN;
    public static PlayerMode getByCode(String code) {
        if ("user".equals(code)) {
            return HUMAN;
        } else if ("easy".equals(code)) {
            return COMPUTER_EASY;
        } else if ("medium".equals(code)) {
            return COMPUTER_MEDIUM;
        } else if ("hard".equals(code)) {
            return  COMPUTER_HARD;
        } else {
            return UNKNOWN;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case HUMAN: return "user";
            case COMPUTER_EASY:  return "easy";
            case COMPUTER_MEDIUM: return "medium";
            case COMPUTER_HARD: return "hard";
            default: return "unknown";
        }
    }
}
