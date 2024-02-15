package dev.scotthammer.refactoring.uglytrivia;

public class Player {
    private final String name;

    private int purse;

    private int place;
    boolean isInPenaltyBox;
    public Player(String name) {
        this.name = name;
        isInPenaltyBox = false;
        place = 0;
        purse = 0;
    }

    public String getName() {
        return name;
    }

    public boolean isInPenaltyBox() {
        return isInPenaltyBox;
    }

    public void setInPenaltyBox(boolean inPenaltyBox) {
        isInPenaltyBox = inPenaltyBox;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getPurse() {
        return purse;
    }

    public void incrementPurse() {
        purse++;
    }
}
