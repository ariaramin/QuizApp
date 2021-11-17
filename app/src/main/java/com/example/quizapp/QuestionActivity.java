package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Parcelable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener, OnRestartListener {

    ImageView closeBtn;
    CircularProgressIndicator timerProgressBar;
    TextView timerCountDown, questionTV, questionIndexTV, healthTV;
    Button optionOneBtn, optionTwoBtn, optionThreeBtn, optionFourBtn;

    ProgressDialog progressDialog;
    int health = 5;
    List<Question> questionList = new ArrayList<>();
    int questionIndex = 0;
    Question currentQuestion;
    CountDownTimer timer;
    boolean wrongAnswerSelected;
    private static final int TIME_OVER_ID = 1;
    private static final int GAME_OVER_ID = 2;
    private static final int VICTORY_ID = 3;

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

        int categoryId = getIntent().getIntExtra("categoryId", 0);
        String categoryTitle = getIntent().getStringExtra("category");
        getQuestions(categoryId, categoryTitle);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    private void getQuestions(int categoryId, String category) {

        APIEndpoint request = APIClient.getAPIClient().create(APIEndpoint.class);
        if (categoryId != 0) {
            request.getQuestions(categoryId, 15).enqueue(new Callback<APIResponse>() {
                @Override
                public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        progressDialog.dismiss();
                        questionList = response.body().getResults();
                        setQuestions();
                        startTimer();
                    } else {
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<APIResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setQuestions() {
        currentQuestion = questionList.get(questionIndex);
        List<String> options = new ArrayList<>(currentQuestion.getOptions());
        Log.i("O", options+"");
        questionIndexTV.setText(String.format("%d/%d", questionIndex + 1, questionList.size()));

        AlphaAnimation fadeOut = new AlphaAnimation(1f, 0f);
        fadeOut.setDuration(200);
        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setDuration(200);

        questionTV.startAnimation(fadeOut);
        optionOneBtn.startAnimation(fadeOut);
        optionOneBtn.setVisibility(View.INVISIBLE);
        optionTwoBtn.startAnimation(fadeOut);
        optionTwoBtn.setVisibility(View.INVISIBLE);
        optionThreeBtn.startAnimation(fadeOut);
        optionFourBtn.startAnimation(fadeOut);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                questionTV.setText(Html.fromHtml(currentQuestion.getQuestion()).toString());
                questionTV.startAnimation(fadeIn);
                optionFourBtn.setText(Html.fromHtml(options.get(0)).toString());
                optionFourBtn.startAnimation(fadeIn);
                optionThreeBtn.setText(Html.fromHtml(options.get(1)).toString());
                optionThreeBtn.startAnimation(fadeIn);
                if (options.size() >= 3) {
                    optionTwoBtn.setVisibility(View.VISIBLE);
                    optionTwoBtn.setText(Html.fromHtml(options.get(2)).toString());
                    optionTwoBtn.startAnimation(fadeIn);
                    if (options.size() >= 4) {
                        optionOneBtn.setVisibility(View.VISIBLE);
                        optionOneBtn.setText(Html.fromHtml(options.get(3)).toString());
                        optionOneBtn.startAnimation(fadeIn);
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
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
                } else {
                    timerProgressBar.setIndicatorColor(getColor(R.color.grey_100));
                }
            }

            @SuppressLint("NewApi")
            @Override
            public void onFinish() {
                cancelTimer();
                wrongAnswerSelected = true;
                timerProgressBar.setIndicatorColor(getColor(R.color.grey_100));
                showAppDialog(TIME_OVER_ID);
            }
        };
        timer.start();
    }

    private void cancelTimer() {
        if (timer != null)
            timer.cancel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.closeImageView:
                cancelTimer();
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
        selectedButton.clearAnimation();
        optionOneBtn.setOnClickListener(null);
        optionTwoBtn.setOnClickListener(null);
        optionThreeBtn.setOnClickListener(null);
        optionFourBtn.setOnClickListener(null);
        String selectedOption = selectedButton.getText().toString().toLowerCase(Locale.ROOT);
        if (selectedOption.equals(currentQuestion.getCorrectAnswer().toLowerCase(Locale.ROOT))) {
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
        cancelTimer();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (wrongAnswerSelected)
                    decreaseHealth();
                if (questionIndex < questionList.size() - 1 && health > 0) {
                    questionIndex++;
                    resetOptions();
                    setQuestions();
                    timer.start();
                } else if (questionIndex == questionList.size() - 1 && health > 0) {
                    showAppDialog(VICTORY_ID);
                } else if (questionIndex == questionList.size() - 1 && health <= 0) {
                    showAppDialog(GAME_OVER_ID);
                }
            }
        }, 1000);
    }

    @SuppressLint("NewApi")
    private void resetOptions() {
        optionOneBtn.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.grey_500)));
        optionOneBtn.setOnClickListener(this::onClick);

        optionTwoBtn.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.grey_500)));
        optionTwoBtn.setOnClickListener(this::onClick);

        optionThreeBtn.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.grey_500)));
        optionThreeBtn.setOnClickListener(this::onClick);

        optionFourBtn.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.grey_500)));
        optionFourBtn.setOnClickListener(this::onClick);
    }

    private void decreaseHealth() {
        health--;
        healthTV.setText(String.valueOf(health));
        if (health <= 0) {
            cancelTimer();
            showAppDialog(GAME_OVER_ID);
        }
    }

    public void showAppDialog(int status) {
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        AppDialog dialog = new AppDialog();
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), null);
    }

    @Override
    public void OnRestart(int health, int index) {
        questionIndex = index;
        this.health = health;
        healthTV.setText(String.valueOf(this.health));
        Collections.shuffle(questionList);
        Set<Question> questionSet = new HashSet<Question>(questionList);
        questionList = new ArrayList<>(questionSet);
        startTimer();
        setQuestions();
        resetOptions();
    }

    @Override
    public void onBackPressed() {
        cancelTimer();
        super.onBackPressed();
    }
}