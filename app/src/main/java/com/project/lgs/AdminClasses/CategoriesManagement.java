package com.project.lgs.AdminClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.project.lgs.CategoryClasses.Category;
import com.project.lgs.Database.CategoriesMgr;
import com.project.lgs.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoriesManagement extends AppCompatActivity implements CategoryAdapter.CategoryListener{

    ArrayList<Category> cat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_management);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
        }

        CategoriesMgr categoriesMgr = new CategoriesMgr(AdminActivity.dbName, AdminActivity.mongoClient);
        cat = categoriesMgr.findDocument(new HashMap<String, String>(), new HashMap<String, Integer>());

        CategoryAdapter categoryAdapter = new CategoryAdapter(this, cat,this);
        ListView listView = (ListView)findViewById(R.id.category_list);
        listView.setAdapter(categoryAdapter);
    }

    public void addCategory (View v){

        Intent i = new Intent(this, AddCat.class);
        this.startActivity(i);

    }

    @Override
    public void categoryListener(int position) {
        Intent i = new Intent(this, AddCat.class);
        i.putExtra("Category", cat.get(position));
        startActivityForResult(i,1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if(resultCode == RESULT_OK){

                Toast toast = Toast.makeText(this, "Category Updated", Toast.LENGTH_SHORT);
                toast.show();
                finish();
                startActivity(getIntent());
            }
        }
    }
}
