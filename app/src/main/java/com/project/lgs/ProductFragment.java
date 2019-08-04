package com.project.lgs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.project.lgs.Database.ProductsMgr;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_search_product, container, false);
        Bundle bundle = new Bundle();
        ActivitySearchProduct activitySearchProduct = new ActivitySearchProduct();

        return view;

    }
}
