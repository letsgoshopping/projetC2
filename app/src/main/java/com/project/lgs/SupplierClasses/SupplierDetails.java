package com.project.lgs.SupplierClasses;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.lgs.R;

public class SupplierDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_details);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
        }

        Runnable runnable = new Runnable() {

            public void run() {

                Supplier sup = (Supplier) getIntent().getSerializableExtra("Supplier");

                TextView textView = findViewById(R.id.supDetProBar);
                textView.setVisibility(View.GONE);

                TextView supName  = (TextView) findViewById(R.id.sup_det_name);
                supName.setText(sup.getName());

                TextView supDate  = (TextView) findViewById(R.id.sup_det_joinDate);
                supDate.setText(sup.getJoinDate());

                TextView supDesc  = (TextView) findViewById(R.id.sup_det_description);
                supDesc.setText(sup.getDescription());

                byte[] img = sup.getImage();
                ImageView supImg  = (ImageView) findViewById(R.id.sup_det_image);
                if (img == null){
                    supImg.setImageResource(R.drawable.nopic);
                }
                else {
                    Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length);
                    supImg.setImageBitmap(bmp);
                }

                TextView supEmail = (TextView) findViewById(R.id.sup_det_email);
                supEmail.setText(sup.getEmail());

                TextView suppPhone = (TextView) findViewById(R.id.sup_det_phnumber);
                suppPhone.setText(sup.getPhoneNumber());

            }
        };

        Handler handler =  new Handler();
        handler.postDelayed(runnable, 500);

    }
}
