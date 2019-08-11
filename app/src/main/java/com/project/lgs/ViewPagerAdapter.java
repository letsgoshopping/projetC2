package com.project.lgs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mongodb.lang.Nullable;
import com.project.lgs.CategoryClasses.Category;
import com.project.lgs.Database.CategoriesMgr;
import com.project.lgs.ProductClasses.ProductFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Category> catList;

    public ViewPagerAdapter(FragmentManager fm) {

        super(fm);

        CategoriesMgr catMgr = new CategoriesMgr(MainActivity.dbName, MainActivity.mongoClient);
        HashMap<String, String> catIns = new HashMap<String, String>();
        catIns.put("Display","1");
        HashMap<String, Integer> catSort = new HashMap<String, Integer>();
        catSort.put("Name", 1);

        catList = catMgr.findDocument(catIns,catSort);
    }


    @Override
    public Fragment getItem(int i) {

        Bundle bundle  = new Bundle();

        if (i >=0 && i< catList.size()) {

            ProductFragment fragment = new ProductFragment();
            fragment.setCategoryName(catList.get(i).getCatName());
            fragment.setArguments(bundle);
            i+=1;
            return fragment;

        }else{
            DemoFragment fragment = new DemoFragment();
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
            title = "Users";
        }

        i +=1;

        return title;
    }
}
