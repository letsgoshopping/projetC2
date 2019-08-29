package com.project.lgs.SupplierClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.project.lgs.AllProductsClasses.AllProductsAdapter;
import com.project.lgs.Database.ProductsMgr;
import com.project.lgs.MainActivity;
import com.project.lgs.ProductClasses.Product;
import com.project.lgs.ProductDetails;
import com.project.lgs.R;
import com.project.lgs.add_product;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductList extends AppCompatActivity implements AllProductsAdapter.ProductDetailsListener{

    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
        }

       /* ProductsMgr productMgr = new ProductsMgr(MainActivity.dbName, MainActivity.mongoClient);

        HashMap<String, String> prodIns = new HashMap<String, String>();
        prodIns.put("User", MainActivity.supplierLogin.getId());

        HashMap<String, Integer> prodSort = new HashMap<String, Integer>();
        prodSort.put("PDate", 1);

        products = productMgr.findDocument(prodIns, prodSort, 500);

        if (products.size()>0) {

            setContentView(R.layout.activity_product_list);
            AllProductsAdapter proAdapter = new AllProductsAdapter(this, products,this);
            ListView listView = (ListView)findViewById(R.id.allsup_list);
            listView.setAdapter(proAdapter);

        }else{

            setContentView(R.layout.activity_product_list);
        }*/
    }

    @Override
    public void ProductDetailsListener(int position) {
        Product pro = products.get(position);
        Intent i = new Intent(this, ProductDetails.class);
        i.putExtra("Product", pro);
        startActivity(i);
    }

    public void addPro(View w){

        Intent i = new Intent(this, add_product.class);
        startActivity(i);

    }
}
