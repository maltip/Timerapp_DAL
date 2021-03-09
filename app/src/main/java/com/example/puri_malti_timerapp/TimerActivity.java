package com.example.puri_malti_timerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TimerActivity extends AppCompatActivity {

    private Button btnReset;
    private Button btnPause;
    private TextView txtTimer;
    private CountDownTimer timer;
    private long millisInFuture, remainingMillis, minutes, seconds = 0;
    private boolean paused, stopped = false;

    @Override
    protected void onStop() {
        super.onStop();

        timer.cancel();

        paused = true;

        Toast.makeText(this, "Stop Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // app entered again, so start time where it left off
        if(!stopped) {
            startCountDownTimer();
            btnReset.setText(R.string.reset);
            btnPause.setText(R.string.pause);
            paused = false;
        }

        Toast.makeText(this, "Start called!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Toast.makeText(this, "Destroy called!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Toast.makeText(this, "Pause called!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Toast.makeText(this, "Resume called!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        btnReset = findViewById(R.id.btnReset);
        btnPause = findViewById(R.id.btnPause);
        txtTimer = findViewById(R.id.txtTimer);

        // Get the intent that started thi activity and extract the string
        Intent intent = getIntent();

        minutes = Integer.parseInt(intent.getStringExtra(MainActivity.MESSAGE_MINUTES));
        seconds = Integer.parseInt(intent.getStringExtra(MainActivity.MESSAGE_SECONDS));

        //remember to convert to milliseconds
        millisInFuture = (minutes*60000) +(seconds*1000);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paused = false;
                setTxtTimerValue(millisInFuture);

                if(!stopped) {
                    timer.cancel();
                    btnReset.setText(R.string.start);
                    btnPause.setText(R.string.pause);
                    btnPause.setEnabled(false);
                    stopped = true;
                } else {
                    startCountDownTimer();
                    btnReset.setText(R.string.reset);
                    btnPause.setEnabled(true);
                    stopped = false;
                }
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!paused) {
                    timer.cancel();
                    btnPause.setText(R.string.resume);
                    paused = true;
                } else {
                    startCountDownTimer();
                    btnPause.setText(R.string.pause);
                    paused = false;
                }
            }
        });
    }

    private void startCountDownTimer() {
        // if paused, resume from where timer left off
        long startFrom = this.paused ? remainingMillis : millisInFuture;

        timer = new CountDownTimer(startFrom, 1000) {
            @Override
            public void onTick(long millisLeft) {
                //update the remaining milliseconds to calculate where the timer left off
                remainingMillis = millisLeft;
                setTxtTimerValue(millisLeft);
            }

            @Override
            public void onFinish() {
                setTxtTimerValue(0);
                Toast.makeText(getApplicationContext(), "Finished", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    public void setTxtTimerValue(long millis) {
        // convert from milliseconds
        minutes = millis / 60000;
        seconds = (millis / 1000) % 60;

        String minutesVal = String.valueOf(minutes);
        String secondsVal = String. valueOf(seconds);

        // formatting: insert leading zero to handle seconds < 10 being only a single char
        secondsVal = secondsVal.replaceAll("(?<!\\d)\\d(?!\\d)", "0$0");

        txtTimer.setText(getString(R.string.txtTimer, minutesVal, secondsVal));
    }
}
