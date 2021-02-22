package com.example.studenthelpermobile;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.studenthelpermobile.MainActivity.isStudent;
import static com.example.studenthelpermobile.MainActivity.login;

public class MainMenu extends AppCompatActivity {

    TextView WelcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        WelcomeText = findViewById(R.id.welcome_text);

        if(isStudent){
            String s = login + " " + getString(R.string.Group);
            WelcomeText.setText(s);
        }
        else {
            WelcomeText.setText(login);
        }

    }
}
