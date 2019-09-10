package com.project.lgs.OrdersClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.project.lgs.AdminClasses.AdminActivity;
import com.project.lgs.AdminClasses.Email;
import com.project.lgs.CartClasses.Cart;
import com.project.lgs.CartClasses.UserProfile;
import com.project.lgs.CartClasses.cartAdapter;
import com.project.lgs.CategoryClasses.Category;
import com.project.lgs.Database.CartMgr;
import com.project.lgs.Database.OrdersMgr;
import com.project.lgs.Database.ProductsMgr;
import com.project.lgs.Database.SupplierMgr;
import com.project.lgs.Login;
import com.project.lgs.MainActivity;
import com.project.lgs.ProductClasses.Product;
import com.project.lgs.R;
import com.project.lgs.SupplierClasses.Supplier;
import com.project.lgs.UsersClasses.User;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class OrderDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Order order;
    Boolean isAdmin;
    String orderStatus;
    static Context orderDetContext;
    Email email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
        }

        orderDetContext = this;
        Runnable runnable = new Runnable() {

            public void run() {

                order = (Order) getIntent().getSerializableExtra("Order");
                isAdmin = (Boolean)getIntent().getBooleanExtra("isAdmin",false);
                email = new Email(order, isAdmin);

                if (isAdmin ==  true){
                    Button sendOrder = (Button) findViewById(R.id.order_sts);
                    sendOrder.setVisibility(View.VISIBLE);

                    Button orderemail = (Button)findViewById(R.id.order_email);
                    orderemail.setVisibility(View.VISIBLE);
                }


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

                TextView textView = findViewById(R.id.orderProBar);
                textView.setVisibility(View.GONE);

                ListView cartList = findViewById(R.id.order_detail_list);
                cartList.setVisibility(View.VISIBLE);

                OrderDetailsAdapter orderDetailsAdapter = new OrderDetailsAdapter(OrderDetails.orderDetContext, arrayList,isAdmin,email);
                ListView listView = (ListView)findViewById(R.id.order_detail_list);
                listView.setAdapter(orderDetailsAdapter);

                TextView total = (TextView) findViewById(R.id.orderDetailTotal);
                total.setText(order.getTotal()==null?"0":order.getTotal() + "$");

                TextView invNum = (TextView) findViewById(R.id.inv_number);
                invNum.setText(order.getInvoiceNumber());

                TextView invDate = (TextView) findViewById(R.id.inv_date);
                invDate.setText(order.getDate());

                orderStatus = order.getStatus();

                Spinner spinner = (Spinner) findViewById(R.id.inv_status);
                spinner.setOnItemSelectedListener(OrderDetails.this);

                ArrayList<String> list = new ArrayList<String>();
                list.add("Pending");
                list.add("Submitted");
                list.add("Cancelled");
                list.add("Delivered");
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(OrderDetails.orderDetContext,
                        android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(dataAdapter);
                if (orderStatus!= null) {
                    spinner.setSelection(dataAdapter.getPosition(orderStatus));
                }

            }
        };

        Handler handler =  new Handler();
        handler.postDelayed(runnable, 2000);
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

    public void SendOrder (View v){

        Order ordr = email.getOrder();
        ArrayList<Product> proList = email.getProducts();
        User user = email.getUser();

        String s = "User Email: " + user.getEmail()+ "\n";
        s = s + "Contract Name: " + user.getConName() + "\n";
        s = s + "Phone Number: " + user.getPhoneCode() + user.getPhoneNum() +  "\n";
        s = s + "Address: " + user.getCounty() + "," + user.getCity() +  "\n";
        s = s + "     " + user.getStreet() + user.getFloor() + "\n\n";

        s = s+ "Invoice Number: " + ordr.getInvoiceNumber() + "\n";
        s = s + "Creation Date: " + ordr.getDate() + "\n";
        s = s + "Sending Date: " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + "\n";
        s = s + "Email: " + ordr.getUserId() + "\n";
        s = s + "Total: " + ordr.getTotal() + "$ \n\n";
        s = s + "Products: " + "\n";


        String [] emails = new String[proList.size()];
        int i = 0;

        for(Product pro: proList){

            String supName = "No email";
            if (pro.getSupEmail() != null){
                    supName = pro.getSupEmail();
            }

            s = s + "Title: " + pro.getTitle() + "\n";
            s = s + "Code: " + pro.getCode() + "\n";
            s = s + "Supplier: " + supName + "\n";
            s = s + "Price: " + pro.getPrice().replace("$","") + "$ \n";
            s = s + "Quantity: " + pro.getOrderedQtity() + "\n\n";

            if(!supName.equals("No email")){
                emails[i] = supName;
                i++;
            }

        }

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL  , emails);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Order Num: " + order.getInvoiceNumber());
        emailIntent.putExtra(Intent.EXTRA_TEXT   , s);
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }

    }
}