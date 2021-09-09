package com.example.mdt;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import kotlin.Unit;

public class Progress extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        CircularProgressBar circularProgressBar = findViewById(R.id.circularProgressBar);
// Set Progress
        circularProgressBar.setProgress(65f);
// or with animation
        circularProgressBar.setProgressWithAnimation(65f, (long) 1000); // =1s

// Set Progress Max
        circularProgressBar.setProgressMax(200f);

// Set ProgressBar Color
        circularProgressBar.setProgressBarColor(Color.BLACK);
// or with gradient
        circularProgressBar.setProgressBarColorStart(Color.GRAY);
        circularProgressBar.setProgressBarColorEnd(Color.RED);
        circularProgressBar.setProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);

// Set background ProgressBar Color
        circularProgressBar.setBackgroundProgressBarColor(Color.GRAY);
// or with gradient
        circularProgressBar.setBackgroundProgressBarColorStart(Color.WHITE);
        circularProgressBar.setBackgroundProgressBarColorEnd(Color.RED);
        circularProgressBar.setBackgroundProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);

// Set Width
        circularProgressBar.setProgressBarWidth(7f); // in DP
        circularProgressBar.setBackgroundProgressBarWidth(3f); // in DP

// Other
        circularProgressBar.setRoundBorder(true);
        circularProgressBar.setStartAngle(180f);
        circularProgressBar.setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);

        circularProgressBar.setOnIndeterminateModeChangeListener(isEnable -> {
            // Do something
            return Unit.INSTANCE;
        });

        circularProgressBar.setOnProgressChangeListener(progress -> {
            // Do something
            return Unit.INSTANCE;
        });
    }
}