package com.example.studenthelpermobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.studenthelpermobile.Model.ApiRepository;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.studenthelpermobile.MainActivity.isStudent;
import static com.example.studenthelpermobile.MainActivity.login;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    ApiRepository apiRepository;

    TextView WelcomeText;
    Button ScheduleToday;
    Button ScheduleTomorrow;
    Button ScheduleTwoWeeks;
    Button PrepodInfo;
    Button PrepodChat;
    Button Logout;
    JSONObject schedule;

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
        PrepodInfo = findViewById(R.id.prepod_info);
        PrepodInfo.setOnClickListener(this);
        PrepodChat = findViewById(R.id.prepod_chat);
        PrepodChat.setOnClickListener(this);
        Logout = findViewById(R.id.logout);
        Logout.setOnClickListener(this);

        if(isStudent){
            String s = login + " " + getString(R.string.Group);
            WelcomeText.setText(s);
        }
        else {
            WelcomeText.setText(login);
        }

        apiRepository = new ApiRepository();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.schedule_today:
                try {
                    schedule = apiRepository.Schedule(1, login);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ScheduleUnpacking(schedule);
                break;
            case R.id.schedule_tomorrow:
                try {
                    schedule = apiRepository.Schedule(2, login);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ScheduleUnpacking(schedule);
                break;
            case R.id.schedule_two_weeks:
                try {
                    schedule = apiRepository.Schedule(14, login);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ScheduleUnpacking(schedule);
                break;
            case R.id.prepod_info:

                break;
            case R.id.prepod_chat:

                break;
            case R.id.logout:

                break;
        }
    }

    public void ScheduleUnpacking(JSONObject schedule){
        //скорее всего в отдельном классе View
    }
}
