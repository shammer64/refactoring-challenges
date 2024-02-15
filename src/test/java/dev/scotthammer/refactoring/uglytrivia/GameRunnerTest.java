package dev.scotthammer.refactoring.uglytrivia;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class GameRunnerTest {

    @Test
    @Disabled("This test is non-determinate due to random number generation")
    void characterizationTest() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        GameRunner.main(new String[0]);

        Approvals.verify(outputStream.toString());
    }

}