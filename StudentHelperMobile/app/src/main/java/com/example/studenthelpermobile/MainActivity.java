package com.example.studenthelpermobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.studenthelpermobile.Model.ResponseClass;
import com.example.studenthelpermobile.Repository.AsyncInterface;
import com.example.studenthelpermobile.Repository.LoginRepo;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AsyncInterface <ResponseClass> {

    Button StudentBtn;
    Button PrepodBtn;
    Button LoginButton;

    TextView LoginText;
    EditText LoginField;

    TextView PasswordText;
    EditText PasswordField;

    TextView ErrorText;
    ProgressBar loginProgressbar;

    private String login;
    private boolean isStudent;
    private String password;
    private String role;

    public static final String APP_PREFERENCES = "mysettings";
    SharedPreferences mSettings;

    public static final String APP_PREFERENCES_LOGIN = "Login";
    public static final String APP_PREFERENCES_PASS = "Pass";
    public static final String APP_PREFERENCES_ROLE = "Role";
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginText = findViewById(R.id.text_enter_fio);
        loginProgressbar = findViewById(R.id.login_progressbar);
        PasswordText = findViewById(R.id.text_enter_password);
        LoginField = findViewById(R.id.enter_fio);
        ErrorText = findViewById(R.id.error_text);
        PasswordField = findViewById(R.id.enter_password);
        StudentBtn = findViewById(R.id.choose_student);
        StudentBtn.setOnClickListener(this);
        PrepodBtn = findViewById(R.id.choose_prepod);
        PrepodBtn.setOnClickListener(this);
        LoginButton = findViewById(R.id.login_button);
        LoginButton.setOnClickListener(this);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        LoadParams();

        try {
            if(!login.equals("")){
                GetServerResponse();
            }
        }
        catch (NullPointerException e){

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.choose_student:
                LoginField.setText("");
                LoginText.setText(R.string.Enter_Group_number);
                ChangeVisibility();
                PasswordText.setVisibility(View.GONE);
                PasswordField.setVisibility(View.GONE);
                isStudent = true;
                break;
            case R.id.choose_prepod:
                LoginText.setText(R.string.Enter_FIO);
                LoginField.setText("");
                ChangeVisibility();
                PasswordText.setVisibility(View.VISIBLE);
                PasswordField.setVisibility(View.VISIBLE);
                isStudent = false;
                break;
            case R.id.login_button:
                if (isStudent){
                    login = LoginField.getText().toString().toUpperCase();
                    password = "";
                    role = "student";
                }
                else {
                    login = LoginField.getText().toString();
                    password = PasswordField.getText().toString();
                    role = "prepod";
                }
                ErrorText.setText("");
                GetServerResponse();
                break;
        }
    }

    private void LoadParams(){
        if(mSettings.contains(APP_PREFERENCES_LOGIN)) {
            login = mSettings.getString(APP_PREFERENCES_LOGIN, "");
        }
        if(mSettings.contains(APP_PREFERENCES_ROLE)) {
            role = mSettings.getString(APP_PREFERENCES_ROLE, "");
        }
        if(mSettings.contains(APP_PREFERENCES_PASS)) {
            password = mSettings.getString(APP_PREFERENCES_PASS, "");
        }
    }

    private void GetServerResponse(){
        try {
            LoginRepo loginRepo = new LoginRepo(role, login, password, loginProgressbar, this);
            loginRepo.execute();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void ChangeVisibility(){
        LoginText.setVisibility(View.VISIBLE );
        LoginField.setVisibility(View.VISIBLE);
        LoginButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAsyncTaskFinished(ResponseClass responseClass) {
        try {
            if(responseClass.getStatus().equals("OK")) {
                if (responseClass.getResponseString().equals("OK")) {
                    editor = mSettings.edit();
                    editor.putString(APP_PREFERENCES_LOGIN, this.login);
                    editor.putString(APP_PREFERENCES_ROLE, this.role);
                    editor.putString(APP_PREFERENCES_PASS, this.password);
                    editor.apply();
                    Intent intent = new Intent(this, MainMenu.class);
                    intent.putExtra("login", this.login);
                    intent.putExtra("role", role);
                    startActivity(intent);
                }
            }
            else {
                switch (responseClass.getResponseString()) {
                    case "WRONG_LOGIN":
                        if (isStudent) {
                            ErrorText.setText(R.string.Group_error);
                        } else {
                            if (this.login.equals("Хохол"))
                                ErrorText.setText(R.string.Hohol);
                            else
                                ErrorText.setText(R.string.Login_error);
                        }
                        ErrorText.setVisibility(View.VISIBLE);
                        break;
                    case "WRONG_PASSWORD":
                        ErrorText.setText(R.string.Password_error);
                        ErrorText.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
        catch (NullPointerException e){
            ErrorText.setText(R.string.Server_error);
            ErrorText.setVisibility(View.VISIBLE);
        }
        loginProgressbar.setVisibility(View.INVISIBLE);
    }
}
