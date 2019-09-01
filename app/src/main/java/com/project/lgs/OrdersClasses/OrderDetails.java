package com.project.lgs.OrdersClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.project.lgs.AdminClasses.AdminActivity;
import com.project.lgs.CartClasses.Cart;
import com.project.lgs.CartClasses.UserProfile;
import com.project.lgs.CartClasses.cartAdapter;
import com.project.lgs.CategoryClasses.Category;
import com.project.lgs.Database.CartMgr;
import com.project.lgs.Database.OrdersMgr;
import com.project.lgs.Database.ProductsMgr;
import com.project.lgs.Login;
import com.project.lgs.MainActivity;
import com.project.lgs.ProductClasses.Product;
import com.project.lgs.R;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class OrderDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Order order;
    Boolean isAdmin;
    String orderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
        }


        order = (Order) getIntent().getSerializableExtra("Order");
        isAdmin = (Boolean)getIntent().getBooleanExtra("isAdmin",false);


        Document productList  = order.getProducts();
        ArrayList<Document> arrayList = new ArrayList<>();

        if (productList != null){

            Set<String> s = productList.keySet();

            for ( String key :s){
                Document d = (Document)productList.get(key);
                d.append("rowId", key);
                arrayList.add(d);
            }
        }

        OrderDetailsAdapter orderDetailsAdapter = new OrderDetailsAdapter(this, arrayList,isAdmin);
        ListView listView = (ListView)findViewById(R.id.order_detail_list);
        listView.setAdapter(orderDetailsAdapter);

        TextView total = (TextView) this.findViewById(R.id.orderDetailTotal);
        total.setText(order.getTotal()==null?"0":order.getTotal() + "$");

        TextView invNum = (TextView) this.findViewById(R.id.inv_number);
        invNum.setText(order.getInvoiceNumber());

        TextView invDate = (TextView) this.findViewById(R.id.inv_date);
        invDate.setText(order.getDate());

        orderStatus = order.getStatus();

        Spinner spinner = (Spinner) this.findViewById(R.id.inv_status);
        spinner.setOnItemSelectedListener(this);

        ArrayList<String> list = new ArrayList<String>();
        list.add("Pending");
        list.add("Submitted");
        list.add("Cancelled");
        list.add("Delivered");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
        if (orderStatus!= null) {
            spinner.setSelection(dataAdapter.getPosition(orderStatus));
        }

    }



    public void submitOrder (View v){

        OrdersMgr ordersMgr;

        if(isAdmin == true){
            ordersMgr = new OrdersMgr(AdminActivity.dbName, AdminActivity.mongoClient);
        }else{
            ordersMgr= new OrdersMgr(MainActivity.dbName, MainActivity.mongoClient);
        }

        HashMap<String,String> ordUpd = new HashMap<>();
        ordUpd.put("Status",orderStatus);

        HashMap<String,ObjectId> filter = new HashMap<>();
        filter.put("_id",new ObjectId(order.getId()));

        ordersMgr.updateDocument(ordUpd,filter);

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",1);
        setResult(RESULT_OK,returnIntent);
        finish();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        orderStatus = parent.getItemAtPosition(position).toString();
        ((TextView) parent.getChildAt(0)).setTextColor(Color.DKGRAY);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
