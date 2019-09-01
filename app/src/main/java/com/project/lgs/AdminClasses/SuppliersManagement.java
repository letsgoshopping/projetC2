package com.project.lgs.AdminClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.project.lgs.Database.CategoriesMgr;
import com.project.lgs.Database.SupplierMgr;
import com.project.lgs.R;
import com.project.lgs.SupplierClasses.Supplier;

import java.util.ArrayList;
import java.util.HashMap;

public class SuppliersManagement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppliers_management);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
        }

        SupplierMgr supplierMgr = new SupplierMgr(AdminActivity.dbName, AdminActivity.mongoClient);
        ArrayList<Supplier> supp = supplierMgr.findDocument(new HashMap<String, String>(), new HashMap<String, Integer>(),100);

        ArrayAdapter<Supplier> arrayAdapter = new ArrayAdapter<>(this,R.layout.supplierlayout,supp);
        ListView listView = (ListView)findViewById(R.id.supplier_list);
        listView.setAdapter(arrayAdapter);
    }

    public void addSupplier (View v){

        Intent i = new Intent(this, AddCat.class);
        this.startActivity(i);

    }
}
