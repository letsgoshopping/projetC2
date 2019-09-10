package com.project.lgs.AdminClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.project.lgs.Database.CategoriesMgr;
import com.project.lgs.Database.SupplierMgr;
import com.project.lgs.R;
import com.project.lgs.SupplierClasses.Supplier;

import java.util.ArrayList;
import java.util.HashMap;

public class SuppliersManagement extends AppCompatActivity {

    static Context suppContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppliers_management);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
        }

        suppContext = this;

        Runnable runnable = new Runnable() {

            public void run() {

                SupplierMgr supplierMgr = new SupplierMgr(AdminActivity.dbName, AdminActivity.mongoClient);
                ArrayList<Supplier> supp = supplierMgr.findDocument(new HashMap<String, String>(), new HashMap<String, Integer>(),100);

                TextView textView = findViewById(R.id.suppMgrProBar);
                textView.setVisibility(View.GONE);

                ArrayAdapter<Supplier> arrayAdapter = new ArrayAdapter<Supplier>(SuppliersManagement.suppContext,R.layout.supplierlayout,supp);
                ListView listView = (ListView)findViewById(R.id.supplier_list);
                listView.setAdapter(arrayAdapter);
            }
        };

        Handler handler =  new Handler();
        handler.postDelayed(runnable, 250);
    }

    public void addSupplier (View v){

        Intent i = new Intent(this, AddSupplier.class);
        this.startActivity(i);

    }
}
