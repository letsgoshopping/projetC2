package com.project.lgs.CartClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.project.lgs.Database.CartMgr;
import com.project.lgs.Database.OrdersMgr;
import com.project.lgs.Database.ProductsMgr;
import com.project.lgs.Database.UsersMgr;
import com.project.lgs.Login;
import com.project.lgs.MainActivity;
import com.project.lgs.OrdersClasses.Order;
import com.project.lgs.OrdersClasses.OrderHistory;
import com.project.lgs.ProblemActivity;
import com.project.lgs.ProductClasses.Product;
import com.project.lgs.R;
import com.project.lgs.UsersClasses.User;
import com.project.lgs.UsersClasses.UserAddress;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class UserProfile extends AppCompatActivity implements cartAdapter.CartListener, DialogInterface.OnClickListener {

    ArrayList<String> products = new ArrayList<>();
    Cart cart;
    int curPos;
    static Context userProContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
        }

        userProContext = this;
        Runnable runnable = new Runnable() {

            public void run() {

                CartMgr cartMgr = new CartMgr(MainActivity.dbName, MainActivity.mongoClient);

                String userEmail = "-";

                if (MainActivity.isSupp == false && MainActivity.userLogin != null) {
                    userEmail = MainActivity.userLogin.getEmail();
                }

                if (MainActivity.isSupp == true && MainActivity.supplierLogin != null) {
                    userEmail = MainActivity.supplierLogin.getEmail();
                }

                HashMap<String, String> cartIns = new HashMap<String, String>();
                cartIns.put("User", userEmail);

                ArrayList<Cart> carts = cartMgr.findDocument(cartIns, new HashMap<String, Integer>(), 5000);

                if (carts.size() == 1) {
                    cart = carts.get(0);

                    Document productList = cart.getProducts();

                    ArrayList<Document> arrayList = new ArrayList<>();

                    Set<String> s = productList.keySet();

                    for (String key : s) {
                        Document d = (Document) productList.get(key);
                        d.append("rowId", key);
                        arrayList.add(d);
                        products.add(key);
                    }

                    TextView textView = findViewById(R.id.cartProBar);
                    textView.setVisibility(View.GONE);

                    ListView cartList = findViewById(R.id.cart_list);
                    cartList.setVisibility(View.VISIBLE);

                    cartAdapter proAdapter = new cartAdapter(UserProfile.userProContext, arrayList, UserProfile.this);
                    ListView listView = (ListView) findViewById(R.id.cart_list);
                    listView.setAdapter(proAdapter);

                    TextView total = (TextView) UserProfile.this.findViewById(R.id.cartTotal);
                    total.setText(cart.getTotal() + "$");

                }
            }
        };

            Handler handler =  new Handler();
            handler.postDelayed(runnable, 2000);
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {

        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                //getting product key to delete
                String pro = products.get(curPos);

                //getting product document to delete
                Document productList = cart.getProducts();
                Document qtitydoc = (Document) productList.get(pro);

                //getting ordered quantity of the deleted product
                int toDelQtity = Integer.parseInt((String) qtitydoc.get("Qtity"));

                //finding the product and get its price
                ProductsMgr productsMgr = new ProductsMgr(MainActivity.dbName, MainActivity.mongoClient);
                HashMap<String, ObjectId> proIns = new HashMap<>();
                proIns.put("_id", new ObjectId((String) qtitydoc.get("_id")));
                ArrayList<Product> p = productsMgr.findDocumentById(proIns, new HashMap<String, Integer>(), 1);
                int toDelPrice = Integer.parseInt(p.get(0).getPrice().replace("$", ""));
                int total = toDelPrice * toDelQtity;

                //removing the product from the list
                productList.remove(pro);

                //updating the cart's info
                CartMgr cartMgr = new CartMgr(MainActivity.dbName, MainActivity.mongoClient);
                //preparing the filter conditions
                HashMap<String, ObjectId> filter = new HashMap<>();
                filter.put("_id", new ObjectId(cart.getId()));

                //preparing to update values (list of products and total)
                Document upd = new Document();
                upd.append("Products", productList);

                HashMap<String, String> totalupd = new HashMap<>();
                int finalTotal = Integer.parseInt(cart.getTotal()) - total;
                totalupd.put("Total", Integer.toString(finalTotal));

                //updating the cart and reloading the screen
                cartMgr.updateDocumentMulti(upd, totalupd, filter);

                finish();
                Intent i = new Intent(this, UserProfile.class);
                startActivity(i);

                Toast toast = Toast.makeText(this, "Product deleted from cart", Toast.LENGTH_SHORT);
                toast.show();


                break;

            case DialogInterface.BUTTON_NEGATIVE:
                //No button clicked
                break;
        }

    }


    @Override
    public void cartListener(int position) {

        curPos = position;

        DialogInterface.OnClickListener dialogClickListener = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setMessage("Remove from cart?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logoutmenu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.logout) {

            Login.mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            Log.d("google", "sign out completed");
                        }
                    });


            Login.mGoogleSignInClient.revokeAccess()
                        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {

                                MainActivity.userLogin = null;
                                MainActivity.supplierLogin = null;
                                Intent i = new Intent(UserProfile.this, Login.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                UserProfile.this.startActivity(i);
                            }
                        });


        }

        return true;
    }

    public void submitOrder (View v){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        if(MainActivity.userLogin.getConName()!= null && !MainActivity.userLogin.getConName().trim().equals("") &&
                                MainActivity.userLogin.getPhoneCode()!= null && !MainActivity.userLogin.getPhoneCode().trim().equals("")&&
                                MainActivity.userLogin.getPhoneNum()!= null && !MainActivity.userLogin.getPhoneNum().trim().equals("")&&
                                MainActivity.userLogin.getCity()!= null && !MainActivity.userLogin.getCity().trim().equals("")&&
                                MainActivity.userLogin.getStreet()!= null && !MainActivity.userLogin.getStreet().trim().equals("") &&
                                MainActivity.userLogin.getFloor()!= null && !MainActivity.userLogin.getFloor().trim().equals("")) {

                            Document proList = cart.getProducts();
                            Document proListDoc = new Document();
                            proListDoc.append("Products", proList);

                            OrdersMgr ordersMgr = new OrdersMgr(MainActivity.dbName, MainActivity.mongoClient);
                            HashMap<String, String> orderDetails = new HashMap<>();
                            orderDetails.put("InvoiceNumber", cart.getInvoiceNumber());
                            orderDetails.put("Date", cart.getDate());
                            orderDetails.put("User", cart.getUserId());
                            orderDetails.put("Total", cart.getTotal());
                            orderDetails.put("Status", "Pending");
                            ordersMgr.insertDocument(proListDoc, orderDetails);

                            CartMgr cartMgr = new CartMgr(MainActivity.dbName, MainActivity.mongoClient);

                            cartMgr.deleteDocument(new ObjectId(cart.getId()));

                            finish();
                            Intent i = new Intent(UserProfile.this, UserProfile.class);
                            startActivity(i);

                            Toast toast = Toast.makeText(UserProfile.this, "Cart submitted", Toast.LENGTH_SHORT);
                            toast.show();
                        }else{

                            Intent i = new Intent(UserProfile.this, UserAddress.class);
                            startActivity(i);

                            Toast toast = Toast.makeText(UserProfile.this, "Please fill your address info", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }

            }
        };

        if(cart != null){
            if (cart.getProducts()!=null){
                if (cart.getProducts().size()>0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
                    builder.setMessage("Submit cart?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();

                }else{
                    Toast toast = Toast.makeText(UserProfile.this, "Empty Cart", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }else{
                Toast toast = Toast.makeText(UserProfile.this, "Empty Cart", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else{
            Toast toast = Toast.makeText(UserProfile.this, "Empty Cart", Toast.LENGTH_SHORT);
            toast.show();

        }


    }

    public void orderHistory (View v){
        Intent i = new Intent(this, OrderHistory.class);

        if(MainActivity.isSupp == true && MainActivity.supplierLogin != null){
            i.putExtra("User",MainActivity.supplierLogin.getEmail());
            startActivity(i);
        }
        if(MainActivity.isSupp == false && MainActivity.userLogin != null){
            i.putExtra("User",MainActivity.userLogin.getEmail());
            startActivity(i);
        }

    }

}
