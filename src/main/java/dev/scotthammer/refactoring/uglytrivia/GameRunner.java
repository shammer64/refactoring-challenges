package dev.scotthammer.refactoring.uglytrivia;
/*
    Credit to JB Rainsberger for the code provided at https://github.com/jbrains/trivia
    License: GPLv3 https://github.com/jbrains/trivia/blob/master/LICENSE.txt
 */
import java.util.Random;

public class GameRunner {

    private static boolean notAWinner;

    public static void main(String[] args) {
        Game aGame = new Game();

        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");

        Random rand1 = new Random();
        Random rand2 = new Random();

        do {

            aGame.roll(rand1.nextInt(5) + 1);

            int answer = rand2.nextInt(9);
            if (answer == 7) {
                notAWinner = aGame.wrongAnswer();
            } else {
                notAWinner = aGame.wasCorrectlyAnswered();
            }



        } while (notAWinner);

    }
}