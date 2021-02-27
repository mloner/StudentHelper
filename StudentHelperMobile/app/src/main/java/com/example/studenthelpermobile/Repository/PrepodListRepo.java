package com.example.studenthelpermobile.Repository;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.studenthelpermobile.Model.Login;
import com.example.studenthelpermobile.Model.PrepodList;
import com.example.studenthelpermobile.Model.Schedule;
import com.example.studenthelpermobile.PrepodListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class PrepodListRepo extends AsyncTask<Void, Void, PrepodList> {

    private PrepodListView activity;
    private ProgressBar progressBar;
    private JSONObject request;
    private JSONObject responseJSON;
    private PrepodList prepodList;

    public PrepodListRepo (ProgressBar progressBar, PrepodListView prepodListView) throws JSONException {
        activity = prepodListView;
        this.progressBar = progressBar;
        request = new JSONObject();
        request.put("command","schedule_check");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected PrepodList doInBackground(Void... voids) {
        RepositoryAPI repositoryAPI = new RepositoryAPI();
        try {
            responseJSON = repositoryAPI.getResponse(request);

            String status = responseJSON.get("status").toString();
            //ToDO получить сам лист

            prepodList = new PrepodList();
            prepodList.setStatus(status);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(PrepodList prepodList) {
        super.onPostExecute(prepodList);
        activity.onAsyncTaskFinished(prepodList);
    }
}
