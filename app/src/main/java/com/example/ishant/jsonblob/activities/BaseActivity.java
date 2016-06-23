package com.example.ishant.jsonblob.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.ishant.jsonblob.R;

/**
 * Created by Ishant Rana on 23/06/16.
 */
public class BaseActivity extends AppCompatActivity {

    protected Toolbar setUpToolbar(String title){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        TextView titleTextView = (TextView)toolbar.findViewById(R.id.toolbar_title);
        titleTextView.setText(title);

        return toolbar;
    }
}
