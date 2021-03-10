package com.example.studenthelpermobile.Repository;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.studenthelpermobile.Model.ResponseClass;

public class AsyncSuperClass extends AsyncTask <Void, Void, ResponseClass> {

    private ProgressBar progressBar;


    public AsyncSuperClass(ProgressBar progressBar){
        this.progressBar = progressBar;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected ResponseClass doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onPostExecute(ResponseClass responseClass) {

    }
}
