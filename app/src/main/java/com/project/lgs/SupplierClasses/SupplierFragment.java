package com.project.lgs.SupplierClasses;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.project.lgs.Database.SupplierMgr;
import com.project.lgs.MainActivity;
import com.project.lgs.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SupplierFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.supplier_fragment, container, false);
        SupplierMgr supplierMgr = new SupplierMgr(MainActivity.dbName, MainActivity.mongoClient);

        HashMap<String, String> userIns = new HashMap<String, String>();

        HashMap<String, Integer> userSort = new HashMap<String, Integer>();
        userSort.put("JoinDate", 1);

        ArrayList<Supplier> suppliers = supplierMgr.findDocument(userIns, userSort, 500);
        SupplierAdapter itemsAdapter = new SupplierAdapter(MainActivity.mcontext, suppliers);

        ListView listView = (ListView) view.findViewById(R.id.supplier_list);
        listView.setAdapter(itemsAdapter);


        return view;

    }
}
