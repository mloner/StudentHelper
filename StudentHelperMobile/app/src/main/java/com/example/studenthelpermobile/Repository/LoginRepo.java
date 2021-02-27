package com.example.studenthelpermobile.Repository;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.studenthelpermobile.MainActivity;
import com.example.studenthelpermobile.Model.Login;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;

public class LoginRepo extends AsyncTask <Void, Void, Login> {

    private JSONObject request;
    ProgressBar progressBar;
    private JSONObject responseJSON;
    private MainActivity activity;
    Login l;


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
    protected Login doInBackground(Void... voids) {
        try {
            //ToDO здесь будет запрос к API

            URL url = new URL("http://shipshon.fvds.ru/api");

            //Получение ответа от API
            RepositoryAPI repositoryAPI = new RepositoryAPI();
            responseJSON = repositoryAPI.getResponse(url);


            String status = responseJSON.get("status").toString();
            String response = responseJSON.get("response").toString();

            l = new Login();
            l.setStatus(status);
            l.setResponse(response);


        }catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return l;
    }

    @Override
    protected void onPostExecute(Login l) {
        super.onPostExecute(l);
        activity.onAsyncTaskFinished(l);
    }
}
