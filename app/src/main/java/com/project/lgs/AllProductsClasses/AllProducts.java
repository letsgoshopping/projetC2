package com.project.lgs.AllProductsClasses;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.project.lgs.Database.ProductsMgr;
import com.project.lgs.MainActivity;
import com.project.lgs.ProductClasses.Product;
import com.project.lgs.ProductDetails;
import com.project.lgs.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AllProducts extends AppCompatActivity  implements AllProductsAdapter.ProductDetailsListener{

    ArrayList<Product> products;
    static Context allProContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary)); //status bar or the time bar at the top
        }

        allProContext =this;

        Runnable runnable = new Runnable() {

            public void run() {

                String category = (String) getIntent().getStringExtra("Category");

                ProductsMgr productMgr = new ProductsMgr(MainActivity.dbName, MainActivity.mongoClient);

                HashMap<String, String> prodIns = new HashMap<String, String>();
                prodIns.put("Category", category);

                HashMap<String, Integer> prodSort = new HashMap<String, Integer>();
                prodSort.put("PDate", 1);

                products = productMgr.findDocument(prodIns, prodSort, 500);

                TextView textView = findViewById(R.id.allProProBar);
                textView.setVisibility(View.GONE);

                ListView cartList = findViewById(R.id.allpro_list);
                cartList.setVisibility(View.VISIBLE);


                AllProductsAdapter proAdapter = new AllProductsAdapter(AllProducts.allProContext, products,AllProducts.this);
                ListView listView = (ListView)findViewById(R.id.allpro_list);
                listView.setAdapter(proAdapter);

            }
        };

        Handler handler =  new Handler();
        handler.postDelayed(runnable, 500);

    }

    @Override
    public void ProductDetailsListener(int position) {
        Product pro = products.get(position);
        Intent i = new Intent(this, ProductDetails.class);
        i.putExtra("Product", pro);
        startActivity(i);
    }
}
