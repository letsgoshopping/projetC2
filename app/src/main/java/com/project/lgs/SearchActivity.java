package com.project.lgs;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.project.lgs.Database.ProductsMgr;
import com.project.lgs.ProductClasses.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            ArrayList<Product> productArrayList = doMySearch(query);
            SearchAdapter proAdapter = new SearchAdapter(this,productArrayList);

            ListView listView = (ListView)findViewById(R.id.search_list);
            listView.setAdapter(proAdapter);

        }
        else
        {
            setContentView(R.layout.activity_problem);
        }


    }

    public ArrayList<Product> doMySearch(String query){

        ProductsMgr productsMgr = new ProductsMgr(MainActivity.dbName,MainActivity.mongoClient);

        HashMap<String,String> proSearch = new HashMap<>();
        HashMap<String,Integer> proSort = new HashMap<>();

        proSearch.put("Title",query);
        proSort.put("Title",1);

       return productsMgr.findDocument(proSearch,proSort,500);
    }
}
