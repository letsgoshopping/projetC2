package com.project.lgs.ProductClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.lgs.R;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {

    public ProductAdapter(Context context, ArrayList<Product> products){
        super(context,0,products);
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        Product currentProduct = getItem(position);

        View productView = convertView;
        if (productView == null){
            productView = LayoutInflater.from(getContext()).inflate(R.layout.product_list,parent,false);
        }

        TextView proTitle  = (TextView) productView.findViewById(R.id.product_title);
        proTitle.setText(currentProduct.getTitle());

        TextView proRat  = (TextView) productView.findViewById(R.id.product_rating);
        proRat.setText(currentProduct.getRating());

        TextView proPrice  = (TextView) productView.findViewById(R.id.product_price);
        proPrice.setText(currentProduct.getPrice());

        TextView proDesc  = (TextView) productView.findViewById(R.id.product_description);
        proDesc.setText(currentProduct.getDescription());

        ImageView proImg  = (ImageView) productView.findViewById(R.id.product_image);
        proImg.setImageResource(currentProduct.getImageRessource());

        TextView proUser = (TextView) productView.findViewById(R.id.product_user);
        proUser.setText(currentProduct.getUser());

        TextView proDate = (TextView) productView.findViewById(R.id.product_date);
        proDate.setText(currentProduct.getPublishDate());

        return productView;
    }
}