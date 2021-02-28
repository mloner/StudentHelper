package com.example.studenthelpermobile.Repository;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.studenthelpermobile.Model.PrepodInfo;
import com.example.studenthelpermobile.PrepodInfoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class PrepodInfoRepo extends AsyncTask<Void, Void, PrepodInfo> {

    private PrepodInfoView activity;
    private ProgressBar progressBar;
    private JSONObject request;
    private JSONObject responseJSON;
    private PrepodInfo prepodInfo;


    public PrepodInfoRepo (ProgressBar progressBar, PrepodInfoView prepodInfoView, String name) throws JSONException {
        activity = prepodInfoView;
        this.progressBar = progressBar;
        request = new JSONObject();
        request.put("command","getPrepodInfo");
        request.put("fio", name);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected PrepodInfo doInBackground(Void... voids) {
        RepositoryAPI repositoryAPI = new RepositoryAPI();
        try {
            responseJSON = repositoryAPI.getResponse(request);

            String response = responseJSON.get("status").toString();
            JSONObject r = (JSONObject) responseJSON.get("response");

            String cath = r.get("cathedra").toString();
            String fac = r.get("faculty").toString();
            String loc = r.get("location").toString();
            String phone = r.get("phone").toString();
            String mail = r.get("email").toString();
            String pos = r.get("position").toString();

            prepodInfo = new PrepodInfo();
            prepodInfo.setCathedra(cath);
            prepodInfo.setFaculty(fac);
            prepodInfo.setLocation(loc);
            prepodInfo.setPhone(phone);
            prepodInfo.setMail(mail);
            prepodInfo.setPosition(pos);
            prepodInfo.setStatus(response);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return prepodInfo;
    }

    @Override
    protected void onPostExecute(PrepodInfo prepodInfo) {
        super.onPostExecute(prepodInfo);
        activity.onAsyncTaskFinished(prepodInfo);
    }
}