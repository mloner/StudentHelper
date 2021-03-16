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
import java.util.HashMap;
import java.util.Map;

public class ChatListRepo extends AsyncSuperClass {

    private ChatListView activity;
    private ResponseClass responseClass;
    private Map<String, String> request;

    public ChatListRepo (ProgressBar progressBar, ChatListView chatListView, String role, String login) {
        super(progressBar);
        activity = chatListView;
        request = new HashMap<>();
        request.put("role", role);
        request.put("login", login);
    }

    @Override
    public ResponseClass doInBackground(Void... voids) {
        RepositoryAPI repositoryAPI = new RepositoryAPI();
        try {
            String s = "http://shipshon.fvds.ru/api/getChatList";
            s = repositoryAPI.URLBuilder(s, request);
            URL url = new URL(s);
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
