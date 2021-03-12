package com.example.studenthelpermobile.Repository;

import android.widget.ProgressBar;

import com.example.studenthelpermobile.Model.ResponseClass;
import com.example.studenthelpermobile.ScheduleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ScheduleRepo extends AsyncSuperClass  {


    private ScheduleView activity;
    private Map<String, String> request;
    private ResponseClass responseClass;
    private String role;

    public ScheduleRepo(int type, String login, String role, ProgressBar progressBar, ScheduleView scheduleView){
        super(progressBar);

        activity = scheduleView;
        request = new HashMap<>();
        this.role = role;
        request.put("client_type", "mobile");
        if(role.equals("student")){
            request.put("group", login);
        }
        else {
            request.put("fio", login);
        }
        switch (type){
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
    protected ResponseClass doInBackground(Void... voids) {

        try {
            RepositoryAPI repositoryAPI = new RepositoryAPI();
            String s;
            if(role.equals("student"))
                s = "http://shipshon.fvds.ru/api/getScheduleStudent";
            else
                s = "http://shipshon.fvds.ru/api/getSchedulePrepod";
            s = repositoryAPI.URLBuilder(s, request);
            URL url = new URL(s);
            JSONObject responseJSON = repositoryAPI.getRequest(url);

            String status = responseJSON.get("status").toString();
            JSONArray response = (JSONArray) responseJSON.get("response");

            responseClass = new ResponseClass();
            responseClass.setStatus(status);
            responseClass.setResponseArray(response);

        }catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return responseClass;
    }

    @Override
    protected void onPostExecute(ResponseClass responseClass) {
        super.onPostExecute(responseClass);
        activity.onAsyncTaskFinished(responseClass);
    }
}
