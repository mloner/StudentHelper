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
import com.example.studenthelpermobile.Model.ResponseClass;
import com.example.studenthelpermobile.Repository.AsyncInterface;
import com.example.studenthelpermobile.Repository.ScheduleRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class ScheduleView extends AppCompatActivity implements View.OnClickListener, AsyncInterface <ResponseClass> {

    private TextView ErrorText;
    private ProgressBar progressBar;
    private int type;
    private String role;
    private LinearLayout linearLayout;
    private ArrayList <Lesson> lessonArrayList;

    private Button firstSub;
    private Button secondSub;
    private Button bothSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_activity);
        ErrorText = findViewById(R.id.error_text_schedule);
        progressBar = findViewById(R.id.schedule_progressbar);
        linearLayout = findViewById(R.id.schedule_layout);
        type = Objects.requireNonNull(getIntent().getExtras()).getInt("type");
        String login = Objects.requireNonNull(getIntent().getExtras()).getString("login");
        role = Objects.requireNonNull(getIntent().getExtras()).getString("role");

        firstSub = findViewById(R.id.first_subgroup);
        firstSub.setOnClickListener(this);
        secondSub = findViewById(R.id.second_subgroup);
        secondSub.setOnClickListener(this);
        bothSub = findViewById(R.id.both_subgroup);
        bothSub.setOnClickListener(this);

        lessonArrayList = new ArrayList<>();
        ScheduleRepo scheduleRepo = new ScheduleRepo(type, login, role, progressBar, this);
        scheduleRepo.execute();
    }

    @Override
    public void onAsyncTaskFinished(ResponseClass responseClass) { //Выполняется после получения расписания
        try {
            if(responseClass.getStatus().equals("OK")){
                if(type != 3) {
                    JSONArray array = responseClass.getResponseArray();
                    for (int n = 0; n < array.length(); n++) {
                        JSONObject s = (JSONObject) array.get(n);
                        SetLesson(LessonFill(s));
                    }
                    bothSub.setVisibility(View.VISIBLE);
                    firstSub.setVisibility(View.VISIBLE);
                    secondSub.setVisibility(View.VISIBLE);
                }
                else {
                    JSONArray array = responseClass.getResponseArray();
                    JSONArray fweek = (JSONArray) array.get(0);
                    JSONArray sweek = (JSONArray) array.get(1);
                    SetNumberOfWeek(1);
                    for (int n = 0; n < fweek.length(); n++) {
                        JSONArray dayArr = (JSONArray) fweek.get(n);
                        for(int i = 0; i < dayArr.length(); i++) {
                            JSONObject day = (JSONObject) dayArr.get(i);
                            SetLesson(LessonFill(day));
                        }
                        if(!lessonArrayList.isEmpty())
                            SetSpacers();
                        lessonArrayList.clear();

                    }
                    SetNumberOfWeek(2);
                    for (int n = 0; n < sweek.length(); n++) {
                        JSONArray dayArr = (JSONArray) sweek.get(n);
                        for(int i = 0; i < dayArr.length(); i++) {
                            JSONObject day = (JSONObject) dayArr.get(i);
                            SetLesson(LessonFill(day));
                        }
                        if(!lessonArrayList.isEmpty())
                            SetSpacers();
                        lessonArrayList.clear();
                    }
                    bothSub.setVisibility(View.GONE);
                    firstSub.setVisibility(View.GONE);
                    secondSub.setVisibility(View.GONE);
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
        params.setMargins(30,20,0,0);
        //params.gravity = Gravity.CENTER_HORIZONTAL;

        TextView header = new TextView(this);
        header.setText(lesson.getSubject_name());
        header.setTextColor(Color.BLACK);
        header.setTextSize(16);
        header.setTypeface(null, Typeface.BOLD);
        header.setLayoutParams(params);
        //header.setGravity(Gravity.CENTER_HORIZONTAL);

        linearLayout.addView(header);

        TextView time = new TextView(this);
        time.setText(String.format("%s  - %s", lesson.getLesson_start(), lesson.getLesson_end()));
        time.setTextSize(16);
        //time.setGravity(Gravity.CENTER_HORIZONTAL);
        time.setLayoutParams(params);
        linearLayout.addView(time);

        TextView classname = new TextView(this);
        classname.setText(lesson.getClass_name());
        classname.setTextSize(16);
        classname.setLayoutParams(params);
        //classname.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.addView(classname);

        TextView lessontype = new TextView(this);
        if(role.equals("student"))
            lessontype.setText(String.format("%s    %s", lesson.getLesson_type(), lesson.getPrepod_name()));
        else
            lessontype.setText(String.format("%s    %s", lesson.getLesson_type(), lesson.getGroup()));
        lessontype.setTextSize(16);
        lessontype.setLayoutParams(params);
        //lessontype.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.addView(lessontype);

        if(lesson.getSubgroup()!=0){
            TextView subgroup = new TextView(this);
            subgroup.setText(getString(R.string.SubGroup, lesson.getSubgroup()));
            subgroup.setTextSize(16);
            subgroup.setTypeface(null, Typeface.BOLD);
            subgroup.setLayoutParams(params);
            //subgroup.setGravity(Gravity.CENTER_HORIZONTAL);
            linearLayout.addView(subgroup);
        }

        if(!lesson.getDescription().equals("null")){
            TextView description = new TextView(this);
            if (lesson.getDescription().contains("/")){
                description.setText(String.format("%s: %s\n%s: %s", getString(R.string.FirstSubgroup), lesson.getDescription().substring(0, lesson.getDescription().indexOf('/')),
                        getString(R.string.SecondSubgroup), lesson.getDescription().substring(lesson.getDescription().indexOf('/') + 1)));
            }
            else {
                description.setText(lesson.getDescription());
            }
            description.setTextSize(16);
            description.setTypeface(null, Typeface.BOLD);
            description.setLayoutParams(params);
            //description.setGravity(Gravity.CENTER_HORIZONTAL);
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
        if(lessonArrayList.size()==1){
            SetDayOfWeek(lessonArrayList.get(0));
        }
        return lesson;
    }

    public void SetDayOfWeek(Lesson lesson){
        TextView day = new TextView(this);
        String word = lesson.getWeekdayname();
        day.setText(String.format("%s%s", word.substring(0, 1).toUpperCase(), word.substring(1)));
        day.setTextColor(Color.BLACK);
        day.setTextSize(18);
        day.setTypeface(null, Typeface.BOLD);
        day.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.addView(day);
    }

    public void SetSpacers(){
        TextView space = new TextView(this);
        space.setText("\n\n");
        linearLayout.addView(space);
    }

    public void SetNumberOfWeek(int n){
        TextView number = new TextView(this);
        number.setText(getString(R.string.NumberOfWeek,n));
        number.setTextColor(Color.BLACK);
        number.setTextSize(20);
        number.setTypeface(null, Typeface.BOLD);
        number.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.addView(number);
    }


    @Override
    public void onClick(View view) {
        linearLayout.removeAllViews();
        if(lessonArrayList.size()!=0){
            SetDayOfWeek(lessonArrayList.get(0));
        }
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
                break;
        }
    }
}
