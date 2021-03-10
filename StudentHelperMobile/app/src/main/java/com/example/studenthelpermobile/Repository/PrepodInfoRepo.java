package com.example.studenthelpermobile.Repository;


import android.widget.ProgressBar;

import com.example.studenthelpermobile.Model.PrepodInfo;
import com.example.studenthelpermobile.Model.ResponseClass;
import com.example.studenthelpermobile.PrepodInfoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;


public class PrepodInfoRepo extends AsyncSuperClass {

    private PrepodInfoView activity;
    private String fio;
    private ResponseClass responseClass;


    public PrepodInfoRepo (ProgressBar progressBar, PrepodInfoView prepodInfoView, String name){
        super(progressBar);
        activity = prepodInfoView;
        fio = name;
    }

    @Override
    protected ResponseClass doInBackground(Void... voids) {
        RepositoryAPI repositoryAPI = new RepositoryAPI();
        try {
            URL url = new URL("http://shipshon.fvds.ru/api/getPrepodInfo" + "?fio=" + fio);
            JSONObject responseJSON = repositoryAPI.getRequest(url);

            String status = responseJSON.get("status").toString();
            JSONObject r = (JSONObject) responseJSON.get("response");

            responseClass = new ResponseClass();
            responseClass.setResponseObject(r);
            responseClass.setStatus(status);

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
