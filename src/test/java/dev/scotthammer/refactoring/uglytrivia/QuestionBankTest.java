package dev.scotthammer.refactoring.uglytrivia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static dev.scotthammer.refactoring.uglytrivia.QuestionBank.*;
import static org.junit.jupiter.api.Assertions.*;

class QuestionBankTest {

    private QuestionBank qBank;

    @BeforeEach
    void setUp() {
        qBank = new QuestionBank();
    }

    @Test
    void shouldProvidePopQuestion() {
         assertEquals("Pop Question 0", qBank.pop(QuestionType.Pop).toString());
    }
    @Test
    void shouldProvideTwoPopQuestions() {
        qBank.pop(QuestionType.Pop);
        assertEquals("Pop Question 1", qBank.pop(QuestionType.Pop).toString());
    }

    @Test
    void shouldProvideScienceQuestion() {
        assertEquals("Science Question 0", qBank.pop(QuestionType.Science).toString());
    }
    @Test
    void shouldProvideTwoScienceQuestions() {
        qBank.pop(QuestionType.Science);
        assertEquals("Science Question 1", qBank.pop(QuestionType.Science).toString());
    }

    @Test
    void shouldHaveDifferentCounterPerQuestionType() {
        assertEquals("Pop Question 0", qBank.pop(QuestionType.Pop).toString());
        assertEquals("Science Question 0", qBank.pop(QuestionType.Science).toString());
    }

}