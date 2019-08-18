package com.project.lgs.ProductClasses;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.project.lgs.AllProductsClasses.AllProducts;
import com.project.lgs.MainActivity;
import com.project.lgs.ProductDetails;
import com.project.lgs.R;

import java.util.ArrayList;

public class ProductFragment extends Fragment implements ProductAdapter.ProductDetailsListener, View.OnClickListener {

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

        ProductAdapter itemsAdapter = new ProductAdapter(MainActivity.mcontext, productList,this);

        ListView listView = (ListView) view.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        view.findViewById(R.id.more_pro_button).setOnClickListener(this);

        return view;

    }

    @Override
    public void ProductDetailsListener(int position) {
        Product pro = productList.get(position);
        Intent i = new Intent(MainActivity.mcontext, ProductDetails.class);
        i.putExtra("Product", pro);
        startActivity(i);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.mcontext, AllProducts.class);
        intent.putExtra("Category", categoryName);
        startActivity(intent);
    }
}
