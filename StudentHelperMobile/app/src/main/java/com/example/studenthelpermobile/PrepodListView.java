package com.example.studenthelpermobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.studenthelpermobile.Model.PrepodList;
import com.example.studenthelpermobile.Repository.AsyncInterface;
import com.example.studenthelpermobile.Repository.PrepodListRepo;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class PrepodListView extends AppCompatActivity implements AsyncInterface <PrepodList> {

    private TextView ErrorText;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private PrepodListView activity;
    private EditText SearchText;
    private ArrayList <String> PrepodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prepod_list_activity);
        ErrorText = findViewById(R.id.error_text_prepod_list);
        progressBar = findViewById(R.id.progressbar_prepod_list);
        SearchText = findViewById(R.id.search_text);
        linearLayout = findViewById(R.id.prepod_list_layout);
        activity = this;

        try {
            PrepodListRepo prepodListRepo = new PrepodListRepo(progressBar, this);
            prepodListRepo.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        PrepodList = new ArrayList<>();
        PrepodSearch prepodSearch = new PrepodSearch(this, PrepodList );
        SearchText.addTextChangedListener(prepodSearch);
    }


    @Override
    public void onAsyncTaskFinished(PrepodList prepodListClass) {
        try {
            if(prepodListClass.getStatus().equals("OK")){
                JSONArray array = prepodListClass.getResponse();
                for(int n = 0; n < array.length(); n++){
                    final String s = (String) array.get(n);
                    PrepodList.add(s);
                }
                SetPrepods(PrepodList, "");
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

    }

    private void ServerError(){
        ErrorText.setText(R.string.Server_error);
        ErrorText.setVisibility(View.VISIBLE);
    }

    public void SetPrepods(ArrayList<String> prepods, String sort){
        linearLayout.removeAllViews();
        for (String s: prepods) {
            if(s.toLowerCase().contains(sort.toLowerCase())){
                Button b = new Button(this);
                b.setText(s);
                final String put = s;
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(activity, PrepodInfoView.class);
                        intent.putExtra("name", put);
                        startActivity(intent);
                    }
                });
                linearLayout.addView(b);
            }
        }
    }

}
