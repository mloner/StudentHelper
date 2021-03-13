package com.example.studenthelpermobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.studenthelpermobile.Model.ChatTitle;
import com.example.studenthelpermobile.Model.ResponseClass;
import com.example.studenthelpermobile.Repository.AsyncInterface;
import com.example.studenthelpermobile.Repository.ChatListRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class ChatListView extends AppCompatActivity implements AsyncInterface<ResponseClass> {

    private TextView ErrorText;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private ChatListView activity;
    private ArrayList <ChatTitle> ChatList;
    private String role;
    private String login;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list_activity);
        ErrorText = findViewById(R.id.error_text_chat_list);
        progressBar = findViewById(R.id.progressbar_chat_list);
        linearLayout = findViewById(R.id.chat_list_layout);
        activity = this;
        ChatList = new ArrayList<>();
        role = Objects.requireNonNull(getIntent().getExtras()).getString("role");
        login = Objects.requireNonNull(getIntent().getExtras()).getString("login");

        ChatListRepo chatListRepo = new ChatListRepo(progressBar, this, role, login);
        chatListRepo.execute();
    }

    @Override
    public void onAsyncTaskFinished(ResponseClass responseClass) {
        try {
            if(responseClass.getStatus().equals("OK")){
                JSONArray array = responseClass.getResponseArray();
                for(int n = 0; n < array.length(); n++){
                    JSONObject chat = (JSONObject) array.get(n);
                    ChatTitle chatTitle = new ChatTitle();
                    chatTitle.setCount((Integer) chat.get("messageCount"));
                    if(role.equals("student"))
                        chatTitle.setTitle((String) chat.get("prepodName"));
                    else
                        chatTitle.setTitle((String) chat.get("group"));
                    ChatList.add(chatTitle);
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

    public void SetChats(ArrayList<ChatTitle> chats){
        for (ChatTitle s: chats) {
            Button b = new Button(this);
            b.setText(String.format("%s (%d)",s.getTitle(), s.getCount()));
            final String put = s.getTitle();
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, ChatView.class);
                    if(role.equals("student")){
                        intent.putExtra("group", login);
                        intent.putExtra("prepodName", put);
                    }
                    else {
                        intent.putExtra("group", put);
                        intent.putExtra("prepodName", login);
                    }

                    startActivity(intent);
                }
            });
            linearLayout.addView(b);
        }

    }
}
