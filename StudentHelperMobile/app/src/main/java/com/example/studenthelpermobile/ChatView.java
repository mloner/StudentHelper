package com.example.studenthelpermobile;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studenthelpermobile.Model.ChatTitle;
import com.example.studenthelpermobile.Model.Message;
import com.example.studenthelpermobile.Model.ResponseClass;
import com.example.studenthelpermobile.Repository.AsyncInterface;
import com.example.studenthelpermobile.Repository.ChatRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class ChatView extends AppCompatActivity implements AsyncInterface<ResponseClass>, View.OnClickListener {

    private String prepodName;
    private String group;
    private String lessonName;
    private ProgressBar progressBar;
    private TextView ErrorText;
    private LinearLayout linearLayout;
    private String role;
    private EditText chatLine;
    private ImageButton sendButton;
    private ArrayList <Message> messageArrayList;
    ClipboardManager myClipboard;
    ClipData myClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        prepodName = Objects.requireNonNull(getIntent().getExtras()).getString("prepodName");
        group = Objects.requireNonNull(getIntent().getExtras()).getString("group");
        lessonName = Objects.requireNonNull(getIntent().getExtras()).getString("lessonName");
        role = Objects.requireNonNull(getIntent().getExtras()).getString("role");
        progressBar = findViewById(R.id.progressbar_chat);
        linearLayout = findViewById(R.id.chat_layout);
        chatLine = findViewById(R.id.chat_line);
        sendButton = findViewById(R.id.chat_send);
        sendButton.setOnClickListener(this);
        ErrorText = findViewById(R.id.error_text_chat);
        messageArrayList = new ArrayList<>();
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        if (role.equals("prepod")){
            chatLine.setVisibility(View.VISIBLE);
            sendButton.setVisibility(View.VISIBLE);
        }

        ChatRepo chatRepo = new ChatRepo(progressBar, this, group, prepodName, lessonName);
        chatRepo.execute();

    }

    @Override
    public void onAsyncTaskFinished(ResponseClass responseClass) {
        try {
            if(responseClass.getStatus().equals("OK")){
                JSONArray array = responseClass.getResponseArray();
                for(int n = 0; n < array.length(); n++){
                    JSONObject message = (JSONObject) array.get(n);
                    Message msg = new Message();
                    msg.setTime((String) message.get("time"));
                    msg.setMsg((String) message.get("msg"));
                    messageArrayList.add(msg);
                }
                SetMessages(messageArrayList);
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

    private void SetMessages(ArrayList<Message> messages){
        for (Message m:
                messages) {
            final TextView editText = new TextView(this);
            editText.setText(String.format("%s\n%s\n", m.getTime(), m.getMsg()));
            editText.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View view) {
                    myClip = ClipData.newPlainText("text", editText.getText());
                    myClipboard.setPrimaryClip(myClip);
                    Toast toast = Toast.makeText(getApplicationContext(), "Сообщение скопировано", Toast.LENGTH_LONG);
                    toast.show();
                    return false;
                }
            });
            linearLayout.addView(editText);
        }
    }

    @Override
    public void onClick(View view) {

    }
}
