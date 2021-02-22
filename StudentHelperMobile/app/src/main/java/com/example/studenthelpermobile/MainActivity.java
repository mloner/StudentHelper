package com.example.studenthelpermobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button Student;
    Button Prepod;
    TextView LoginText;
    EditText Login;
    TextView EnterPasswordText;
    EditText Password;
    Button LoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginText = findViewById(R.id.text_enter_fio);
        EnterPasswordText = findViewById(R.id.text_enter_password);
        Login = findViewById(R.id.enter_fio);
        Password = findViewById(R.id.enter_password);
        Student = findViewById(R.id.choose_student);
        Student.setOnClickListener(this);
        Prepod = findViewById(R.id.choose_prepod);
        Prepod.setOnClickListener(this);
        LoginButton = findViewById(R.id.login_button);
        LoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.choose_student:
                LoginText.setVisibility(View.VISIBLE );
                LoginText.setText(R.string.Enter_Group_number);
                Login.setVisibility(View.VISIBLE);
                EnterPasswordText.setVisibility(View.GONE);
                Password.setVisibility(View.GONE);
                LoginButton.setVisibility(View.VISIBLE);
                break;
            case R.id.choose_prepod:
                LoginText.setVisibility(View.VISIBLE );
                LoginText.setText(R.string.Enter_FIO);
                Login.setVisibility(View.VISIBLE );
                EnterPasswordText.setVisibility(View.VISIBLE);
                Password.setVisibility(View.VISIBLE);
                LoginButton.setVisibility(View.VISIBLE);
                break;
        }
    }
}
