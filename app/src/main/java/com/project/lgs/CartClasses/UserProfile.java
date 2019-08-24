package com.project.lgs.CartClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.project.lgs.Database.CartMgr;
import com.project.lgs.Database.OrdersMgr;
import com.project.lgs.Database.ProductsMgr;
import com.project.lgs.Database.UsersMgr;
import com.project.lgs.Login;
import com.project.lgs.MainActivity;
import com.project.lgs.OrdersClasses.Order;
import com.project.lgs.ProblemActivity;
import com.project.lgs.ProductClasses.Product;
import com.project.lgs.R;
import com.project.lgs.UsersClasses.User;

import org.bson.Document;
import org.bson.types.ObjectId;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
        }

        CartMgr cartMgr = new CartMgr(MainActivity.dbName, MainActivity.mongoClient);

        HashMap<String, String> cartIns = new HashMap<String, String>();
        cartIns.put("User", MainActivity.userLogin.getEmail());

        ArrayList<Cart> carts = cartMgr.findDocument(cartIns, new HashMap<String, Integer>(), 5000);

        if (carts.size()==1) {
            cart  = carts.get(0);

            Document productList  = cart.getProducts();

            ArrayList<Document> arrayList = new ArrayList<>();

            Set<String> s = productList.keySet();

            for ( String key :s){
                Document d = (Document)productList.get(key);
                d.append("rowId", key);
                arrayList.add(d);
                products.add(key);
            }

            setContentView(R.layout.activity_user_profile);
            cartAdapter proAdapter = new cartAdapter(this, arrayList,this);
            ListView listView = (ListView)findViewById(R.id.cart_list);
            listView.setAdapter(proAdapter);

            TextView total = (TextView) this.findViewById(R.id.cartTotal);
            total.setText(cart.getTotal() + "$");

        }else{

            setContentView(R.layout.activity_no_results);
        }
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
                        ProductsMgr productsMgr  = new ProductsMgr(MainActivity.dbName, MainActivity.mongoClient);

                        String s = "Invoice Number: " + cart.getInvoiceNumber() + "\n";
                        s = s + "Creation Date: " + cart.getDate() + "\n";
                        s = s + "Sending Date: " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + "\n";
                        s = s + "Email: " + cart.getUserId() + "\n";
                        s = s + "Total: " + cart.getTotal() + "$ \n\n";
                        s = s + "Products: " + "\n";

                        Document proList = cart.getProducts();
                        Set<String> list = proList.keySet();

                        for(String pro: list){

                            Document d  = (Document)proList.get(pro);
                            HashMap<String,ObjectId> proIns = new HashMap<>();
                            proIns.put("_id",new ObjectId((String)d.get("_id")));
                            ArrayList<Product> p = productsMgr.findDocumentById(proIns, new HashMap<String, Integer>(),1);

                            Log.d("cart", p.get(0).getId());
                            s = s + "Title: " + p.get(0).getTitle() + "\n";
                            s = s + "Code: " + p.get(0).getCode() + "\n";
                            s = s + "Supplier: " + p.get(0).getUser() + "\n";
                            s = s + "Price: " + p.get(0).getPrice().replace("$","") + "$ \n";
                            s = s + "Quantity: " + d.get("Qtity") + "\n\n";

                        }


                        OrdersMgr ordersMgr =  new OrdersMgr(MainActivity.dbName, MainActivity.mongoClient);
                        HashMap<String,String>orderDetails = new HashMap<>();
                        orderDetails.put("InvoiceNumber",cart.getInvoiceNumber());
                        orderDetails.put("Date", cart.getDate());
                        orderDetails.put("User", cart.getUserId());
                        orderDetails.put("Total", cart.getTotal());
                        orderDetails.put("Status","Pending");
                        ordersMgr.insertDocument(proList, orderDetails);

                        CartMgr cartMgr = new CartMgr(MainActivity.dbName, MainActivity.mongoClient);

                        cartMgr.deleteDocument(new ObjectId(cart.getId()));

                        finish();
                        Intent i = new Intent(UserProfile.this, UserProfile.class);
                        startActivity(i);

                        Toast toast = Toast.makeText(UserProfile.this, "Cart submitted", Toast.LENGTH_SHORT);
                        toast.show();

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }

            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setMessage("Submit cart?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

}
