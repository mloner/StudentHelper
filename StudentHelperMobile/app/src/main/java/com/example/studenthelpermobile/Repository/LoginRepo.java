package com.example.studenthelpermobile.Repository;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.studenthelpermobile.MainActivity;
import com.example.studenthelpermobile.Model.ResponseClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LoginRepo extends AsyncTask <Void, Void, ResponseClass> {

    private Map <String, String> request;
    private ProgressBar progressBar;
    private MainActivity activity;
    private ResponseClass responseClass;


    public LoginRepo (String role, String login, String password, ProgressBar progressBar, MainActivity activity) throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException {
        this.progressBar = progressBar;
        this.activity = activity;
        //Шифрование пароля

       String encryptedPass = PasswordEncrypt(password);

        request = new HashMap<>();
        request.put("client_type", "mobile");
        request.put("role", role);
        request.put("arg", login);
        request.put("pass", encryptedPass);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected ResponseClass doInBackground(Void... voids) {
        try {

            RepositoryAPI repositoryAPI = new RepositoryAPI();
            String s = "http://shipshon.fvds.ru/api/authorizationMobile";
            s = repositoryAPI.URLBuilder(s, request);
            URL url = new URL(s);
            JSONObject responseJSON = repositoryAPI.getRequest(url);


            String status = responseJSON.get("status").toString();
            String response = responseJSON.get("response").toString();

            responseClass = new ResponseClass();
            responseClass.setStatus(status);
            responseClass.setResponseString(response);


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

    private String PasswordEncrypt(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(password.getBytes(StandardCharsets.UTF_8));

        String s2 = new BigInteger(1, m.digest()).toString(16);
        StringBuilder sb = new StringBuilder(32);
        // дополняем нулями до 32 символов, в случае необходимости
        //System.out.println(32 - s2.length());
        for (int i = 0, count = 32 - s2.length(); i < count; i++) {
            sb.append("0");
        }
        // возвращаем MD5-хеш
        sb.append(s2);
        return sb.toString();
    }

    @Override
    protected void onPostExecute(ResponseClass responseClass) {
        super.onPostExecute(responseClass);
        activity.onAsyncTaskFinished(responseClass);
    }
}
