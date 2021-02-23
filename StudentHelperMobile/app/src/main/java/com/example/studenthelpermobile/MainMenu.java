package com.example.studenthelpermobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.studenthelpermobile.MainActivity.isStudent;
import static com.example.studenthelpermobile.MainActivity.login;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    TextView WelcomeText;
    Button ScheduleToday;
    Button ScheduleTomorrow;
    Button ScheduleTwoWeeks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        WelcomeText = findViewById(R.id.welcome_text);
        ScheduleToday = findViewById(R.id.schedule_today);
        ScheduleToday.setOnClickListener(this);
        ScheduleTomorrow = findViewById(R.id.schedule_tomorrow);
        ScheduleTomorrow.setOnClickListener(this);
        ScheduleTwoWeeks = findViewById(R.id.schedule_two_weeks);
        ScheduleTwoWeeks.setOnClickListener(this);

        if(isStudent){
            String s = login + " " + getString(R.string.Group);
            WelcomeText.setText(s);
        }
        else {
            WelcomeText.setText(login);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.schedule_today:

                break;
            case R.id.schedule_tomorrow:

                break;
            case R.id.schedule_two_weeks:

                break;
        }
    }
}
