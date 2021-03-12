package com.example.studenthelpermobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.studenthelpermobile.Model.ResponseClass;
import com.example.studenthelpermobile.Repository.AsyncInterface;
import com.example.studenthelpermobile.Repository.ChatListRepo;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class ChatListView extends AppCompatActivity implements AsyncInterface<ResponseClass> {

    private TextView ErrorText;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private ChatListView activity;
    private ArrayList <String> ChatList;
    private String role;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list_activity);
        ErrorText = findViewById(R.id.error_text_chat_list);
        progressBar = findViewById(R.id.progressbar_chat_list);
        linearLayout = findViewById(R.id.chat_list_layout);
        activity = this;
        ChatList = new ArrayList<>();
        role = Objects.requireNonNull(getIntent().getExtras()).getString("role");

        ChatListRepo chatListRepo = new ChatListRepo(progressBar, this);
        chatListRepo.execute();
    }

    @Override
    public void onAsyncTaskFinished(ResponseClass responseClass) {
        try {
            if(responseClass.getStatus().equals("OK")){
                JSONArray array = responseClass.getResponseArray();
                for(int n = 0; n < array.length(); n++){
                    final String s = (String) array.get(n);
                    ChatList.add(s);
                }
                SetChats(ChatList);
            }
            else {
                ServerError();
            }
        }
        catch (NullPointerException e){
            ServerError();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        progressBar.setVisibility(View.GONE);
    }

    private void ServerError(){
        ErrorText.setText(R.string.Server_error);
        ErrorText.setVisibility(View.VISIBLE);
    }

    public void SetChats(ArrayList<String> chats){
        for (String s: chats) {
            Button b = new Button(this);
            b.setText(s);
            final String put = s;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, ChatView.class);
                    intent.putExtra("chatName", put);
                    intent.putExtra("role", role);
                    startActivity(intent);
                }
            });
            linearLayout.addView(b);
        }

    }
}
