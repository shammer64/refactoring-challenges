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
    List<Player> players = new ArrayList<>();
    int[] places = new int[MAX_PLAYERS_ALLOWED];
    int[] purses  = new int[MAX_PLAYERS_ALLOWED];
    boolean[] inPenaltyBox  = new boolean[MAX_PLAYERS_ALLOWED];

    Deque<String> popQuestions = new LinkedList<>();
    Deque<String> scienceQuestions = new LinkedList<>();
    Deque<String> sportsQuestions = new LinkedList<>();
    Deque<String> rockQuestions = new LinkedList<>();

    int currentPlayerIndex = 0;
    Player currentPlayer = null;
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
        if (players.size() >= MAX_PLAYERS_ALLOWED) {
            throw new IllegalStateException("Max players allowed in game is " + MAX_PLAYERS_ALLOWED);
        }
        int newPlayerIndex = players.size();
        Player player = new Player(playerName);
        players.add(player);
        if (currentPlayer == null) {
            currentPlayer = player;
        }
        places[newPlayerIndex] = 0;
        purses[newPlayerIndex] = 0;
        inPenaltyBox[newPlayerIndex] = false;

        logNewPlayerAdded(playerName);
    }

    public void roll(int roll) {
        logCurrentPlayerName();
        logCurrentRoll(roll);

        if (inPenaltyBox[currentPlayerIndex]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                logCurrentPlayerLeavingPenaltyBox();
                places[currentPlayerIndex] = places[currentPlayerIndex] + roll;
                if (places[currentPlayerIndex] > 11) places[currentPlayerIndex] = places[currentPlayerIndex] - 12;

                logCurrentPlayerLocation();
                logCurrentCategory();
                askQuestion();
            } else {
                logCurrentPlayerRemainsInPenaltyBox();
                isGettingOutOfPenaltyBox = false;
            }
        } else {
            places[currentPlayerIndex] = places[currentPlayerIndex] + roll;
            if (places[currentPlayerIndex] > 11) places[currentPlayerIndex] = places[currentPlayerIndex] - 12;

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
        if (places[currentPlayerIndex] == 0) return "Pop";
        if (places[currentPlayerIndex] == 4) return "Pop";
        if (places[currentPlayerIndex] == 8) return "Pop";
        if (places[currentPlayerIndex] == 1) return "Science";
        if (places[currentPlayerIndex] == 5) return "Science";
        if (places[currentPlayerIndex] == 9) return "Science";
        if (places[currentPlayerIndex] == 2) return "Sports";
        if (places[currentPlayerIndex] == 6) return "Sports";
        if (places[currentPlayerIndex] == 10) return "Sports";
        return "Rock";
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayerIndex]){
            if (isGettingOutOfPenaltyBox) {
                logCorrectAnswer();
                purses[currentPlayerIndex]++;
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
            purses[currentPlayerIndex]++;
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
                + places[currentPlayerIndex]);
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
                + purses[currentPlayerIndex]
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
        inPenaltyBox[currentPlayerIndex] = true;

        moveToNextPlayer();
        return true;
    }


    private boolean didPlayerWin() {
        return !(purses[currentPlayerIndex] == 6);
    }
}