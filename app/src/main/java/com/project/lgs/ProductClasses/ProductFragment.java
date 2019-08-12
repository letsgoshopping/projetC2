package com.project.lgs.ProductClasses;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.project.lgs.MainActivity;
import com.project.lgs.R;

import java.util.ArrayList;

public class ProductFragment extends Fragment {

    String categoryName;
    ArrayList<Product> productList;

    public void setCategoryName (String name){
        this.categoryName = name;
    }
    public void setProductList (ArrayList<Product> prod){
        this.productList = prod;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.product_fragment, container, false);

        ProductAdapter itemsAdapter = new ProductAdapter(MainActivity.mcontext, productList);

        ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

        return view;

    }
}
