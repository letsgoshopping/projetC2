package com.project.lgs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.project.lgs.Database.MongoConnect;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MongoConnect mongo =  new MongoConnect();
        RemoteMongoClient client = mongo.connect();

    }

    public void searchProduct(View view) {
        Intent i = new Intent(this,SearchProductActivity.class);
        startActivity(i);

    }

    public void searchUser(View view) {
        Intent i = new Intent(this,SearchUserActivity.class);
        startActivity(i);

    }
}
