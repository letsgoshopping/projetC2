package com.project.lgs.OrdersClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.project.lgs.AdminClasses.AdminActivity;
import com.project.lgs.AllProductsClasses.AllProductsAdapter;
import com.project.lgs.CartClasses.Cart;
import com.project.lgs.CartClasses.UserProfile;
import com.project.lgs.CartClasses.cartAdapter;
import com.project.lgs.Database.CartMgr;
import com.project.lgs.Database.OrdersMgr;
import com.project.lgs.Database.ProductsMgr;
import com.project.lgs.Login;
import com.project.lgs.MainActivity;
import com.project.lgs.ProductClasses.Product;
import com.project.lgs.ProductDetails;
import com.project.lgs.R;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class OrderHistory extends AppCompatActivity implements OrderAdapter.OrderListener{

    ArrayList<Order> orders;
    Boolean isAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary)); //status bar or the time bar at the top
        }


        String user = (String) getIntent().getStringExtra("User");

        if(user != null){
            OrdersMgr ordersMgr= new OrdersMgr(MainActivity.dbName, MainActivity.mongoClient);
            HashMap<String, String> orderIns = new HashMap<String, String>();
            orderIns.put("User", user);

            HashMap<String, Integer> orderSort = new HashMap<String, Integer>();
            orderSort.put("Date", 1);

            orders = ordersMgr.findDocument(orderIns, orderSort, 200);

        }else{

            isAdmin = true;
            OrdersMgr ordersMgr= new OrdersMgr(AdminActivity.dbName, AdminActivity.mongoClient);
            HashMap<String, String> orderIns = new HashMap<String, String>();

            HashMap<String, Integer> orderSort = new HashMap<String, Integer>();
            orderSort.put("Date", 1);

            orders = ordersMgr.findDocument(orderIns, orderSort, 200);

        }

        if (orders.size()>0) {

            setContentView(R.layout.activity_order_history);
            OrderAdapter orderAdapter = new OrderAdapter(this, orders,this);
            ListView listView = (ListView)findViewById(R.id.order_list);
            listView.setAdapter(orderAdapter);

        }else{

            setContentView(R.layout.activity_no_results);
        }
    }

    @Override
    public void orderListener(int position) {
        Order order = orders.get(position);
        Intent i = new Intent(this, OrderDetails.class);
        i.putExtra("Order", order);
        i.putExtra("isAdmin", isAdmin);
        startActivityForResult(i,1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if(resultCode == RESULT_OK){

                Toast toast = Toast.makeText(this, "Order Updated", Toast.LENGTH_SHORT);
                toast.show();
                finish();
                startActivity(getIntent());
            }
        }
    }

}
