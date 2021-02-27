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
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

public class LoginRepo extends AsyncTask <Void, Void, Login> {

    private JSONObject request;
    ProgressBar progressBar;
    private JSONObject responseJSON;
    private MainActivity activity;
    Login l;


    public LoginRepo (boolean isStudent, String login, String password, ProgressBar progressBar, MainActivity activity) throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException {

        String role;

        this.progressBar = progressBar;
        this.activity = activity;

        if(isStudent){
            role = "student";
        }
        else {
            role = "prepod";
        }

        //Шифрование пароля

        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(password.getBytes("utf-8"));

        String s2 = new BigInteger(1, m.digest()).toString(16);
        StringBuilder sb = new StringBuilder(32);
        // дополняем нулями до 32 символов, в случае необходимости
        //System.out.println(32 - s2.length());
        for (int i = 0, count = 32 - s2.length(); i < count; i++) {
            sb.append("0");
        }
        // возвращаем MD5-хеш
        sb.append(s2).toString();

        request = new JSONObject();
        request.put("client_type", "mobile");
        request.put("command", "authorizationMobile");
        request.put("role", role);
        request.put("arg", login);
        request.put("pass", sb);



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



            //Получение ответа от API
            RepositoryAPI repositoryAPI = new RepositoryAPI();
            responseJSON = repositoryAPI.getResponse(request);


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
