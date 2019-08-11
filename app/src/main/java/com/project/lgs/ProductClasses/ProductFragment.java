package com.project.lgs.ProductClasses;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.project.lgs.Database.ProductsMgr;
import com.project.lgs.MainActivity;
import com.project.lgs.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductFragment extends Fragment {

    String categoryName;

    public void setCategoryName (String name){
        this.categoryName = name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.product_fragment, container, false);
        ProductsMgr productMgr = new ProductsMgr(MainActivity.dbName, MainActivity.mongoClient);

        HashMap<String, String> prodIns = new HashMap<String, String>();
        prodIns.put("Category", categoryName);

        HashMap<String, Integer> prodSort = new HashMap<String, Integer>();
        prodSort.put("PDate", 1);

        ArrayList<Product> products = productMgr.findDocument(prodIns, prodSort, 500);
        ProductAdapter itemsAdapter = new ProductAdapter(MainActivity.mcontext, products);

        ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

        return view;

    }
}
