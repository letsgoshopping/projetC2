package com.project.lgs;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.lgs.CartClasses.Cart;

import com.project.lgs.Database.CartMgr;
import com.project.lgs.ProductClasses.Product;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.Map;

public class ProductDetails extends AppCompatActivity {

    int qtity = 0;
    Product curPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
        }


        Product currentProduct = (Product) getIntent().getSerializableExtra("Product");
        curPro = currentProduct;

        TextView proTitle  = (TextView) this.findViewById(R.id.detail_title);
        proTitle.setText(currentProduct.getTitle());

        TextView proRat  = (TextView) this.findViewById(R.id.detail_rating);
        proRat.setText(currentProduct.getRating());

        TextView proPrice  = (TextView) this.findViewById(R.id.detail_price);
        proPrice.setText(currentProduct.getPrice());

        TextView proDesc  = (TextView) this.findViewById(R.id.detail_description);
        proDesc.setText(currentProduct.getDescription());

        byte[] img = currentProduct.getImage();
        ImageView proImg  = (ImageView) this.findViewById(R.id.detail_image);
        if (img == null){
            proImg.setImageResource(R.drawable.nopic);
        }
        else {
            Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length);
            proImg.setImageBitmap(Bitmap.createScaledBitmap(bmp, proImg.getWidth(),
                    proImg.getHeight(), false));
        }

        TextView proUser = (TextView) this.findViewById(R.id.detail_user);
        proUser.setText(currentProduct.getUser());

        if(MainActivity.isSupp == false && MainActivity.userLogin != null){

           this.findViewById(R.id.cartLayout).setVisibility(View.VISIBLE);
           this.findViewById(R.id.addToCart).setVisibility(View.VISIBLE);
        }

        TextView proDate = (TextView) this.findViewById(R.id.detail_date);
        proDate.setText(currentProduct.getPublishDate());


    }

    public void cartAdd(View view){

        qtity +=1;
        TextView t = (TextView)this.findViewById(R.id.cartNumber);
        t.setText(Integer.toString(qtity));

    }

    public void cartMinus(View view){

        if (qtity >0){ qtity -=1;}
        TextView t = (TextView)this.findViewById(R.id.cartNumber);
        t.setText(Integer.toString(qtity));

    }

    public void addToCart(View view){

        if(MainActivity.userLogin != null && qtity>0) {

            CartMgr cartMgr = new CartMgr(MainActivity.dbName, MainActivity.mongoClient);

            HashMap<String, String> cartIns = new HashMap<>();
            cartIns.put("User", MainActivity.userLogin.getId());
            HashMap<String, Integer> cartSort = new HashMap<>();
            cartSort.put("User",1);

            ArrayList<Cart> cart = cartMgr.findDocument(cartIns, cartSort, 1);

            if (cart.size() > 0) {

                Cart c = cart.get(0);
                Document d = c.getProducts();

                Document h1 = new Document();
                h1.append("_id",curPro.getId());
                h1.append("Qtity",Integer.toString(qtity));

                int proNum = d.size()+1;

                d.append("product" + proNum, h1);

                Document upd = new Document();
                upd.append("Products",d);

                HashMap<String, ObjectId>filter = new HashMap<>();
                filter.put("_id",new ObjectId(c.getId()));


                cartMgr.updateDocumentMulti(upd,new HashMap<String, String>(),filter);

            } else {

                String invoiceNum = MainActivity.userLogin.getId() + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

                HashMap<String, String> values2 = new HashMap<>();
                values2.put("InvoiceNumber",invoiceNum);
                values2.put("Date",new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
                values2.put("User", MainActivity.userLogin.getId());

                Document productsIns = new Document();

                Document h1 = new Document();
                h1.append("_id",curPro.getId());
                h1.append("Qtity",Integer.toString(qtity));

                productsIns.append("product1", h1);

                Document values = new Document();
                values.append("Products",productsIns);

                cartMgr.insertDocument(values,values2);

            }

            Toast toast = Toast.makeText(this, "Product added to cart", Toast.LENGTH_SHORT);
            toast.show();

        }
    }
}
