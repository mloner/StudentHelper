package com.example.studenthelpermobile.Model;

import org.json.JSONArray;
import org.json.JSONObject;

public class ResponseClass {

    private String status;
    private JSONArray responseArray;
    private String responseString;

    public ResponseClass(){
        status = "";
        responseArray = new JSONArray();

    }

    public JSONArray getResponseArray() {
        return responseArray;
    }

    public void setResponseArray(JSONArray response) {
        this.responseArray = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }
}
