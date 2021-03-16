package com.example.studenthelpermobile;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
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
import com.example.studenthelpermobile.SQLite.DatabaseHelper;

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

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list_activity);
        ErrorText = findViewById(R.id.error_text_chat_list);
        progressBar = findViewById(R.id.progressbar_chat_list);
        linearLayout = findViewById(R.id.chat_list_layout);
        activity = this;

        role = Objects.requireNonNull(getIntent().getExtras()).getString("role");
        login = Objects.requireNonNull(getIntent().getExtras()).getString("login");


    }

    @Override
    protected void onStart() {
        super.onStart();
        ChatListRepo chatListRepo = new ChatListRepo(progressBar, this, role, login);
        chatListRepo.execute();
    }

    @Override
    public void onAsyncTaskFinished(ResponseClass responseClass) {
        try {
            if(responseClass.getStatus().equals("OK")){
                ChatList = new ArrayList<>();
                JSONArray array = responseClass.getResponseArray();
                for(int n = 0; n < array.length(); n++){
                    JSONObject chat = (JSONObject) array.get(n);
                    ChatTitle chatTitle = new ChatTitle();
                    chatTitle.setCount((Integer) chat.get("messageCount"));
                    chatTitle.setLessonName((String) chat.get("lessonName"));
                    if(role.equals("student")) {
                        chatTitle.setPrepodName((String) chat.get("prepodName"));
                        chatTitle.setGroup(login);
                    }
                    else {
                        chatTitle.setGroup((String) chat.get("group"));
                        chatTitle.setPrepodName(login);
                    }
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
        linearLayout.removeAllViews();

        for (ChatTitle s: chats) {
            Button b = new Button(this);
            databaseHelper = new DatabaseHelper(getApplicationContext());
            databaseHelper.create_db();
            db = databaseHelper.open();

            String isExist = "select COUNT(id) from read_messages where lessonName =? and prepodName = ? and groupName =?";
            SQLiteStatement isExiststatement = db.compileStatement(isExist);
            isExiststatement.bindString(1,s.getLessonName());
            isExiststatement.bindString(2,s.getPrepodName());
            isExiststatement.bindString(3,s.getGroup());
            long id = isExiststatement.simpleQueryForLong();
            long count;
            final String group = s.getGroup();
            final String prepodName = s.getPrepodName();
            final String lessonName = s.getLessonName();
            final long insert_count = s.getCount();
            if(id == 0){
                String sql = "INSERT INTO read_messages (lessonName, prepodName, groupName) VALUES (?,?,?)";
                SQLiteStatement statement = db.compileStatement(sql);
                statement.bindString(1,s.getLessonName());
                statement.bindString(2,s.getPrepodName());
                statement.bindString(3,s.getGroup());
                statement.executeInsert();
                count = 0;
            }
            else {
                String sql = "select countMessages from read_messages where lessonName =? and prepodName = ? and groupName =?";
                SQLiteStatement statement = db.compileStatement(sql);
                statement.bindString(1,s.getLessonName());
                statement.bindString(2,s.getPrepodName());
                statement.bindString(3,s.getGroup());
                count = statement.simpleQueryForLong();
            }
            db.close();
            if(role.equals("student")){
                if(s.getCount() - count != 0)
                    b.setText(String.format("%s\n%s (%d)",s.getLessonName(),s.getPrepodName(), s.getCount() - count));
                else
                    b.setText(String.format("%s\n%s",s.getLessonName(),s.getPrepodName()));
            }
            else {
                if(s.getCount() - count != 0)
                    b.setText(String.format("%s\n%s (%d)",s.getLessonName(),s.getGroup(), s.getCount() - count));
                else
                    b.setText(String.format("%s\n%s",s.getLessonName(),s.getGroup()));
            }

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    databaseHelper.create_db();
                    db = databaseHelper.open();
                    String sql = "update read_messages set countMessages = ? where lessonName =? and prepodName = ? and groupName =?";
                    SQLiteStatement statement = db.compileStatement(sql);
                    statement.bindLong(1,insert_count);
                    statement.bindString(2,lessonName);
                    statement.bindString(3,prepodName);
                    statement.bindString(4,group);
                    statement.executeUpdateDelete();
                    db.close();

                    Intent intent = new Intent(activity, ChatView.class);
                    intent.putExtra("group", group);
                    intent.putExtra("prepodName", prepodName);
                    intent.putExtra("lessonName", lessonName);
                    intent.putExtra("role", role);
                    startActivity(intent);
                }
            });
            linearLayout.addView(b);
        }

    }
}
