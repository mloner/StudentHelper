package com.example.studenthelpermobile;

import android.os.Bundle;

import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class PrepodInfo extends AppCompatActivity {

    JSONObject prepodlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prepod_info_activity);

    }
}
