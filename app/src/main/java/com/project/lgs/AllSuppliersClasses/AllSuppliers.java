package com.project.lgs.AllSuppliersClasses;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;

import com.project.lgs.Database.SupplierMgr;
import com.project.lgs.MainActivity;
import com.project.lgs.R;
import com.project.lgs.SupplierClasses.Supplier;
import com.project.lgs.SupplierClasses.SupplierDetails;

import java.util.ArrayList;
import java.util.HashMap;

public class AllSuppliers extends AppCompatActivity implements AllSuppliersAdapter.SupplierDetailsListener {

    ArrayList<Supplier> suppliers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary)); //status bar or the time bar at the top
        }


        SupplierMgr supplierMgr = new SupplierMgr(MainActivity.dbName, MainActivity.mongoClient);

        HashMap<String, String> prodIns = new HashMap<String, String>();

        HashMap<String, Integer> prodSort = new HashMap<String, Integer>();
        prodSort.put("Name", 1);

        suppliers = supplierMgr.findDocument(prodIns, prodSort, 500);

        if (suppliers.size() > 0) {

            setContentView(R.layout.activity_all_suppliers);
            AllSuppliersAdapter proAdapter = new AllSuppliersAdapter(this, suppliers, this);
            ListView listView = (ListView) findViewById(R.id.allsup_list);
            listView.setAdapter(proAdapter);

        } else {

            setContentView(R.layout.activity_no_results);
        }
    }

    @Override
    public void supplierDetailsListener(int position) {
        Supplier sup = suppliers.get(position);
        Intent i = new Intent(this, SupplierDetails.class);
        i.putExtra("Supplier", sup);
        startActivity(i);
    }

}
