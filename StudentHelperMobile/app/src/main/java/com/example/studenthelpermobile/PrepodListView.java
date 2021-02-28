package com.example.studenthelpermobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.studenthelpermobile.Model.PrepodList;
import com.example.studenthelpermobile.Repository.AsyncInterface;
import com.example.studenthelpermobile.Repository.PrepodListRepo;

import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;

import java.util.Iterator;

import androidx.appcompat.app.AppCompatActivity;

public class PrepodListView extends AppCompatActivity implements AsyncInterface <PrepodList> {


    TextView ErrorText;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prepod_list_activity);
        ErrorText = findViewById(R.id.error_text_prepod_list);
        progressBar = findViewById(R.id.progressbar_prepod_list);

        try {
            PrepodListRepo prepodListRepo = new PrepodListRepo(progressBar, this);
            prepodListRepo.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onAsyncTaskFinished(PrepodList prepodList) {
        progressBar.setVisibility(View.GONE);
        //ToDo сделать как при логине, распарсить JSONArray
        try {
            if(prepodList.getStatus().equals("OK")){
                JSONArray array = prepodList.getResponse();

                Iterator i = array.iterator();

                while (i.hasNext()) {
                    JSONObject innerObj = (JSONObject) i.next();
                    Button button = new Button(this);
                    button.setText(innerObj.toString());
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
        progressBar.setVisibility(View.INVISIBLE);

    }
}
