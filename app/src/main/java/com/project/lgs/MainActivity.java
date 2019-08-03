package com.project.lgs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.project.lgs.Database.MongoConnect;


public class MainActivity extends AppCompatActivity{

    public static RemoteMongoClient mongoClient;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MongoConnect mongo = new MongoConnect();
        mongoClient = mongo.connect();

        if (mongoClient == null) {

            setContentView(R.layout.activity_problem);

        } else {

            setContentView(R.layout.activity_main);
        }
    }

    public void searchProduct(View view) {

        startActivity(new Intent(this,SearchProductActivity.class));

    }

    public void searchUser(View view) {
        startActivity(new Intent(this,SearchUserActivity.class));

    }
}
