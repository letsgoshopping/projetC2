package com.project.lgs.AdminClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.project.lgs.AdminClasses.AdminActivity;
import com.project.lgs.Database.CategoriesMgr;
import com.project.lgs.Database.SupplierMgr;
import com.project.lgs.R;

import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Supplier;

public class AddSupplier extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_supplier);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
        }
    }

    public void submitSupplier(View v){

        SupplierMgr supplierMgr = new SupplierMgr(AdminActivity.dbName, AdminActivity.mongoClient);

        EditText title = (EditText) findViewById(R.id.supEmailIns);
        String supplier = title.getText().toString();


        HashMap<String,String> catIns = new HashMap<>();
        catIns.put("Email",supplier);
        catIns.put("Name","");
        catIns.put("Description","");
        catIns.put("PhoneNumber","");
        catIns.put("JoinDate",new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        HashMap<String,byte[]> img = new HashMap<>();
        img.put("Image",null);


        supplierMgr.insertDocumentWPic(catIns,img);

        finish();
        startActivity(getIntent());
        Toast toast = Toast.makeText(this, "Supplier Inserted", Toast.LENGTH_SHORT);
        toast.show();

    }

    public void cancelSupplier(View v){
        finish();
        startActivity(getIntent());
    }
}
