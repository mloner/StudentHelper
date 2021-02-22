package com.example.studenthelpermobile.Model;

public class ApiRepository {

    public String login(boolean isStudent, String login, String password){
        String response;
        if(isStudent){
            if (login.equals("363")){
                response = "OK";
            }
            else {
                    response = "WRONG_LOGIN";
            }
        }
        else
            response = "WRONG_PASSWORD";

        //ToDO получение ответа на логин

        return response;
    }
}
