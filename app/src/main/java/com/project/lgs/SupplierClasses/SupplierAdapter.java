package com.project.lgs.SupplierClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.lgs.R;

import java.util.ArrayList;

public class SupplierAdapter extends ArrayAdapter<Supplier> {

    public SupplierAdapter(Context context, ArrayList<Supplier> Suppliers){
        super(context,0,Suppliers);
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        Supplier currentSupplier = getItem(position);

        View SupplierView = convertView;
        if (SupplierView == null){
            SupplierView = LayoutInflater.from(getContext()).inflate(R.layout.supplier_list,parent,false);
        }

        TextView SupplierName  = (TextView) SupplierView.findViewById(R.id.supplier_name);
        SupplierName.setText(currentSupplier.getName());

        TextView SupplierDesc  = (TextView) SupplierView.findViewById(R.id.supplier_description);
        SupplierDesc.setText(currentSupplier.getDescription());

        TextView SupplierNum  = (TextView) SupplierView.findViewById(R.id.supplier_number);
        SupplierNum.setText(currentSupplier.getPhoneNumber());

        TextView SupplierEmail  = (TextView) SupplierView.findViewById(R.id.supplier_email);
        SupplierEmail.setText(currentSupplier.getEmail());

        ImageView SupplierImg  = (ImageView) SupplierView.findViewById(R.id.supplier_image);
        SupplierImg.setImageResource(currentSupplier.getImageRessource());

        return SupplierView;
    }
}
