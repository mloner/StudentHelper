package com.example.studenthelpermobile;

import com.example.studenthelpermobile.Model.ResponseClass;
import com.example.studenthelpermobile.Repository.AsyncInterface;

import androidx.appcompat.app.AppCompatActivity;

public class ChatView extends AppCompatActivity implements AsyncInterface<ResponseClass> {

    @Override
    public void onAsyncTaskFinished(ResponseClass responseClass) {

    }
}
