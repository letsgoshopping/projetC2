package com.project.lgs.SupplierClasses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.project.lgs.AllSuppliersClasses.AllSuppliers;
import com.project.lgs.MainActivity;
import com.project.lgs.R;

import java.util.ArrayList;

public class SupplierFragment extends Fragment implements SupplierAdapter.SupplierDetailsListener, View.OnClickListener {

    ArrayList<Supplier> supplierList;

    public void setSupplierList (ArrayList<Supplier> sup){
        this.supplierList = sup;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.supplier_fragment, container, false);

        SupplierAdapter itemsAdapter = new SupplierAdapter(MainActivity.mcontext, supplierList,this);

        ListView listView = (ListView) view.findViewById(R.id.supplier_list);
        listView.setAdapter(itemsAdapter);

        view.findViewById(R.id.more_sup_button).setOnClickListener(this);

        return view;

    }

    @Override
    public void supplierDetailsListener(int position) {
        Supplier sup = supplierList.get(position);
        Intent intent = new Intent(MainActivity.mcontext, SupplierDetails.class);
        intent.putExtra("Supplier", sup);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.mcontext, AllSuppliers.class);
        startActivity(intent);
    }
}
