package com.example.studenthelpermobile.Model;

public class Message {
    String time;
    String msg;

    public Message(){
        time= "";
        msg = "";
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
