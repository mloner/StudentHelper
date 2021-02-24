package com.example.studenthelpermobile.Model;

public class Login {

    private String status;
    private String response;

    public Login(){
        status="";
        response="";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }


}
