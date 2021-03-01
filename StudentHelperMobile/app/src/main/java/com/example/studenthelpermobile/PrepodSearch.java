package com.example.studenthelpermobile;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;

public class PrepodSearch implements TextWatcher {

    private PrepodListView activity;
    private ArrayList <String> s;

    public PrepodSearch(PrepodListView prepodListView, ArrayList<String> strings){
        super();
        activity = prepodListView;
        s = strings;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        activity.SetPrepods(s, charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
