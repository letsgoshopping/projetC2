package com.project.lgs.AllSuppliersClasses;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.lgs.R;
import com.project.lgs.SupplierClasses.Supplier;

import java.util.ArrayList;
import java.util.HashMap;

public class AllSuppliersAdapter extends ArrayAdapter<Supplier> implements View.OnClickListener{

    SupplierDetailsListener supplierDetailsListener;
    HashMap<String,Integer> pos = new HashMap<>();

    public AllSuppliersAdapter(Context context, ArrayList<Supplier> p, SupplierDetailsListener supplierDetailsListener){
        super(context,0,p);
        this.supplierDetailsListener = supplierDetailsListener;
    }



    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        Supplier currentSupplier = getItem(position);
        pos.put(currentSupplier.getId(),position);

        View SupplierView = convertView;
        if (SupplierView == null){
            SupplierView = LayoutInflater.from(getContext()).inflate(R.layout.supplier_list,parent,false);
        }

        TextView proId  = (TextView) SupplierView.findViewById(R.id.supplier_id);
        proId.setText(currentSupplier.getId());

        TextView SupplierName  = (TextView) SupplierView.findViewById(R.id.supplier_name);
        SupplierName.setText(currentSupplier.getName());

        TextView SupplierDesc  = (TextView) SupplierView.findViewById(R.id.supplier_description);
        SupplierDesc.setText(currentSupplier.getDescription());

        TextView SupplierNum  = (TextView) SupplierView.findViewById(R.id.supplier_number);
        SupplierNum.setText(currentSupplier.getPhoneNumber());

        TextView SupplierEmail  = (TextView) SupplierView.findViewById(R.id.supplier_email);
        SupplierEmail.setText(currentSupplier.getEmail());

        ImageView SupplierImg  = (ImageView) SupplierView.findViewById(R.id.supplier_image);
        byte[] img = currentSupplier.getImage();
        if (img == null){
            SupplierImg.setImageResource(R.drawable.nopic);
        }
        else {
            Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length);
            SupplierImg.setImageBitmap(Bitmap.createScaledBitmap(bmp, SupplierImg.getWidth(),
                    SupplierImg.getHeight(), false));
        }

        SupplierView.setOnClickListener(this);

        return SupplierView;
    }

    @Override
    public void onClick(View v) {
        TextView proView = (TextView)v.findViewById(R.id.supplier_id);
        String proId = proView.getText().toString();
        supplierDetailsListener.supplierDetailsListener(pos.get(proId));
    }

    public interface SupplierDetailsListener{
        void supplierDetailsListener(int position);
    }
}
