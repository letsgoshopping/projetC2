package com.project.lgs;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.project.lgs.Database.MongoConnect;
import com.project.lgs.Database.SupplierMgr;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity{

    public static RemoteMongoClient mongoClient;
    public static Context mcontext;
    public static final String dbName = "LGS";
    private Toolbar toolbar;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
        }

        MongoConnect mongo = new MongoConnect();
        mongoClient = mongo.connect();
        mcontext = this;

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
