package dev.scotthammer.refactoring.uglytrivia;
/*
    Credit to JB Rainsberger for the code provided at https://github.com/jbrains/trivia
    License: GPLv3 https://github.com/jbrains/trivia/blob/master/LICENSE.txt
 */

import java.util.ArrayList;
import java.util.List;

public class Game {
    public static final int MIN_PLAYERS_REQUIRED = 2;
    public static final int MAX_PLAYERS_ALLOWED = 6;
    public static final int MAX_PLAYER_PLACE = 11;
    List<Player> players = new ArrayList<>();

    private final QuestionBank questionBank = new QuestionBank();
    int currentPlayerIndex = 0;
    Player currentPlayer = null;
    boolean isGettingOutOfPenaltyBox;

    public Game(){

    }

    public boolean isPlayable() {
        return (players.size() >= MIN_PLAYERS_REQUIRED);
    }

    public void add(String playerName) {
        if (players.size() >= MAX_PLAYERS_ALLOWED) {
            throw new IllegalStateException("Max players allowed in game is " + MAX_PLAYERS_ALLOWED);
        }
        Player player = new Player(playerName);
        players.add(player);
        if (currentPlayer == null) {
            currentPlayer = player;
        }
        logNewPlayerAdded(playerName);
    }

    public void roll(int roll) {
        logCurrentPlayerName();
        logCurrentRoll(roll);

        if (currentPlayer.isInPenaltyBox()) {
            handleRollForPlayerInPenaltyBox(roll);
        } else {
            handleRollForPlayer(roll);
        }
    }

    private void handleRollForPlayer(int roll) {
        addRollToPlace(roll);
        if (currentPlayer.getPlace() > MAX_PLAYER_PLACE)
            resetPlace();
        logCurrentPlayerLocation();
        logCurrentCategory();
        askQuestion();
    }

    private void handleRollForPlayerInPenaltyBox(int roll) {
        if (isEvenRoll(roll)) {
            isGettingOutOfPenaltyBox = true;
            logCurrentPlayerLeavingPenaltyBox();
            handleRollForPlayer(roll);
        } else {
            logCurrentPlayerRemainsInPenaltyBox();
            isGettingOutOfPenaltyBox = false;
        }
    }

    private boolean isEvenRoll(int roll) {
        return roll % 2 != 0;
    }

    private void addRollToPlace(int roll) {
        currentPlayer.setPlace(currentPlayer.getPlace() + roll);
    }

    private void resetPlace() {
        currentPlayer.setPlace(currentPlayer.getPlace() - 12);
    }

    private void askQuestion() {
        switch (currentCategory()) {
            case "Pop" -> System.out.println(questionBank.pop(QuestionBank.QuestionType.Pop));
            case "Science" -> System.out.println(questionBank.pop(QuestionBank.QuestionType.Science));
            case "Sports" -> System.out.println(questionBank.pop(QuestionBank.QuestionType.Sports));
            case "Rock" -> System.out.println(questionBank.pop(QuestionBank.QuestionType.Rock));
        }
    }


    private String currentCategory() {
        if (currentPlayer.getPlace() == 0) return "Pop";
        if (currentPlayer.getPlace() == 4) return "Pop";
        if (currentPlayer.getPlace() == 8) return "Pop";
        if (currentPlayer.getPlace() == 1) return "Science";
        if (currentPlayer.getPlace() == 5) return "Science";
        if (currentPlayer.getPlace() == 9) return "Science";
        if (currentPlayer.getPlace() == 2) return "Sports";
        if (currentPlayer.getPlace() == 6) return "Sports";
        if (currentPlayer.getPlace() == 10) return "Sports";
        return "Rock";
    }

    public boolean wasCorrectlyAnswered() {
        if (currentPlayer.isInPenaltyBox()){
            if (isGettingOutOfPenaltyBox) {
                logCorrectAnswer();
                currentPlayer.incrementPurse();
                logCurrentPlayerCoins();
                moveToNextPlayer();
                return stillNoWinner();
            } else {
                moveToNextPlayer();
                return true;
            }
        } else {
            logCorrectAnswer();
            currentPlayer.incrementPurse();
            logCurrentPlayerCoins();
            moveToNextPlayer();
            return stillNoWinner();
        }
    }

    private void logNewPlayerAdded(String playerName) {
        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
    }

    private void logCurrentPlayerLeavingPenaltyBox() {
        System.out.println(currentPlayer.getName() + " is getting out of the penalty box");
    }

    private void logCurrentPlayerRemainsInPenaltyBox() {
        System.out.println(currentPlayer.getName() + " is not getting out of the penalty box");
    }

    private void logCurrentCategory() {
        System.out.println("The category is " + currentCategory());
    }

    private void logCurrentPlayerLocation() {
        System.out.println(currentPlayer.getName()
                + "'s new location is "
                + currentPlayer.getPlace());
    }

    private void logCurrentRoll(int roll) {
        System.out.println("They have rolled a " + roll);
    }

    private void logCurrentPlayerName() {
        System.out.println(currentPlayer.getName() + " is the current player");
    }

    private void logCurrentPlayerCoins() {
        System.out.println(currentPlayer.getName()
                + " now has "
                + currentPlayer.getPurse()
                + " Gold Coins.");
    }

    private void logCorrectAnswer() {
        System.out.println("Answer was correct!!!!");
    }

    private void moveToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentPlayer = players.get(currentPlayerIndex);
    }

    public boolean wrongAnswer(){
        System.out.println("Question was incorrectly answered");
        System.out.println(currentPlayer.getName() + " was sent to the penalty box");
        currentPlayer.setInPenaltyBox(true);

        moveToNextPlayer();
        return true;
    }


    private boolean stillNoWinner() {
        return !(currentPlayer.getPurse() == 6);
    }
}