package com.project.lgs.SearchClasses;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;

import com.mongodb.BasicDBObject;
import com.project.lgs.Database.ProductsMgr;
import com.project.lgs.MainActivity;
import com.project.lgs.ProductClasses.Product;
import com.project.lgs.ProductDetails;
import com.project.lgs.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class SearchActivity extends AppCompatActivity implements SearchAdapter.ProductDetailsListener {

    ArrayList<Product> productArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary)); //status bar or the time bar at the top
        }

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            productArrayList = doMySearch(query);

            if (productArrayList.size()>0) {

                setContentView(R.layout.activity_search);
                SearchAdapter proAdapter = new SearchAdapter(this, productArrayList,this);
                ListView listView = (ListView)findViewById(R.id.search_list);
                listView.setAdapter(proAdapter);

            }else{

                setContentView(R.layout.activity_no_results);
            }

        }
        else
        {
            setContentView(R.layout.activity_problem);
        }


    }

    public ArrayList<Product> doMySearch(String query){

        ProductsMgr productsMgr = new ProductsMgr(MainActivity.dbName,MainActivity.mongoClient);

       // HashMap<String,Document> proSearch = new HashMap<>();
        HashMap<String,Integer> proSort = new HashMap<>();
        BasicDBObject q = new BasicDBObject();
        q.put("Title", java.util.regex.Pattern.compile(query, Pattern.CASE_INSENSITIVE));

        //proSearch.put("Title", new Document().append("$in", query));
        proSort.put("Title",1);

      // return productsMgr.findDocumentNonExact(proSearch,proSort,500);
        return  productsMgr.findDocumentNonExact(q,proSort,500);
    }

    @Override
    public void ProductDetailsListener(int position) {
        Product pro = productArrayList.get(position);
        Intent i = new Intent(this, ProductDetails.class);
        i.putExtra("Product", pro);
        startActivity(i);
    }


}
