package com.example.studenthelpermobile.Repository;

import android.widget.ProgressBar;

import com.example.studenthelpermobile.ChatListView;
import com.example.studenthelpermobile.ChatView;
import com.example.studenthelpermobile.Model.ResponseClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ChatRepo extends AsyncSuperClass {

    private ChatView activity;
    private ResponseClass responseClass;
    private Map<String, String> request;
    private JSONObject requestJSON;
    private boolean isGetRequest;

    public ChatRepo(ProgressBar progressBar, ChatView chatView, String group, String prepodName, String lessonName, boolean isGet, String text) throws JSONException {
        super(progressBar);
        activity = chatView;
        request = new HashMap<>();
        isGetRequest = isGet;
        requestJSON = new JSONObject();
        if(isGet) {
            request.put("group", group);
            request.put("prepod", prepodName);
            request.put("lessonName", lessonName);
        }
        else {
            requestJSON.put("text", text);
            requestJSON.put("group", group);
            requestJSON.put("prepod", prepodName);
            requestJSON.put("lessonName", lessonName);
        }
    }

    @Override
    public ResponseClass doInBackground(Void... voids) {
        RepositoryAPI repositoryAPI = new RepositoryAPI();
        try {
            JSONObject responseJSON;
            if(isGetRequest) {
                String s = "http://shipshon.fvds.ru/api/getChat";
                s = repositoryAPI.URLBuilder(s, request);
                URL url = new URL(s);
                responseJSON = repositoryAPI.getRequest(url);
            }
            else {
                String s = "http://shipshon.fvds.ru/api/sendMessage";
                URL url = new URL(s);
                responseJSON = repositoryAPI.postResponse(requestJSON, url);
            }

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
