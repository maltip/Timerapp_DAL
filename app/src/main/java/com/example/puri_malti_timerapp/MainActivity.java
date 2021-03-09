package com.example.puri_malti_timerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTimerActivity = findViewById(R.id.btnTimerActivity);
        spinnerMinutes = findViewById(R.id.spinnerMinutes);
        spinnerSeconds = findViewById(R.id.spinnerSeconds);

        ArrayAdapter<CharSequence> minuteAdapter = ArrayAdapter.createFromResource ( this,
                R.array.minutes_array, R.layout.spinner_dropdown_item);

        minuteAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinnerMinutes.setAdapter(minuteAdapter);

        ArrayAdapter<CharSequence> secondsAdapter = ArrayAdapter.createFromResource(this,
                R.array.seconds_array, R.layout.spinner_item);

        secondsAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinnerSeconds.setAdapter(secondsAdapter);


        btnTimerActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // transitions to the next activity with the minutes and seconds chosen
                Intent intent = new Intent(getApplicationContext(), TimerActivity.class);
                intent.putExtra(MESSAGE_MINUTES, spinnerMinutes.getSelectedItem().toString());
                intent.putExtra(MESSAGE_SECONDS, spinnerSeconds.getSelectedItem().toString());
                startActivity(intent);
            }
        });

    }
    //Accessed in TimerActivity.java
    public static final String MESSAGE_MINUTES = "MINUTES";
    public static final String MESSAGE_SECONDS = "SECONDS";

    private Button btnTimerActivity;
    private Spinner spinnerMinutes, spinnerSeconds;



}
