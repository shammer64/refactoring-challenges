package dev.scotthammer.refactoring.uglytrivia;

import java.util.HashMap;
import java.util.Map;

public class QuestionBank {
    private final Map<Topic, Integer> indices = new HashMap<>();

    public Question pop(Topic topic) {
        int index = indices.getOrDefault(topic, 0);
        indices.put(topic, index + 1);
        return new Question(topic, index);
    }

    public static class Question {
        private final String text;

        public Question(Topic topic, int index) {
            this.text = topic + " Question " + index;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public enum Topic {
        Pop, Science, Sports, Rock
    }
}
