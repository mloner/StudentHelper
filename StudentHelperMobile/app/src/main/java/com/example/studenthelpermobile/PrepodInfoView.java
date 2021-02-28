package com.example.studenthelpermobile;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.studenthelpermobile.Model.PrepodInfo;
import com.example.studenthelpermobile.Repository.AsyncInterface;
import com.example.studenthelpermobile.Repository.PrepodInfoRepo;

import org.json.JSONException;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class PrepodInfoView extends AppCompatActivity implements AsyncInterface <PrepodInfo> {

    private String name;
    private TextView ErrorText;
    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prepod_info_activity);
        name = Objects.requireNonNull(getIntent().getExtras()).getString("name");
        ErrorText = findViewById(R.id.error_text_prepod_info);
        progressBar = findViewById(R.id.progressbar_prepod_info);
        textView = findViewById(R.id.prepod_info_text);

        try {
            PrepodInfoRepo prepodInfoRepo = new PrepodInfoRepo(progressBar, this,name);
            prepodInfoRepo.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAsyncTaskFinished(PrepodInfo prepodInfo) {
        try {
            if(prepodInfo.getStatus().equals("OK")){
                String info = "Имя: " + name + "\nФакультет: " + prepodInfo.getFaculty() + "\nКафедра: " + prepodInfo.getCathedra() + "\nНа каком этаже: " + prepodInfo.getLocation() +
                        "\nДолжность: " + prepodInfo.getPosition() + "\nНомер телефона: " + prepodInfo.getPhone() + "\nАдрес электронной почты: " + prepodInfo.getMail();
                textView.setText(info);
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
        progressBar.setVisibility(View.GONE);
    }
}
