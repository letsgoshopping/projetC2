package com.project.lgs.SupplierClasses;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.project.lgs.MainActivity;
import com.project.lgs.R;

import java.util.ArrayList;

public class SupplierFragment extends Fragment {

    ArrayList<Supplier> supplierList;

    public void setSupplierList (ArrayList<Supplier> sup){
        this.supplierList = sup;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.supplier_fragment, container, false);

        SupplierAdapter itemsAdapter = new SupplierAdapter(MainActivity.mcontext, supplierList);

        ListView listView = (ListView) view.findViewById(R.id.supplier_list);
        listView.setAdapter(itemsAdapter);


        return view;

    }
}
