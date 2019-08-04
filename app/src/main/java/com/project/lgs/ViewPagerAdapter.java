package com.project.lgs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mongodb.lang.Nullable;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        Bundle bundle  = new Bundle();

        if (i == 0) {
            DemoFragment fragment = new DemoFragment();
            fragment.setArguments(bundle);
            i+=1;
            return fragment;
        }
        else if (i == 1) {
            ProductFragment fragment = new ProductFragment();
            fragment.setArguments(bundle);
            i+=1;
            return fragment;
        }
        else if (i == 2){
            DemoFragment fragment = new DemoFragment();
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
        return 3;
    }

    @Override
    @Nullable
    public CharSequence getPageTitle (int position){

        position +=1;

        return "Fragment "+position;
    }
}
