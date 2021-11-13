package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;

public class QuestionActivity extends AppCompatActivity {

    CircularProgressIndicator timerProgressBar;
    TextView timerCountDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        timerProgressBar = findViewById(R.id.timerProgressBar);
        timerCountDown = findViewById(R.id.timerTextView);

        startTimer();
    }

    private void startTimer() {
        new CountDownTimer(30000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                long second = millisUntilFinished / 1000;
                timerCountDown.setText(String.valueOf(second));
                timerProgressBar.setProgress((int) second);
                if (second <= 10) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        timerProgressBar.setIndicatorColor(getColor(R.color.pink_500));
                    } else {
                        timerProgressBar.setIndicatorColor(Integer.parseInt("#E91E63"));
                    }
                }
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Times Up!", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }
}