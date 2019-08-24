package com.project.lgs.SearchClasses;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.lgs.ProductClasses.Product;
import com.project.lgs.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchAdapter extends ArrayAdapter<Product> implements View.OnClickListener{

    ProductDetailsListener productDetailsListener;
    HashMap<String,Integer> pos = new HashMap<>();

    public SearchAdapter(Context context, ArrayList<Product> p,ProductDetailsListener productDetailsListener){
        super(context,0,p);
        this.productDetailsListener = productDetailsListener;
    }



    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        Product currentProduct = getItem(position);
        pos.put(currentProduct.getId(),position);

        View searchView = convertView;
        if (searchView == null){
            searchView = LayoutInflater.from(getContext()).inflate(R.layout.product_list,parent,false);
        }

        TextView proId  = (TextView) searchView.findViewById(R.id.product_id);
        proId.setText(currentProduct.getId());

        TextView proTitle  = (TextView) searchView.findViewById(R.id.product_title);
        proTitle.setText(currentProduct.getTitle());

        TextView proRat  = (TextView) searchView.findViewById(R.id.product_rating);
        proRat.setText(currentProduct.getCode());

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

        searchView.setOnClickListener(this);

        return searchView;
    }

    @Override
    public void onClick(View v) {
        TextView proView = (TextView)v.findViewById(R.id.product_id);
        String proId = proView.getText().toString();
        productDetailsListener.ProductDetailsListener(pos.get(proId));
    }

    public interface ProductDetailsListener{
        void ProductDetailsListener (int position);
    }
}
