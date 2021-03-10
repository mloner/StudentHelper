package com.example.studenthelpermobile.Repository;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.studenthelpermobile.ChatListView;
import com.example.studenthelpermobile.Model.ResponseClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class ChatListRepo extends AsyncTask<Void, Void, ResponseClass> {

    private ChatListView activity;
    private ProgressBar progressBar;
    private ResponseClass responseClass;

    public ChatListRepo (ProgressBar progressBar, ChatListView chatListView) throws JSONException {
        activity = chatListView;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    protected ResponseClass doInBackground(Void... voids) {
        RepositoryAPI repositoryAPI = new RepositoryAPI();
        try {
            URL url = new URL("http://shipshon.fvds.ru/api/getChatList");
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
