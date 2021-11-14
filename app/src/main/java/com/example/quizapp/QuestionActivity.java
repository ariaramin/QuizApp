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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener, OnRestartListener {

    ImageView closeBtn;
    CircularProgressIndicator timerProgressBar;
    TextView timerCountDown, questionTV, questionIndexTV, healthTV;
    Button optionOneBtn, optionTwoBtn, optionThreeBtn, optionFourBtn;

    int health = 5;
    List<Question> questionList = new ArrayList<>();
    int questionIndex = 0;
    Question currentQuestion;
    CountDownTimer timer;
    boolean wrongAnswerSelected;

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
        healthTV = findViewById(R.id.healthTextView);
        healthTV.setText(String.valueOf(health));

        String category = getIntent().getStringExtra("category");
        getQuestions(category);
        setQuestions();
        startTimer();
    }

    private void setQuestions() {
        currentQuestion = questionList.get(questionIndex);
        int index = questionList.indexOf(currentQuestion);
        questionIndexTV.setText(String.format("%o/%o", index + 1, questionList.size()));

        AlphaAnimation fadeOut = new AlphaAnimation(1f, 0f);
        fadeOut.setDuration(200);
        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setDuration(200);

        questionTV.startAnimation(fadeOut);
        optionOneBtn.startAnimation(fadeOut);
        optionTwoBtn.startAnimation(fadeOut);
        optionThreeBtn.startAnimation(fadeOut);
        optionFourBtn.startAnimation(fadeOut);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                questionTV.setText(currentQuestion.getQuestion());
                questionTV.startAnimation(fadeIn);
                optionOneBtn.setText(currentQuestion.getOptionOne());
                optionOneBtn.startAnimation(fadeIn);
                optionTwoBtn.setText(currentQuestion.getOptionTwo());
                optionTwoBtn.startAnimation(fadeIn);
                optionThreeBtn.setText(currentQuestion.getOptionThree());
                optionThreeBtn.startAnimation(fadeIn);
                optionFourBtn.setText(currentQuestion.getOptionFour());
                optionFourBtn.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
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
                    shakingButtonAnimation(optionOneBtn, second);
                    shakingButtonAnimation(optionTwoBtn, second);
                    shakingButtonAnimation(optionThreeBtn, second);
                    shakingButtonAnimation(optionFourBtn, second);
                } else {
                    timerProgressBar.setIndicatorColor(getColor(R.color.grey_100));
                }
            }

            @SuppressLint("NewApi")
            @Override
            public void onFinish() {
                timer.cancel();
                wrongAnswerSelected = true;
                timerProgressBar.setIndicatorColor(getColor(R.color.grey_100));
                TimesUpDialog dialog = new TimesUpDialog();
                dialog.show(getSupportFragmentManager(), null);
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

    private void decreaseHealth() {
        health--;
        healthTV.setText(String.valueOf(health));
    }

    @SuppressLint("NewApi")
    private void checkAnswer(Button selectedButton) {
        selectedButton.clearAnimation();
        String selectedOption = selectedButton.getText().toString().toLowerCase(Locale.ROOT);
        if (selectedOption.equals(currentQuestion.getAnswer().toLowerCase(Locale.ROOT))) {
            selectedButton.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.green_600)));
            wrongAnswerSelected = false;
            nextQuestion();
        } else {
            selectedButton.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.red_600)));
            wrongAnswerSelected = true;
            nextQuestion();
        }
    }

    private void nextQuestion() {
        timer.cancel();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (wrongAnswerSelected)
                    decreaseHealth();
                questionIndex++;
                resetOptions();
                setQuestions();
                timer.start();
            }
        }, 1000);
    }

    @SuppressLint("NewApi")
    private void resetOptions() {
        optionOneBtn.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.grey_500)));
        optionTwoBtn.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.grey_500)));
        optionThreeBtn.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.grey_500)));
        optionFourBtn.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.grey_500)));
    }

    private void shakingButtonAnimation(Button button, long second) {
        Animation shaking = new RotateAnimation(-5,
                5,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        shaking.setDuration(100);
        shaking.setRepeatCount(Animation.INFINITE);
        shaking.setRepeatMode(Animation.REVERSE);
        shaking.setInterpolator(new LinearInterpolator());
        button.startAnimation(shaking);
        if (second == 0) {
            button.clearAnimation();
        }
    }

    @Override
    public void OnRestart(int health, int index) {
        questionIndex = index;
        this.health = health;
        healthTV.setText(String.valueOf(this.health));
        startTimer();
        setQuestions();
    }
}