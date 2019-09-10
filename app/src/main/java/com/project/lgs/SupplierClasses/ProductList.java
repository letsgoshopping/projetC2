package com.project.lgs.SupplierClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.lgs.AllProductsClasses.AllProductsAdapter;
import com.project.lgs.Database.ProductsMgr;
import com.project.lgs.MainActivity;
import com.project.lgs.ProductClasses.Product;
import com.project.lgs.ProductDetails;
import com.project.lgs.R;
import com.project.lgs.ProductClasses.add_product;

import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductList extends AppCompatActivity implements ProductListAdapter.ProductListListener{

    ArrayList<Product> products = new ArrayList<>();
    int curPos;
    Bundle savedInstanceState;
    static Context proListContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_product_list);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
        }

        proListContext = this;

        Runnable runnable = new Runnable() {

            public void run() {

                ProductsMgr productsMgr = new ProductsMgr(MainActivity.dbName, MainActivity.mongoClient);

                HashMap<String,String> prodIns = new HashMap<>();
                prodIns.put("User",MainActivity.supplierLogin.getId());

                products = productsMgr.findDocument(prodIns,new HashMap<String, Integer>(),50);

                TextView textView = findViewById(R.id.proListproBar);
                textView.setVisibility(View.GONE);

                if (products.size()>0) {

                    ProductListAdapter proAdapter = new ProductListAdapter(ProductList.proListContext, products,ProductList.this);
                    ListView listView = (ListView)findViewById(R.id.sup_pro_list);
                    listView.setAdapter(proAdapter);

                }

            }
        };

            Handler handler =  new Handler();
            handler.postDelayed(runnable, 500);

    }

    @Override
    public void productListListener(int position) {
        Product pro = products.get(position);
        Intent i = new Intent(this, add_product.class);
        i.putExtra("Product", pro);
        startActivityForResult(i,1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if(resultCode == RESULT_OK){

                Toast toast = Toast.makeText(this, "Product Updated", Toast.LENGTH_SHORT);
                toast.show();
                finish();
                startActivity(getIntent());
            }
        }
    }

    public void addPro(View w){

        Intent i = new Intent(this, add_product.class);
        startActivity(i);

    }
}
