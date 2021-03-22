package com.example.trivia.model;

public class Question {
    private String questionText;
    private boolean answer;

    public Question() {
    }

    public Question(String questionText, boolean answerTrue) {
        this.questionText = questionText;
        this.answer = answerTrue;
    }

    public String getQuestionText() {
        return questionText;
    }

    public boolean getAnswer() {
        return answer;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionText='" + questionText + '\'' +
                ", answer=" + answer +
                '}';
    }
}
