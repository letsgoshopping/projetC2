package com.project.lgs.SupplierClasses;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.lgs.CartClasses.cartAdapter;
import com.project.lgs.Database.ProductsMgr;
import com.project.lgs.Database.SupplierMgr;
import com.project.lgs.MainActivity;
import com.project.lgs.ProductClasses.Product;
import com.project.lgs.R;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductListAdapter extends ArrayAdapter<Product> implements View.OnClickListener{

    ProductListListener productListListener;
    HashMap<String,Integer> pos = new HashMap<>();

    public ProductListAdapter(Context context, ArrayList<Product> d, ProductListAdapter.ProductListListener productListListener){
        super(context,0,d);
        this.productListListener = productListListener;
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
        proPrice.setText(currentProduct.getPrice() + "$");

        TextView proDesc  = (TextView) searchView.findViewById(R.id.product_description);
        proDesc.setText(currentProduct.getDescription());

        ImageView proImg  = (ImageView) searchView.findViewById(R.id.product_image);
        byte[] img = currentProduct.getImage();
        if (img == null){
            proImg.setImageResource(R.drawable.nopic);
        }
        else {
            Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length);
            proImg.setImageBitmap(bmp);
        }

        TextView proUser = (TextView) searchView.findViewById(R.id.product_user);
        proUser.setText(MainActivity.supplierLogin.getName());

        TextView proDate = (TextView) searchView.findViewById(R.id.product_date);
        proDate.setText(currentProduct.getPublishDate());

        searchView.setOnClickListener(this);

        return searchView;
    }

    @Override
    public void onClick(View v) {

        TextView proView = (TextView)v.findViewById(R.id.product_id);
        String proId = proView.getText().toString();
        productListListener.productListListener(pos.get(proId));
    }

    public interface ProductListListener{
        void productListListener(int position);
    }
}
