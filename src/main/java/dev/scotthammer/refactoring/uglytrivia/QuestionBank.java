package dev.scotthammer.refactoring.uglytrivia;

import java.util.HashMap;
import java.util.Map;

public class QuestionBank {
    private final Map<QuestionType, Integer> indices = new HashMap<>();

    public Question pop(QuestionType questionType) {
        int index = indices.getOrDefault(questionType, 0);
        indices.put(questionType, index + 1);
        return new Question(questionType, index);
    }

    public static class Question {
        private final String text;

        public Question(QuestionType questionType, int index) {
            this.text = questionType + " Question " + index;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public enum QuestionType {
        Pop, Science, Sports, Rock
    }
}
