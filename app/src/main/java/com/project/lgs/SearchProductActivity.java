package com.project.lgs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        ArrayList<Product> products = new ArrayList<Product>();
        products.add(new Product("Product 1","this is a description",5.0, 10.0, R.drawable.prodsample,"User1","5/5/2019"));
        products.add(new Product("Product 2","this is a description",3.0, 15.0, R.drawable.prodsample,"user2","5/5/2019"));
        products.add(new Product("Product 3","this is a description",2.0, 100.0, R.drawable.prodsample,"user3","5/5/2019"));
        products.add(new Product("Product 4","this is a description",1.0, 25.0, R.drawable.prodsample,"User4","5/5/2019"));
        products.add(new Product("Product 5","this is a description",2.0, 20.0, R.drawable.prodsample,"User6","5/5/2019"));
        products.add(new Product("Product 6","this is a description",4.0, 25.0, R.drawable.prodsample,"User7","5/5/2019"));
        products.add(new Product("Product 7","this is a description",2.5, 205.0, R.drawable.prodsample,"user8","5/5/2019"));

        ProductAdapter itemsAdapter = new ProductAdapter(this,products);

        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

    }
}
