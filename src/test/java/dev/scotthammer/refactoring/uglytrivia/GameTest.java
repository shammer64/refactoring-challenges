package dev.scotthammer.refactoring.uglytrivia;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void characterizationTest1() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Game aGame = new Game();
        aGame.add("Chet");
        aGame.add("Sue");

        for (int i = 0; i < 100; i++) {
            aGame.roll(i % 6 + 1);
            if (i % 9 == 7) {
                aGame.wrongAnswer();
            } else {
                aGame.wasCorrectlyAnswered();
            }
        }

        Approvals.verify(outputStream.toString());
    }

    @Test
    void characterizationTest2() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Game aGame = new Game();
        aGame.add("Scott");
        aGame.add("Barney");
        aGame.add("Fred");
        aGame.add("Wilma");
        aGame.add("Betty");

        boolean noWinner;
        int turnNumber = 0;
        do {
            aGame.roll(turnNumber % 6 + 1);
            if (turnNumber % 9 == 7) {
                noWinner = aGame.wrongAnswer();
            } else {
                noWinner = aGame.wasCorrectlyAnswered();
            }
            turnNumber++;
        } while (noWinner);

        Approvals.verify(outputStream.toString());
    }

    @Test
    void singlePlayerGameNotPlayable() {
        Game aGame = new Game();
        aGame.add("Chet");
        assertFalse(aGame.isPlayable());
    }

    @Test
    void multiPlayerGameNotPlayable() {
        Game aGame = new Game();
        aGame.add("Chet");
        aGame.add("Sue");
        assertTrue(aGame.isPlayable());
    }

    @Test
    void maxSixPlayersAllowedInGame() {
        Game aGame = new Game();
        aGame.add("Chet");
        aGame.add("Sue");
        aGame.add("Fred");
        aGame.add("Wilma");
        aGame.add("Barney");
        aGame.add("Betty");
        assertThrows(IllegalStateException.class, () -> aGame.add("Joy"));
    }


}