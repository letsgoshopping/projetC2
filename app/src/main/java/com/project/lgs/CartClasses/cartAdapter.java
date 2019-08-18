package com.project.lgs.CartClasses;

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

import com.project.lgs.Database.ProductsMgr;
import com.project.lgs.MainActivity;
import com.project.lgs.ProductClasses.Product;
import com.project.lgs.R;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;

public class cartAdapter extends ArrayAdapter<Document> {


    public cartAdapter(Context context, ArrayList<Document> d){
        super(context,0,d);
    }



    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        Document selectedProduct = getItem(position);

        View searchView = convertView;
        if (searchView == null){
            searchView = LayoutInflater.from(getContext()).inflate(R.layout.cart_list,parent,false);
        }

        ProductsMgr productsMgr = new ProductsMgr(MainActivity.dbName,MainActivity.mongoClient);

        HashMap<String, ObjectId> proSearch = new HashMap<>();

        proSearch.put("_id",new ObjectId((String)selectedProduct.get("_id")));

        ArrayList<Product> p = productsMgr.findDocumentById(proSearch,new HashMap<String, Integer>(),1);
        Product currentProduct = p.get(0);

        TextView proTitle  = (TextView) searchView.findViewById(R.id.cart_pr_name);
        proTitle.setText(currentProduct.getTitle());

        TextView proPrice  = (TextView) searchView.findViewById(R.id.cart_pr_price);
        proPrice.setText(currentProduct.getPrice());

        TextView proQtity  = (TextView) searchView.findViewById(R.id.cart_pr_qtity);
        proQtity.setText((String)selectedProduct.get("Qtity"));

        TextView proTotal  = (TextView) searchView.findViewById(R.id.cart_pr_total);
        String s  = currentProduct.getPrice().replace("$","");
        int price  = Integer.parseInt(s);
        int qtity = Integer.parseInt((String)selectedProduct.get("Qtity"));
        int total = price * qtity;
        proTotal.setText(Integer.toString(total) + "$");


        return searchView;
    }


}
