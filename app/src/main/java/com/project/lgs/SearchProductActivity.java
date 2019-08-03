package com.project.lgs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.project.lgs.Database.ProductsMgr;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        ProductsMgr productMgr = new ProductsMgr(getString(R.string.database_name), MainActivity.mongoClient);

        HashMap<String, String> prodIns = new HashMap<String, String>();
        prodIns.put("Title", "Product 1");

        HashMap<String, Integer> prodSort = new HashMap<String, Integer>();
        prodSort.put("Title", 1);

        ArrayList<Product> products = productMgr.findDocument(prodIns, prodSort, 10);
        ProductAdapter itemsAdapter = new ProductAdapter(this,products);

        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

    }
}
