package com.example.studenthelpermobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.studenthelpermobile.Model.ApiRepository;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button StudentBtn;
    Button PrepodBtn;
    TextView LoginText;
    EditText LoginField;
    TextView PasswordText;
    EditText PasswordField;
    Button LoginButton;
    TextView ErrorText;
    public static boolean isStudent;
    ApiRepository apiRepository;

    public static String login;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginText = findViewById(R.id.text_enter_fio);
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
        apiRepository = new ApiRepository();
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
                String response;
                response = apiRepository.login(isStudent, login, password);

                switch (response){
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

                break;
        }
    }
}