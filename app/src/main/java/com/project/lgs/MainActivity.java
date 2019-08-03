package com.project.lgs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteInsertOneResult;
import com.project.lgs.Database.MongoConnect;
import com.project.lgs.Database.ProductsMgr;

import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MongoConnect mongo = new MongoConnect();
        RemoteMongoClient mongoClient = mongo.connect();


        if (mongoClient == null) {

            setContentView(R.layout.activity_problem);

        } else {

            setContentView(R.layout.activity_main);

            ProductsMgr product = new ProductsMgr(getString(R.string.database_name), mongoClient);
            HashMap<String, String> prodIns = new HashMap<String, String>();
            prodIns.put("Title", "Product 1");
            prodIns.put("Description", "This is product 1 description");
            prodIns.put("Price", "10$");
            prodIns.put("Rating", "5");
            String res = product.insertDocument(prodIns);
            Log.d("insert", res);

            HashMap<String, Integer> prodSort = new HashMap<String, Integer>();
            prodSort.put("Title", 1);

            ArrayList<Document> sResults = product.findDocument(prodIns, null);

            if (sResults.size() > 0) {
                Log.d("maya", "result found");
            } else {
                Log.d("maya", "result not found");
            }
        }
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
