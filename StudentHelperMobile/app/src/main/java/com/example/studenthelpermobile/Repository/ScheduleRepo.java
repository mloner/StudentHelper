package com.example.studenthelpermobile.Repository;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.studenthelpermobile.Model.Schedule;
import com.example.studenthelpermobile.ScheduleView;

import org.json.JSONException;
import org.json.JSONObject;

public class ScheduleRepo extends AsyncTask <Void, Void, Schedule>  {

    private int type;
    private ScheduleView activity;
    private ProgressBar progressBar;
    private JSONObject request;

    public ScheduleRepo(int type, String login, String role, ProgressBar progressBar, ScheduleView scheduleView)  throws JSONException {
        this.type = type;
        activity = scheduleView;
        this.progressBar = progressBar;
        request = new JSONObject();
        request.put("command","schedule_check");
        request.put("role", role);
        if(role.equals("student")){
            request.put("group", login);
        }
        else {
            request.put("fio", login);
        }
        switch (this.type){
            case 1:
                request.put("schedule_type", "today");
                break;
            case 2:
                request.put("schedule_type", "tomorrow");
                break;
            case 3:
                request.put("schedule_type", "two_weeks");
                break;
        }


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Schedule doInBackground(Void... voids) {

        //ToDO здесь будет запрос к API
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Schedule schedule) {
        super.onPostExecute(schedule);
        activity.onAsyncTaskFinished(schedule);
    }
}
