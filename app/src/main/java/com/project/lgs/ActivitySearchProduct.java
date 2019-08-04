package com.project.lgs;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.project.lgs.Database.ProductsMgr;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivitySearchProduct extends Activity {

    public ActivitySearchProduct(){

        ProductsMgr productMgr = new ProductsMgr(getString(R.string.database_name), MainActivity.mongoClient);

        HashMap<String, String> prodIns = new HashMap<String, String>();
        prodIns.put("Title", "Product 1");

        HashMap<String, Integer> prodSort = new HashMap<String, Integer>();
        prodSort.put("Title", 1);

        ArrayList<Product> products = productMgr.findDocument(prodIns, prodSort, 10);
        ProductAdapter itemsAdapter = new ProductAdapter(this,products);

        ListView listView = (ListView)this.findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

    }
    
}

