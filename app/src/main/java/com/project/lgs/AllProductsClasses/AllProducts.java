package com.project.lgs.AllProductsClasses;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.project.lgs.Database.ProductsMgr;
import com.project.lgs.MainActivity;
import com.project.lgs.ProductClasses.Product;
import com.project.lgs.ProductDetails;
import com.project.lgs.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AllProducts extends AppCompatActivity  implements AllProductsAdapter.ProductDetailsListener{

    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary)); //status bar or the time bar at the top
        }


        String category = (String) getIntent().getStringExtra("Category");

        ProductsMgr productMgr = new ProductsMgr(MainActivity.dbName, MainActivity.mongoClient);

        HashMap<String, String> prodIns = new HashMap<String, String>();
        prodIns.put("Category", category);

        HashMap<String, Integer> prodSort = new HashMap<String, Integer>();
        prodSort.put("PDate", 1);

        products = productMgr.findDocument(prodIns, prodSort, 500);

        if (products.size()>0) {

            setContentView(R.layout.activity_all_products);
            AllProductsAdapter proAdapter = new AllProductsAdapter(this, products,this);
            ListView listView = (ListView)findViewById(R.id.allpro_list);
            listView.setAdapter(proAdapter);

        }else{

            setContentView(R.layout.activity_no_results);
        }
    }

    @Override
    public void ProductDetailsListener(int position) {
        Product pro = products.get(position);
        Intent i = new Intent(this, ProductDetails.class);
        i.putExtra("Product", pro);
        startActivity(i);
    }
}
