package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView closeBtn;
    CircularProgressIndicator timerProgressBar;
    TextView timerCountDown, questionTV, questionIndexTV;
    Button optionOneBtn, optionTwoBtn, optionThreeBtn, optionFourBtn;

    List<Question> questionList = new ArrayList<>();
    int questionIndex = 0;
    Question currentQuestion;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        closeBtn = findViewById(R.id.closeImageView);
        closeBtn.setOnClickListener(this);
        timerProgressBar = findViewById(R.id.timerProgressBar);
        timerCountDown = findViewById(R.id.timerTextView);
        questionTV = findViewById(R.id.questionTextView);
        questionIndexTV = findViewById(R.id.questionIndexTextView);
        optionOneBtn = findViewById(R.id.optionOneButton);
        optionOneBtn.setOnClickListener(this);
        optionTwoBtn = findViewById(R.id.optionTwoButton);
        optionTwoBtn.setOnClickListener(this);
        optionThreeBtn = findViewById(R.id.optionThreeButton);
        optionThreeBtn.setOnClickListener(this);
        optionFourBtn = findViewById(R.id.optionFourButton);
        optionFourBtn.setOnClickListener(this);

        String category = getIntent().getStringExtra("category");
        getQuestions(category);
        setQuestions();
        startTimer();
    }

    private void setQuestions() {
        currentQuestion = questionList.get(questionIndex);
        int index = questionList.indexOf(currentQuestion);
        questionTV.setText(currentQuestion.getQuestion());
        questionIndexTV.setText(String.format("%o/%o", index + 1, questionList.size()));
        optionOneBtn.setText(currentQuestion.getOptionOne());
        optionTwoBtn.setText(currentQuestion.getOptionTwo());
        optionThreeBtn.setText(currentQuestion.getOptionThree());
        optionFourBtn.setText(currentQuestion.getOptionFour());
    }

    private void getQuestions(String category) {
        List<Question> data = new ArrayList<>();
        data.add(new Question(1, "food and drink", "Which food has cholestrol?", "pizza", "burger", "hot dog", "chicken", "chicken"));
        data.add(new Question(2, "food and drink", "Which food has not cholestrol?", "pizza", "burger", "hot dog", "chicken", "chicken"));
        data.add(new Question(3, "athletic", "Where does ronaldo play?", "man city", "man unt", "real madrid", "juventos", "man unt"));
        data.add(new Question(4, "athletic", "Where does messi play?", "man city", "man unt", "real madrid", "psg", "psg"));
        data.add(new Question(4, "athletic", "Where does messi play?", "man city", "man unt", "real madrid", "psg", "psg"));
        data.add(new Question(4, "athletic", "Where does messi play?", "man city", "man unt", "real madrid", "psg", "psg"));
        data.add(new Question(4, "athletic", "Where does messi play?", "man city", "man unt", "real madrid", "psg", "psg"));
        data.add(new Question(4, "athletic", "Where does messi play?", "man city", "man unt", "real madrid", "psg", "psg"));

        for (Question question :
                data) {
            if (question.getCategory().toLowerCase(Locale.ROOT).equals(category.toLowerCase(Locale.ROOT))) {
                questionList.add(question);
            }
        }

        Log.i("Que", questionList + "");
    }

    private void startTimer() {
        timer = new CountDownTimer(30000, 1000) {
            @SuppressLint("NewApi")
            @Override
            public void onTick(long millisUntilFinished) {
                long second = millisUntilFinished / 1000;
                timerCountDown.setText(String.valueOf(second));
                timerProgressBar.setProgress((int) second);
                if (second <= 10) {
                    timerProgressBar.setIndicatorColor(getColor(R.color.red_600));
                }
            }

            @SuppressLint("NewApi")
            @Override
            public void onFinish() {
                nextQuestion();
                timerProgressBar.setIndicatorColor(getColor(R.color.grey_100));
                Toast.makeText(getApplicationContext(), "Times Up!", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        if (timer != null)
            timer.cancel();
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.closeImageView:
                if (timer != null)
                    timer.cancel();
                finish();
                break;
            case R.id.optionOneButton:
                checkAnswer(optionOneBtn);
                break;
            case R.id.optionTwoButton:
                checkAnswer(optionTwoBtn);
                break;
            case R.id.optionThreeButton:
                checkAnswer(optionThreeBtn);
                break;
            case R.id.optionFourButton:
                checkAnswer(optionFourBtn);
                break;
        }
    }

    @SuppressLint("NewApi")
    private void checkAnswer(Button selectedButton) {
        String selectedOption = selectedButton.getText().toString().toLowerCase(Locale.ROOT);
        Log.d("Btn", selectedOption);
        if (selectedOption.equals(currentQuestion.getAnswer().toLowerCase(Locale.ROOT))) {
            selectedButton.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.green_600)));
            nextQuestion();
        } else {
            selectedButton.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.red_600)));
            nextQuestion();
        }
    }

    private void nextQuestion() {
        timer.cancel();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                questionIndex++;
                resetOptions();
                setQuestions();
                timer.start();
            }
        }, 2000);
    }

    @SuppressLint("NewApi")
    private void resetOptions() {
        optionOneBtn.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.grey_500)));
        optionTwoBtn.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.grey_500)));
        optionThreeBtn.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.grey_500)));
        optionFourBtn.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.grey_500)));
    }
}