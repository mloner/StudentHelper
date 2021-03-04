package com.example.studenthelpermobile;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class ScheduleView extends AppCompatActivity implements View.OnClickListener, AsyncInterface <Schedule> {

    TextView ErrorText;
    ProgressBar progressBar;
    private int type;
    private String login;
    private String role;
    ScheduleRepo scheduleRepo;
    LinearLayout linearLayout;
    ArrayList <Lesson> lessonArrayList;

    Button firstSub;
    Button secondSub;
    Button bothSub;

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

        firstSub = findViewById(R.id.first_subgroup);
        firstSub.setOnClickListener(this);
        secondSub = findViewById(R.id.second_subgroup);
        secondSub.setOnClickListener(this);
        bothSub = findViewById(R.id.both_subgroup);
        bothSub.setOnClickListener(this);

        lessonArrayList = new ArrayList<>();
        scheduleRepo = new ScheduleRepo(type, login,role,progressBar, this);
        scheduleRepo.execute();
    }

    @Override
    public void onAsyncTaskFinished(Schedule scheduleClass) { //Выполняется после получения расписания
        try {
            if(scheduleClass.getStatus().equals("OK")){
                JSONArray array = scheduleClass.getResponse();
                    for(int n = 0; n < array.length(); n++) {
                        JSONObject s = (JSONObject) array.get(n);
                        SetLesson(LessonFill(s));
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
        if(role.equals("student"))
            lessontype.setText(lesson.getLesson_type() + "    " + lesson.getPrepod_name());
        else
            lessontype.setText(lesson.getLesson_type() + "    " + lesson.getGroup());
        lessontype.setTextSize(16);
        lessontype.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.addView(lessontype);

        if(lesson.getSubgroup()!=0){
            TextView subgroup = new TextView(this);
            subgroup.setText(lesson.getSubgroup() + " " + getString(R.string.SubGroup));
            subgroup.setTextSize(16);
            subgroup.setTypeface(null, Typeface.BOLD);
            subgroup.setGravity(Gravity.CENTER_HORIZONTAL);
            linearLayout.addView(subgroup);
        }

        if(!lesson.getDescription().equals("null")){
            TextView description = new TextView(this);
            if (lesson.getDescription().contains("/")){
                description.setText(getString(R.string.FirstSubgroup) + ": " + lesson.getDescription().substring(0, lesson.getDescription().indexOf('/')) + "\n" +
                        getString(R.string.SecondSubgroup) + ": " + lesson.getDescription().substring(lesson.getDescription().indexOf('/')+1));
            }
            else {
                description.setText(lesson.getDescription());
            }
            description.setTextSize(16);
            description.setTypeface(null, Typeface.BOLD);
            description.setGravity(Gravity.CENTER_HORIZONTAL);
            linearLayout.addView(description);
        }
    }

    public Lesson LessonFill(JSONObject s) throws JSONException {
        Lesson lesson = new Lesson();
        lessonArrayList.add(lesson);
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
        lesson.setDescription(s.get("description").toString());
        lesson.setWeekdayname(s.get("weekDayName").toString());
        return lesson;
    }


    @Override
    public void onClick(View view) {
        linearLayout.removeAllViews();
        switch (view.getId()){
            case R.id.first_subgroup:
                for (Lesson l: lessonArrayList
                     ) {
                    if(l.getSubgroup()==1 || l.getSubgroup() == 0){
                        SetLesson(l);
                    }
                }
                break;
            case R.id.second_subgroup:
                for (Lesson l: lessonArrayList
                ) {
                    if(l.getSubgroup()==2 || l.getSubgroup() == 0){
                        SetLesson(l);
                    }
                }
                break;
            default:
                for (Lesson l: lessonArrayList
                ) {
                    SetLesson(l);
                }
        }
    }
}
