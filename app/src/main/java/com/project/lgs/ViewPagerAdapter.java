package com.project.lgs;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mongodb.lang.Nullable;
import com.project.lgs.CategoryClasses.Category;
import com.project.lgs.Database.CategoriesMgr;
import com.project.lgs.Database.ProductsMgr;
import com.project.lgs.Database.SupplierMgr;
import com.project.lgs.ProductClasses.Product;
import com.project.lgs.ProductClasses.ProductFragment;
import com.project.lgs.SupplierClasses.Supplier;
import com.project.lgs.SupplierClasses.SupplierFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Category> catList;
    ArrayList<ProductFragment> productList = new ArrayList<ProductFragment>();
    ArrayList<Supplier> suppliers;

    public ViewPagerAdapter(FragmentManager fm) {

        super(fm);

        CategoriesMgr catMgr = new CategoriesMgr(MainActivity.dbName, MainActivity.mongoClient);
        HashMap<String, String> catIns = new HashMap<String, String>();
        catIns.put("Display","1");
        HashMap<String, Integer> catSort = new HashMap<String, Integer>();
        catSort.put("Name", 1);

        catList = catMgr.findDocument(catIns,catSort);

        for (int i=0; i<catList.size(); i++){
            ProductsMgr productMgr = new ProductsMgr(MainActivity.dbName, MainActivity.mongoClient);

            HashMap<String, String> prodIns = new HashMap<String, String>();
            prodIns.put("Category", catList.get(i).getCatId());

            HashMap<String, Integer> prodSort = new HashMap<String, Integer>();
            prodSort.put("PDate", 1);

            ArrayList<Product> products = productMgr.findDocument(prodIns, prodSort, 5);

            ProductFragment fragment = new ProductFragment();
            fragment.setCategoryName(catList.get(i).getCatId());
            fragment.setProductList(products);
            productList.add(fragment);
        }

        SupplierMgr supplierMgr = new SupplierMgr(MainActivity.dbName, MainActivity.mongoClient);

        HashMap<String, String> userIns = new HashMap<String, String>();

        HashMap<String, Integer> userSort = new HashMap<String, Integer>();
        userSort.put("JoinDate", 1);

        suppliers = supplierMgr.findDocument(userIns, userSort, 5);
    }


    @Override
    public Fragment getItem(int i) {

        Bundle bundle  = new Bundle();

        if (i == catList.size()) {
            SupplierFragment fragment = new SupplierFragment();
            fragment.setSupplierList(suppliers);
            fragment.setArguments(bundle);
            i+=1;
            return fragment;

        }else{
            ProductFragment fragment = productList.get(i);
            fragment.setArguments(bundle);
            i+=1;
            return fragment;
        }
    }

    @Override
    public int getCount() {
        return catList.size()+1;
    }

    @Override
    @Nullable
    public String getPageTitle (int i){

        String title;

        if (i >=0 && i< catList.size()) {

            title = catList.get(i).getCatName();

        }else{
            title = "Suppliers";
        }

        i +=1;

        return title;
    }
}
