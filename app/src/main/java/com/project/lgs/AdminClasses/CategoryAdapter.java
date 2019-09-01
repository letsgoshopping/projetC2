package com.project.lgs.AdminClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.lgs.CartClasses.cartAdapter;
import com.project.lgs.CategoryClasses.Category;
import com.project.lgs.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoryAdapter extends ArrayAdapter<Category> implements View.OnClickListener{

    CategoryListener categoryListener;
    HashMap<String,Integer> pos = new HashMap<>();

    public CategoryAdapter(Context context, ArrayList<Category> d, CategoryListener categoryListener){
        super(context,0,d);
        this.categoryListener = categoryListener;
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        Category selectedPCat = getItem(position);

        pos.put((String)selectedPCat.getCatId(),position);

        View searchView = convertView;
        if (searchView == null){
            searchView = LayoutInflater.from(getContext()).inflate(R.layout.categoryitem,parent,false);
        }

        TextView catid = (TextView)searchView.findViewById(R.id.catId);
        catid.setText(selectedPCat.getCatId());

        TextView catItem = (TextView)searchView.findViewById(R.id.categoryItem);
        catItem.setText(selectedPCat.getCatName());

        catItem.setOnClickListener(this);

        return searchView;
    }

    @Override
    public void onClick(View v) {

        LinearLayout proView = (LinearLayout)v.getParent();
        TextView textView = (TextView)proView.findViewById(R.id.catId);
        String proId = textView.getText().toString();
        categoryListener.categoryListener(pos.get(proId));
    }

    public interface CategoryListener{
        void categoryListener(int position);
    }
}
