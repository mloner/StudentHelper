package com.example.studenthelpermobile;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.studenthelpermobile.Model.ChatList;
import com.example.studenthelpermobile.Model.PrepodList;
import com.example.studenthelpermobile.Repository.AsyncInterface;
import com.example.studenthelpermobile.Repository.ChatListRepo;
import com.example.studenthelpermobile.Repository.PrepodListRepo;

import org.json.JSONException;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class ChatListView extends AppCompatActivity implements AsyncInterface<ChatList> {

    private TextView ErrorText;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private ChatListView activity;
    private ArrayList <String> PrepodList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list_activity);
        ErrorText = findViewById(R.id.error_text_chat_list);
        progressBar = findViewById(R.id.progressbar_chat_list);
        linearLayout = findViewById(R.id.chat_list_layout);
        activity = this;

        try {
            ChatListRepo chatListRepo = new ChatListRepo(progressBar, this);
            chatListRepo.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onAsyncTaskFinished(ChatList chatList) {
        //ToDO вынести распаковку в отдельный класс
    }
}
