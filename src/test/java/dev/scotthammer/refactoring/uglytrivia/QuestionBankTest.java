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
         assertEquals("Pop Question 0", qBank.pop(Topic.Pop).toString());
    }
    @Test
    void shouldProvideTwoPopQuestions() {
        qBank.pop(Topic.Pop);
        assertEquals("Pop Question 1", qBank.pop(Topic.Pop).toString());
    }

    @Test
    void shouldProvideScienceQuestion() {
        assertEquals("Science Question 0", qBank.pop(Topic.Science).toString());
    }
    @Test
    void shouldProvideTwoScienceQuestions() {
        qBank.pop(Topic.Science);
        assertEquals("Science Question 1", qBank.pop(Topic.Science).toString());
    }

    @Test
    void shouldProvideRockQuestion() {
        assertEquals("Rock Question 0", qBank.pop(Topic.Rock).toString());
    }
    @Test
    void shouldProvideTwoRockQuestions() {
        qBank.pop(Topic.Rock);
        assertEquals("Rock Question 1", qBank.pop(Topic.Rock).toString());
    }

    @Test
    void shouldProvideSportsQuestion() {
        assertEquals("Sports Question 0", qBank.pop(Topic.Sports).toString());
    }
    @Test
    void shouldProvideTwoSportsQuestions() {
        qBank.pop(Topic.Sports);
        assertEquals("Sports Question 1", qBank.pop(Topic.Sports).toString());
    }

    @Test
    void shouldHaveDifferentCounterPerQuestionType() {
        assertEquals("Pop Question 0", qBank.pop(Topic.Pop).toString());
        assertEquals("Science Question 0", qBank.pop(Topic.Science).toString());
    }

}