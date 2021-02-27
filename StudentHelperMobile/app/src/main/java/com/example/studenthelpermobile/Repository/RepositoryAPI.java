package com.example.studenthelpermobile.Repository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RepositoryAPI {

    public JSONObject getResponse(JSONObject request) throws IOException, JSONException {

        JSONObject responseJSON;
        URL url = new URL("http://shipshon.fvds.ru/api");

        //Отправка запроса
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = request.toString().getBytes();
            os.write(input, 0, input.length);
        }

        //Получение ответа

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer responseString = new StringBuffer();


        while ((inputLine = in.readLine()) != null) {
            responseString.append(inputLine);
        }

        in.close();

        String s = responseString.toString();

        responseJSON = new JSONObject(s);


        return responseJSON;
    }
}
