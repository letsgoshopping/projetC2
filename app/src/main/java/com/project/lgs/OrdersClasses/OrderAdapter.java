package com.project.lgs.OrdersClasses;

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

import com.project.lgs.AllProductsClasses.AllProductsAdapter;
import com.project.lgs.Database.SupplierMgr;
import com.project.lgs.MainActivity;
import com.project.lgs.ProductClasses.Product;
import com.project.lgs.R;
import com.project.lgs.SupplierClasses.Supplier;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderAdapter extends ArrayAdapter<Order> implements View.OnClickListener{

    OrderAdapter.OrderListener orderListener;
    HashMap<String,Integer> pos = new HashMap<>();

    public OrderAdapter(Context context, ArrayList<Order> p, OrderAdapter.OrderListener orderListener){
        super(context,0,p);
        this.orderListener = orderListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Order currentOrder = getItem(position);
        pos.put(currentOrder.getId(),position);

        View searchView = convertView;
        if (searchView == null){
            searchView = LayoutInflater.from(getContext()).inflate(R.layout.order_list,parent,false);
        }


        TextView proId  = (TextView) searchView.findViewById(R.id.order_inv_rowId);
        proId.setText(currentOrder.getId());

        TextView proTitle  = (TextView) searchView.findViewById(R.id.order_inv_name);
        proTitle.setText(currentOrder.getInvoiceNumber());

        TextView proRat  = (TextView) searchView.findViewById(R.id.order_inv_date);
        proRat.setText(currentOrder.getDate());

        TextView proPrice  = (TextView) searchView.findViewById(R.id.order_user);
        proPrice.setText(currentOrder.getUserId());

        TextView proDesc  = (TextView) searchView.findViewById(R.id.order_status);
        proDesc.setText(currentOrder.getStatus());


        searchView.setOnClickListener(this);

        return searchView;
    }

    @Override
    public void onClick(View v) {
        TextView invView = (TextView)v.findViewById(R.id.order_inv_rowId);
        String invId = invView.getText().toString();
        orderListener.orderListener(pos.get(invId));
    }

    public interface OrderListener{
        void orderListener(int position);
    }
}

