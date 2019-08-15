package com.project.lgs.ProductClasses;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.lgs.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductAdapter extends ArrayAdapter<Product> implements View.OnClickListener{

    ProductDetailsListener productDetailsListener;
    HashMap<String,Integer> pos = new HashMap<>();

    public ProductAdapter(Context context, ArrayList<Product> products, ProductDetailsListener productDetailsListener){
        super(context,0,products);
        this.productDetailsListener = productDetailsListener;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        Product currentProduct = getItem(position);
        pos.put(currentProduct.getId(),position);

        View productView = convertView;
        if (productView == null){
            productView = LayoutInflater.from(getContext()).inflate(R.layout.product_list,parent,false);
        }

        TextView proId  = (TextView) productView.findViewById(R.id.product_id);
        proId.setText(currentProduct.getId());

        TextView proTitle  = (TextView) productView.findViewById(R.id.product_title);
        proTitle.setText(currentProduct.getTitle());

        TextView proRat  = (TextView) productView.findViewById(R.id.product_rating);
        proRat.setText(currentProduct.getRating());

        TextView proPrice  = (TextView) productView.findViewById(R.id.product_price);
        proPrice.setText(currentProduct.getPrice());

        TextView proDesc  = (TextView) productView.findViewById(R.id.product_description);
        proDesc.setText(currentProduct.getDescription());

        ImageView proImg  = (ImageView) productView.findViewById(R.id.product_image);
        byte[] img = currentProduct.getImage();
        if (img == null){
            proImg.setImageResource(R.drawable.nopic);
        }
        else {
            Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length);
            proImg.setImageBitmap(Bitmap.createScaledBitmap(bmp, proImg.getWidth(),
                    proImg.getHeight(), false));
        }

        TextView proUser = (TextView) productView.findViewById(R.id.product_user);
        proUser.setText(currentProduct.getUser());

        TextView proDate = (TextView) productView.findViewById(R.id.product_date);
        proDate.setText(currentProduct.getPublishDate());

        productView.setOnClickListener(this);

        return productView;
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
