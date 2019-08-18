package com.project.lgs.CartClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.project.lgs.AllProductsClasses.AllProductsAdapter;
import com.project.lgs.Database.CartMgr;
import com.project.lgs.Database.ProductsMgr;
import com.project.lgs.MainActivity;
import com.project.lgs.R;

import org.bson.Document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
        }

        CartMgr cartMgr = new CartMgr(MainActivity.dbName, MainActivity.mongoClient);

        HashMap<String, String> cartIns = new HashMap<String, String>();
        cartIns.put("User", MainActivity.userLogin.getId());

        ArrayList<Cart> carts = cartMgr.findDocument(cartIns, new HashMap<String, Integer>(), 1);

        if (carts.size()>0) {

            Cart cart  = carts.get(0);

            Document productList  = cart.getProducts();

            ArrayList<Document> arrayList = new ArrayList<>();

            Set<String> s = productList.keySet();

            for ( String key :s){

                arrayList.add((Document) productList.get(key));
            }

            setContentView(R.layout.activity_user_profile);
            cartAdapter proAdapter = new cartAdapter(this, arrayList);
            ListView listView = (ListView)findViewById(R.id.cart_list);
            listView.setAdapter(proAdapter);

        }else{

            setContentView(R.layout.activity_no_results);
        }
    }
}
