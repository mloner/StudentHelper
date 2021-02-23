package com.example.studenthelpermobile.Model;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import static com.example.studenthelpermobile.MainActivity.isStudent;

public class ApiRepository {

    private JSONParser jsonParser;
    private JSONObject request;

    private final String client_type = "mobile";
    private String authorization_request = "authorization";
    private String schedule_request = "schedule_check";
    private String prepod_list_request = "prepod_list";

    private String role;
    private String group;

    public ApiRepository(){
        jsonParser = new JSONParser();
        request = new JSONObject();
        if(isStudent){
            role = "student";
        }
        else {
            role = "prepod";
        }
    }

    public String login(boolean isStudent, String login, String password) throws JSONException {
        String response;

        request.put("client_type", client_type);
        request.put("command", authorization_request);
        request.put("role", role);
        request.put("pass",password);
        request.put("arg", login);



        if(isStudent){
            if (login.equals("363")){
                response = "OK";
            }
            else {
                    response = "WRONG_LOGIN";
            }
        }
        else if(login.equals("123") && password.equals("123")){
            response = "OK";
        }
        else
            response = "WRONG_PASSWORD";

        //ToDO получение ответа на логин

        return response;
    }

    public JSONObject Schedule(int days, String login) throws JSONException {
        JSONObject response = new JSONObject();
        request.put("command", schedule_request);
        request.put("role", role);
        if(isStudent){
            request.put("group", login);
        }
        else {
            request.put("fio", login);
        }
        switch (days){
            case 1:
                request.put("schedule_type","today");
                break;
            case 2:
                request.put("schedule_type","tomorrow");
                break;
            case 14:
                request.put("schedule_type","two_weeks");
                break;

        }
        //ToDO получить ответ от сервера

        return response;
    }

    public JSONObject PrepodList() throws JSONException {
        JSONObject response = new JSONObject();
        request.put("command", prepod_list_request);
        //ToDO получить ответ от сервера
        return response;
    }
}
