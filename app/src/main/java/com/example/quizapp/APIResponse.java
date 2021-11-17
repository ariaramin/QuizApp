package com.example.quizapp;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class APIResponse {

    List<Question> results;

    public List<Question> getResults() {
        return results;
    }
}

class Question {

    String category;
    String question;
    @SerializedName("correct_answer")
    String correctAnswer;
    @SerializedName("incorrect_answers")
    List<String> options;

    public String getCategory() {
        return category;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public Set<String> getOptions() {
        options.add(correctAnswer);
        Collections.shuffle(options);
        Set<String> optionsSet = new HashSet<String>(options);
        return optionsSet;
    }
}
