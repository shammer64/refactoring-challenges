package dev.scotthammer.refactoring.uglytrivia;

public class Player {
    private final String name;
    boolean isInPenaltyBox;

    public Player(String name) {
        this.name = name;
        isInPenaltyBox = false;
    }

    public boolean isInPenaltyBox() {
        return isInPenaltyBox;
    }

    public void setInPenaltyBox(boolean inPenaltyBox) {
        isInPenaltyBox = inPenaltyBox;
    }

    public String getName() {
        return name;
    }
}
