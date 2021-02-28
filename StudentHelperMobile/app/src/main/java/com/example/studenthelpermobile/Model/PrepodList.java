package com.example.studenthelpermobile.Model;

import org.json.simple.JSONArray;

public class PrepodList {

    private String status;
    private JSONArray response;

    public PrepodList(){
        status = "";
        response = new JSONArray();
    }

    public JSONArray getResponse() {
        return response;
    }

    public void setResponse(JSONArray response) {
        this.response = response;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
