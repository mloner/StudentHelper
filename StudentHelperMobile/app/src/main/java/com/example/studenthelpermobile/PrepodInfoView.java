package com.example.studenthelpermobile;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.studenthelpermobile.Model.PrepodInfo;
import com.example.studenthelpermobile.Model.ResponseClass;
import com.example.studenthelpermobile.Repository.AsyncInterface;
import com.example.studenthelpermobile.Repository.PrepodInfoRepo;

import org.json.JSONException;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class PrepodInfoView extends AppCompatActivity implements AsyncInterface <ResponseClass> {

    private String name;
    private TextView ErrorText;
    private ProgressBar progressBar;
    private TextView textView;
    private PrepodInfo prepodInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prepod_info_activity);
        name = Objects.requireNonNull(getIntent().getExtras()).getString("name");
        ErrorText = findViewById(R.id.error_text_prepod_info);
        progressBar = findViewById(R.id.progressbar_prepod_info);
        textView = findViewById(R.id.prepod_info_text);

        PrepodInfoRepo prepodInfoRepo = new PrepodInfoRepo(progressBar, this,name);
        prepodInfoRepo.execute();
    }

    @Override
    public void onAsyncTaskFinished(ResponseClass responseClass) {


        try {
            String cath = responseClass.getResponseObject().get("cathedra").toString();
            String fac = responseClass.getResponseObject().get("faculty").toString();
            String loc = responseClass.getResponseObject().get("location").toString();
            String phone = responseClass.getResponseObject().get("phone").toString();
            String mail = responseClass.getResponseObject().get("email").toString();
            String pos = responseClass.getResponseObject().get("position").toString();
            String degree = responseClass.getResponseObject().get("degree").toString();prepodInfo = new PrepodInfo();
            prepodInfo.setCathedra(cath);
            prepodInfo.setFaculty(fac);
            prepodInfo.setLocation(loc);
            prepodInfo.setPhone(phone);
            prepodInfo.setMail(mail);
            prepodInfo.setPosition(pos);
            prepodInfo.setDegree(degree);
        }

        catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if(responseClass.getStatus().equals("OK")){
                String info = "Имя: " + name + "\nФакультет: " + prepodInfo.getFaculty() + "\nКафедра: " + prepodInfo.getCathedra() + "\nГде найти: " + prepodInfo.getLocation() +
                        "\nДолжность: " + prepodInfo.getPosition() + "\nУченая степень: " + prepodInfo.getDegree() + "\nНомер телефона: " + prepodInfo.getPhone() + "\nАдрес электронной почты: " + prepodInfo.getMail();
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
