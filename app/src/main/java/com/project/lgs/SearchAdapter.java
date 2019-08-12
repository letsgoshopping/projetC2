package com.project.lgs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.lgs.ProductClasses.Product;

import java.util.ArrayList;

public class SearchAdapter extends ArrayAdapter<Product> {

    public SearchAdapter(Context context, ArrayList<Product> p){
        super(context,0,p);
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        Product currentProduct = getItem(position);

        View searchView = convertView;
        if (searchView == null){
            searchView = LayoutInflater.from(getContext()).inflate(R.layout.activity_search,parent,false);
        }

        TextView proTitle  = (TextView) searchView.findViewById(R.id.product_title);
        proTitle.setText(currentProduct.getTitle());

        TextView proRat  = (TextView) searchView.findViewById(R.id.product_rating);
        proRat.setText(currentProduct.getRating());

        TextView proPrice  = (TextView) searchView.findViewById(R.id.product_price);
        proPrice.setText(currentProduct.getPrice());

        TextView proDesc  = (TextView) searchView.findViewById(R.id.product_description);
        proDesc.setText(currentProduct.getDescription());

        ImageView proImg  = (ImageView) searchView.findViewById(R.id.product_image);
        byte[] img = currentProduct.getImage();
        if (img == null){
            proImg.setImageResource(R.drawable.nopic);
        }
        else {
            Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length);
            proImg.setImageBitmap(Bitmap.createScaledBitmap(bmp, proImg.getWidth(),
                    proImg.getHeight(), false));
        }

        TextView proUser = (TextView) searchView.findViewById(R.id.product_user);
        proUser.setText(currentProduct.getUser());

        TextView proDate = (TextView) searchView.findViewById(R.id.product_date);
        proDate.setText(currentProduct.getPublishDate());

        return searchView;
    }
}
