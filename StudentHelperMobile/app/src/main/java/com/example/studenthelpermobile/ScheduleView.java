package com.example.studenthelpermobile;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.studenthelpermobile.Model.Lesson;
import com.example.studenthelpermobile.Model.Schedule;
import com.example.studenthelpermobile.Repository.AsyncInterface;
import com.example.studenthelpermobile.Repository.ScheduleRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class ScheduleView extends AppCompatActivity implements AsyncInterface <Schedule> {

    TextView ErrorText;
    ProgressBar progressBar;
    private int type;
    private String login;
    private String role;
    ScheduleRepo scheduleRepo;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_activity);
        ErrorText = findViewById(R.id.error_text_schedule);
        progressBar = findViewById(R.id.schedule_progressbar);
        linearLayout = findViewById(R.id.schedule_layout);
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
    public void onAsyncTaskFinished(Schedule scheduleClass) { //Выполняется после получения расписания
        try {
            if(scheduleClass.getStatus().equals("OK")){
                JSONArray array = scheduleClass.getResponse();
                for(int n = 0; n < array.length(); n++){
                    JSONObject s = (JSONObject) array.get(n);
                    Lesson lesson = new Lesson();
                    lesson.setClass_name(s.get("className").toString());
                    lesson.setPrepod_name(s.get("prepodName").toString());
                    lesson.setSubject_name(s.get("subjectName").toString());
                    lesson.setLesson_type(s.get("lessonType").toString());
                    lesson.setClass_name(s.get("className").toString());
                    lesson.setLesson_start(s.get("lessonStart").toString());
                    lesson.setLesson_end(s.get("lessonEnd").toString());
                    lesson.setSubgroup(Integer.parseInt(s.get("subGroup").toString()));
                    lesson.setRemote(Boolean.parseBoolean(s.get("isRemote").toString()));
                    lesson.setGroup(s.get("groupName").toString());
                    SetLesson(lesson);
                }
            }
            else {
                ServerError();
            }
        }
        catch (NullPointerException e){
            ServerError();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        progressBar.setVisibility(View.GONE);
    }

    private void ServerError(){
        ErrorText.setText(R.string.Server_error);
        ErrorText.setVisibility(View.VISIBLE);
    }

    private void SetLesson(Lesson lesson){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,30,0,0);
        params.gravity = Gravity.CENTER_HORIZONTAL;

        TextView header = new TextView(this);
        header.setText(lesson.getSubject_name());
        header.setTextColor(Color.BLACK);
        header.setTextSize(16);
        header.setTypeface(null, Typeface.BOLD);
        header.setLayoutParams(params);
        header.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.addView(header);

        TextView time = new TextView(this);
        time.setText(lesson.getLesson_start() + "  - " + lesson.getLesson_end());
        time.setTextSize(16);
        time.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.addView(time);

        TextView classname = new TextView(this);
        classname.setText(lesson.getClass_name());
        classname.setTextSize(16);
        classname.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.addView(classname);

        TextView lessontype = new TextView(this);
        lessontype.setText(lesson.getLesson_type() + "    " + lesson.getPrepod_name());
        lessontype.setTextSize(16);
        lessontype.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.addView(lessontype);

        if(lesson.getSubgroup()!=0){
            TextView subgroup = new TextView(this);
            subgroup.setText(lesson.getSubgroup() + getString(R.string.Subgroup));
            subgroup.setTextSize(16);
            subgroup.setTypeface(null, Typeface.BOLD);
            subgroup.setGravity(Gravity.CENTER_HORIZONTAL);
            linearLayout.addView(subgroup);
        }
    }


}
