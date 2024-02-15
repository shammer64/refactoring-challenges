package dev.scotthammer.refactoring.uglytrivia;
/*
    Credit to JB Rainsberger for the code provided at https://github.com/jbrains/trivia
    License: GPLv3 https://github.com/jbrains/trivia/blob/master/LICENSE.txt
 */
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Game {
    public static final int MIN_PLAYERS_REQUIRED = 2;
    public static final int MAX_PLAYERS_ALLOWED = 6;
    List<String> players = new ArrayList<>();
    int[] places = new int[MAX_PLAYERS_ALLOWED];
    int[] purses  = new int[MAX_PLAYERS_ALLOWED];
    boolean[] inPenaltyBox  = new boolean[MAX_PLAYERS_ALLOWED];

    Deque<String> popQuestions = new LinkedList<>();
    Deque<String> scienceQuestions = new LinkedList<>();
    Deque<String> sportsQuestions = new LinkedList<>();
    Deque<String> rockQuestions = new LinkedList<>();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public  Game(){
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast("Science Question " + i);
            sportsQuestions.addLast("Sports Question " + i);
            rockQuestions.addLast("Rock Question " + i);
        }
    }

    public boolean isPlayable() {
        return (players.size() >= MIN_PLAYERS_REQUIRED);
    }

    public void add(String playerName) {
        if (players.size() == MAX_PLAYERS_ALLOWED) {
            throw new IllegalStateException("Max players allowed in game is " + MAX_PLAYERS_ALLOWED);
        }
        int newPlayerIndex = players.size();
        players.add(playerName);
        places[newPlayerIndex] = 0;
        purses[newPlayerIndex] = 0;
        inPenaltyBox[newPlayerIndex] = false;

        logNewPlayerAdded(playerName);
    }

    public void roll(int roll) {
        logCurrentPlayerName();
        logCurrentRoll(roll);

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                logCurrentPlayerLeavingPenaltyBox();
                places[currentPlayer] = places[currentPlayer] + roll;
                if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

                logCurrentPlayerLocation();
                logCurrentCategory();
                askQuestion();
            } else {
                logCurrentPlayerRemainsInPenaltyBox();
                isGettingOutOfPenaltyBox = false;
            }
        } else {
            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

            logCurrentPlayerLocation();
            logCurrentCategory();
            askQuestion();
        }
    }

    private void askQuestion() {
        if (currentCategory().equals("Pop"))
            System.out.println(popQuestions.removeFirst());
        if (currentCategory().equals("Science"))
            System.out.println(scienceQuestions.removeFirst());
        if (currentCategory().equals("Sports"))
            System.out.println(sportsQuestions.removeFirst());
        if (currentCategory().equals("Rock"))
            System.out.println(rockQuestions.removeFirst());
    }


    private String currentCategory() {
        if (places[currentPlayer] == 0) return "Pop";
        if (places[currentPlayer] == 4) return "Pop";
        if (places[currentPlayer] == 8) return "Pop";
        if (places[currentPlayer] == 1) return "Science";
        if (places[currentPlayer] == 5) return "Science";
        if (places[currentPlayer] == 9) return "Science";
        if (places[currentPlayer] == 2) return "Sports";
        if (places[currentPlayer] == 6) return "Sports";
        if (places[currentPlayer] == 10) return "Sports";
        return "Rock";
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer]){
            if (isGettingOutOfPenaltyBox) {
                logCorrectAnswer();
                purses[currentPlayer]++;
                logCurrentPlayerCoins();

                boolean winner = didPlayerWin();
                moveToNextPlayer();

                return winner;
            } else {
                moveToNextPlayer();
                return true;
            }
        } else {
            logCorrectAnswer();
            purses[currentPlayer]++;
            logCurrentPlayerCoins();

            boolean winner = didPlayerWin();
            moveToNextPlayer();

            return winner;
        }
    }

    private void logNewPlayerAdded(String playerName) {
        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
    }

    private void logCurrentPlayerLeavingPenaltyBox() {
        System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
    }

    private void logCurrentPlayerRemainsInPenaltyBox() {
        System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
    }

    private void logCurrentCategory() {
        System.out.println("The category is " + currentCategory());
    }

    private void logCurrentPlayerLocation() {
        System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + places[currentPlayer]);
    }

    private void logCurrentRoll(int roll) {
        System.out.println("They have rolled a " + roll);
    }

    private void logCurrentPlayerName() {
        System.out.println(players.get(currentPlayer) + " is the current player");
    }

    private void logCurrentPlayerCoins() {
        System.out.println(players.get(currentPlayer)
                + " now has "
                + purses[currentPlayer]
                + " Gold Coins.");
    }

    private void logCorrectAnswer() {
        System.out.println("Answer was correct!!!!");
    }

    private void moveToNextPlayer() {
        currentPlayer = (currentPlayer + 1) % players.size();
    }

    public boolean wrongAnswer(){
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        moveToNextPlayer();
        return true;
    }


    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == 6);
    }
}