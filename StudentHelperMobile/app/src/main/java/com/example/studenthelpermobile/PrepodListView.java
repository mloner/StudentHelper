package com.example.studenthelpermobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.studenthelpermobile.Model.PrepodList;
import com.example.studenthelpermobile.Repository.AsyncInterface;
import com.example.studenthelpermobile.Repository.PrepodListRepo;

import org.json.JSONArray;
import org.json.JSONException;

import androidx.appcompat.app.AppCompatActivity;

public class PrepodListView extends AppCompatActivity implements AsyncInterface <PrepodList> {


    private TextView ErrorText;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private PrepodListView activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prepod_list_activity);
        ErrorText = findViewById(R.id.error_text_prepod_list);
        progressBar = findViewById(R.id.progressbar_prepod_list);
        linearLayout = findViewById(R.id.prepod_list_layout);
        activity = this;


        try {
            PrepodListRepo prepodListRepo = new PrepodListRepo(progressBar, this);
            prepodListRepo.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onAsyncTaskFinished(PrepodList prepodList) {

        try {
            if(prepodList.getStatus().equals("OK")){
                JSONArray array = prepodList.getResponse();

                for(int n = 0; n < array.length(); n++){
                    final String s = (String) array.get(n);
                    Button b = new Button(this);
                    b.setText(s);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(activity, PrepodInfoView.class);
                            intent.putExtra("name", s);
                            startActivity(intent);
                        }
                    });
                    linearLayout.addView(b);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        progressBar.setVisibility(View.GONE);

    }
}
