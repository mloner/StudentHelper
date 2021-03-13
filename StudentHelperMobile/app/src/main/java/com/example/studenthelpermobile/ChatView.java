package com.example.studenthelpermobile;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.studenthelpermobile.Model.ResponseClass;
import com.example.studenthelpermobile.Repository.AsyncInterface;
import com.example.studenthelpermobile.Repository.ChatRepo;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class ChatView extends AppCompatActivity implements AsyncInterface<ResponseClass> {

    private String prepodName;
    private String group;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        prepodName = Objects.requireNonNull(getIntent().getExtras()).getString("prepodName");
        group = Objects.requireNonNull(getIntent().getExtras()).getString("group");
        progressBar = findViewById(R.id.progressbar_chat);
        ChatRepo chatListRepo = new ChatRepo(progressBar, this, group, prepodName);
        chatListRepo.execute();
    }

    @Override
    public void onAsyncTaskFinished(ResponseClass responseClass) {

    }
}
