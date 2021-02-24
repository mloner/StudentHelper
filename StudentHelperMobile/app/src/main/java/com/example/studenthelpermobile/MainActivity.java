package com.example.studenthelpermobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.studenthelpermobile.Model.ApiRepository;
import com.example.studenthelpermobile.Model.AsyncInterface;
import com.example.studenthelpermobile.Model.Login;
import com.example.studenthelpermobile.Model.LoginRepo;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AsyncInterface {

    Button StudentBtn;
    Button PrepodBtn;
    Button LoginButton;

    TextView LoginText;
    EditText LoginField;

    TextView PasswordText;
    EditText PasswordField;

    TextView ErrorText;
    ProgressBar loginProgressbar;

    public static String login;
    public static boolean isStudent;
    String password;

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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.choose_student:
                LoginText.setVisibility(View.VISIBLE );
                LoginText.setText(R.string.Enter_Group_number);
                LoginField.setVisibility(View.VISIBLE);
                PasswordText.setVisibility(View.GONE);
                PasswordField.setVisibility(View.GONE);
                LoginButton.setVisibility(View.VISIBLE);
                isStudent = true;
                break;
            case R.id.choose_prepod:
                LoginText.setVisibility(View.VISIBLE );
                LoginText.setText(R.string.Enter_FIO);
                LoginField.setVisibility(View.VISIBLE );
                PasswordText.setVisibility(View.VISIBLE);
                PasswordField.setVisibility(View.VISIBLE);
                LoginButton.setVisibility(View.VISIBLE);
                isStudent = false;
                break;
            case R.id.login_button:
                if (isStudent){
                    login = LoginField.getText().toString();
                    password = "";
                }
                else {
                    login = LoginField.getText().toString();
                    password = PasswordField.getText().toString();
                }
                ErrorText.setText("");
                String response ="";

                try {//получить ответ от сервера
                    LoginRepo loginRepo = new LoginRepo(isStudent, login, password, loginProgressbar, this);
                    loginRepo.execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onAsyncTaskFinished(Login login) {
        try {
            if(login.getStatus().equals("OK")){
                switch (login.getResponse()){
                    case "OK":
                        Intent intent = new Intent(this, MainMenu.class);
                        startActivity(intent);
                        break;
                    case "WRONG_LOGIN":
                        if(isStudent){
                            ErrorText.setText(R.string.Group_error);
                        }
                        else {
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
            else {
                ErrorText.setText(R.string.Server_error);
                ErrorText.setVisibility(View.VISIBLE);
            }
        }
        catch (NullPointerException e){
            ErrorText.setText(R.string.Server_error);
            ErrorText.setVisibility(View.VISIBLE);
        }

        loginProgressbar.setVisibility(View.INVISIBLE);
    }
}
