package com.example.studenthelpermobile;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.studenthelpermobile.Model.AsyncInterface;
import com.example.studenthelpermobile.Model.Schedule;
import com.example.studenthelpermobile.Model.ScheduleRepo;

import org.json.JSONException;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class ScheduleView extends AppCompatActivity implements AsyncInterface <Schedule> {

    TextView ErrorText;
    ProgressBar progressBar;
    private int type;
    private String login;
    private String role;
    ScheduleRepo scheduleRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_layout);
        ErrorText = findViewById(R.id.error_text_schedule);
        progressBar = findViewById(R.id.schedule_progressbar);
        type = Objects.requireNonNull(getIntent().getExtras()).getInt("type");
        login = Objects.requireNonNull(getIntent().getExtras()).getString("login");
        role = Objects.requireNonNull(getIntent().getExtras()).getString("role");
        try {
            scheduleRepo = new ScheduleRepo(type, login,role,progressBar, this);
            scheduleRepo.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAsyncTaskFinished(Schedule schedule ) { //Выполняется после получения расписания
        progressBar.setVisibility(View.GONE);
    }


}
