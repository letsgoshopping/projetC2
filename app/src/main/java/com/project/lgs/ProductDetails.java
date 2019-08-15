package com.project.lgs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.lgs.ProductClasses.Product;

public class ProductDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
        }


        Product currentProduct = (Product) getIntent().getSerializableExtra("Product");

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

        TextView proDate = (TextView) this.findViewById(R.id.detail_date);
        proDate.setText(currentProduct.getPublishDate());
    }
}
