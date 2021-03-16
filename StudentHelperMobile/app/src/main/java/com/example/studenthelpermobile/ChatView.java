package com.example.studenthelpermobile;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
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
    private ScrollView scrollView;
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
        scrollView = findViewById(R.id.chat_scroll);

        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        if (role.equals("prepod")){
            chatLine.setVisibility(View.VISIBLE);
            sendButton.setVisibility(View.VISIBLE);
        }

        ChatRepo chatRepo = null;
        try {
            chatRepo = new ChatRepo(progressBar, this, group, prepodName, lessonName, true, "");
            chatRepo.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void onAsyncTaskFinished(ResponseClass responseClass) {
        try {
            if(responseClass.getStatus().equals("OK")){
                messageArrayList = new ArrayList<>();
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
        //скролл в конец
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    private void ServerError(){
        ErrorText.setText(R.string.Server_error);
        ErrorText.setVisibility(View.VISIBLE);
    }

    private void SetMessages(ArrayList<Message> messages){
        linearLayout.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(30,20,0,0);
        ContextThemeWrapper newContext = new ContextThemeWrapper(this, R.style.MessageStyle);
        for (Message m:
                messages) {
            final TextView text = new TextView(newContext);
            text.setText(String.format("%s\n%s\n", m.getTime(), m.getMsg()));
            Linkify.addLinks(text, Linkify.WEB_URLS);
            text.setLinksClickable(true);
            text.setLayoutParams(params);
            final String copy = m.getMsg();
            text.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View view) {
                    myClip = ClipData.newPlainText("text", copy);
                    myClipboard.setPrimaryClip(myClip);
                    Toast toast = Toast.makeText(getApplicationContext(), "Сообщение скопировано", Toast.LENGTH_LONG);
                    toast.show();
                    return false;
                }
            });
            linearLayout.addView(text);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.chat_send:
                String text = chatLine.getText().toString();
                chatLine.setText("");
                InputMethodManager imm = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                try {
                    ChatRepo chatRepo = new ChatRepo(progressBar, this, group, prepodName, lessonName, false, text);
                    chatRepo.execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
