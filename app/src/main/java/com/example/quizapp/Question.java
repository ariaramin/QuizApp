package com.example.quizapp;

public class Question {

    long id;
    String category;
    String question;
    String optionOne;
    String optionTwo;
    String optionThree;
    String optionFour;
    String answer;

    public Question(long id, String category, String question, String optionOne, String optionTwo, String optionThree, String optionFour, String answer) {
        this.id = id;
        this.category = category;
        this.question = question;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.optionThree = optionThree;
        this.optionFour = optionFour;
        this.answer = answer;
    }

    public long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getQuestion() {
        return question;
    }

    public String getOptionOne() {
        return optionOne;
    }

    public String getOptionTwo() {
        return optionTwo;
    }

    public String getOptionThree() {
        return optionThree;
    }

    public String getOptionFour() {
        return optionFour;
    }

    public String getAnswer() {
        return answer;
    }
}
