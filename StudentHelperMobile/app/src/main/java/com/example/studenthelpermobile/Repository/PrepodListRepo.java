package com.example.studenthelpermobile.Repository;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.studenthelpermobile.Model.ResponseClass;
import com.example.studenthelpermobile.PrepodListView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.IOException;
import java.net.URL;

public class PrepodListRepo extends AsyncSuperClass {

    private PrepodListView activity;
    private ResponseClass responseClass;

    public PrepodListRepo (ProgressBar progressBar, PrepodListView prepodListView) {
        super(progressBar);
        activity = prepodListView;
    }


    @Override
    protected ResponseClass doInBackground(Void... voids) {
        RepositoryAPI repositoryAPI = new RepositoryAPI();
        try {
            URL url = new URL("http://shipshon.fvds.ru/api/getPrepodList");
            JSONObject responseJSON = repositoryAPI.getRequest(url);

            String status = responseJSON.get("status").toString();
            JSONArray response = (JSONArray) responseJSON.get("response");

            responseClass = new ResponseClass();
            responseClass.setStatus(status);
            responseClass.setResponseArray(response);


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
