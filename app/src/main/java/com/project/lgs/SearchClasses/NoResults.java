package com.project.lgs.SearchClasses;

import android.os.Build;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.project.lgs.R;

public class NoResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_results);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
        }

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);




    }

}
