package com.example.studenthelpermobile.Repository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RepositoryAPI {

    public JSONObject getResponse(URL URL) throws IOException, JSONException {

        JSONObject responseJSON;

        HttpURLConnection connection = (HttpURLConnection) URL.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer responseString = new StringBuffer();


        while ((inputLine = in.readLine()) != null) {
            responseString.append(inputLine);
        }


        in.close();

        responseJSON = new JSONObject(responseString.toString());
        return responseJSON;
    }
}
