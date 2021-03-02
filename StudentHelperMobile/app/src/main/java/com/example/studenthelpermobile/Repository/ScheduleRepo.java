package com.example.studenthelpermobile.Repository;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.studenthelpermobile.Model.Login;
import com.example.studenthelpermobile.Model.Schedule;
import com.example.studenthelpermobile.ScheduleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

public class ScheduleRepo extends AsyncTask <Void, Void, Schedule>  {

    private int type;
    private ScheduleView activity;
    private ProgressBar progressBar;
    private JSONObject request;
    private JSONObject responseJSON;
    private Schedule schedule;

    public ScheduleRepo(int type, String login, String role, ProgressBar progressBar, ScheduleView scheduleView)  throws JSONException {
        this.type = type;
        activity = scheduleView;
        this.progressBar = progressBar;
        request = new JSONObject();
        request.put("client_type", "mobile");
        if(role.equals("student")){
            request.put("group", login);
            request.put("command","getScheduleStudent");
        }
        else {
            request.put("fio", login);
            request.put("command","getSchedulePrepod");
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

        try {

            RepositoryAPI repositoryAPI = new RepositoryAPI();
            responseJSON = repositoryAPI.getResponse(request);


            String status = responseJSON.get("status").toString();
            JSONArray response = (JSONArray) responseJSON.get("response");

            schedule = new Schedule();
            schedule.setStatus(status);
            schedule.setResponse(response);

        }catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return schedule;
    }

    @Override
    protected void onPostExecute(Schedule schedule) {
        super.onPostExecute(schedule);
        activity.onAsyncTaskFinished(schedule);
    }
}
