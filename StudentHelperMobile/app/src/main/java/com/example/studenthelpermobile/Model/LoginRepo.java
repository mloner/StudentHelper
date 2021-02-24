package com.example.studenthelpermobile.Model;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.studenthelpermobile.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginRepo extends AsyncTask <Void, Void, String> {

    private JSONObject request;
    ProgressBar progressBar;
    private String response;
    private MainActivity activity;

    public LoginRepo (boolean isStudent, String login, String password, ProgressBar progressBar, MainActivity activity) throws JSONException {

        String role;

        this.progressBar = progressBar;
        this.activity = activity;

        if(isStudent){
            role = "student";
        }
        else {
            role = "prepod";
        }

        request = new JSONObject();
        request.put("client_type", "mobile");
        request.put("command", "authorization");
        request.put("role", role);
        request.put("pass",password);
        request.put("arg", login);





    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            Thread.sleep(5000);
            //ToDo здесь будет запрос к API
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        activity.onAsyncTaskFinished(s);
    }
}
