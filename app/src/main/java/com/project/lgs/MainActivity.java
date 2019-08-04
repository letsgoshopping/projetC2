package com.project.lgs;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TableLayout;

import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.project.lgs.Database.MongoConnect;


public class MainActivity extends AppCompatActivity{

    public static RemoteMongoClient mongoClient;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MongoConnect mongo = new MongoConnect();
        mongoClient = mongo.connect();

        if (mongoClient == null) {

            setContentView(R.layout.activity_problem);

        } else {

            setContentView(R.layout.activity_main);
            toolbar = findViewById(R.id.toolBar);
            setSupportActionBar(toolbar);

            viewPager = findViewById(R.id.pager);
            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(viewPagerAdapter);

            tabLayout = findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);

        }
    }

}
