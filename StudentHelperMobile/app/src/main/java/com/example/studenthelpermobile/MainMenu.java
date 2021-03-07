package com.example.studenthelpermobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    TextView WelcomeText;
    Button ScheduleToday;
    Button ScheduleTomorrow;
    Button ScheduleTwoWeeks;
    Button PrepodInfo;
    Button PrepodChat;

    private String login;
    private String role;

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

        login = Objects.requireNonNull(getIntent().getExtras()).getString("login");
        role = Objects.requireNonNull(getIntent().getExtras()).getString("role");

        assert role != null;
        if(role.equals("student")){
            String s = getString(R.string.LoginAsStudent, login);
            WelcomeText.setText(s);
        }
        else {
            String s = getString(R.string.LoginAsPrepod, login);
            WelcomeText.setText(s);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.schedule_today:
                Intent i1 = new Intent(this, ScheduleView.class);
                i1.putExtra("type",1);
                i1.putExtra("role",role);
                i1.putExtra("login", login);
                startActivity(i1);
                break;
            case R.id.schedule_tomorrow:
                Intent i2 = new Intent(this, ScheduleView.class);
                i2.putExtra("type",2);
                i2.putExtra("role",role);
                i2.putExtra("login", login);
                startActivity(i2);
                break;
            case R.id.schedule_two_weeks:
                Intent i3 = new Intent(this, ScheduleView.class);
                i3.putExtra("type",3);
                i3.putExtra("role",role);
                i3.putExtra("login", login);
                startActivity(i3);
                break;
            case R.id.prepod_info:
                Intent prepodinfo = new Intent(this, PrepodListView.class);
                startActivity(prepodinfo);
                break;
            case R.id.prepod_chat:

                break;
        }
    }
}
