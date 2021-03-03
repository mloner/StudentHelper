package com.example.studenthelpermobile.Repository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class RepositoryAPI {

    public JSONObject postResponse(JSONObject request) throws IOException, JSONException {

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

        int code = connection.getResponseCode();
        while ((inputLine = in.readLine()) != null) {
            responseString.append(inputLine);
        }

        in.close();

        String s = responseString.toString();

        responseJSON = new JSONObject(s);

        return responseJSON;
    }

        public JSONObject getRequest(URL url) throws IOException, JSONException {

        JSONObject responseJSON;

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        int code = connection.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();


        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        String s = response.toString();

        responseJSON = new JSONObject(s);

        return responseJSON;
    }

    public String URLBuilder (String url, Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        result.append("?");

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }
        String resultString = result.toString();
        if(resultString.length()> 1){
            resultString = resultString.substring(0, resultString.length() - 1);
        }
        url = url + resultString;
        return url;
    }
}
