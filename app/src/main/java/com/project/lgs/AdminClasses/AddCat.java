package com.project.lgs.AdminClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.lgs.AdminClasses.AdminActivity;
import com.project.lgs.CategoryClasses.Category;
import com.project.lgs.Database.CategoriesMgr;
import com.project.lgs.Database.ProductsMgr;
import com.project.lgs.R;

import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class AddCat extends AppCompatActivity {

    Category curCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cat);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
        }

        curCat = (Category) getIntent().getSerializableExtra("Category");

        if(curCat != null) {

            TextView catName = (TextView) this.findViewById(R.id.catNameIns);
            catName.setText(curCat.getCatName());

        }

    }

    public void submitCategory(View v){

        CategoriesMgr categoriesMgr = new CategoriesMgr(AdminActivity.dbName, AdminActivity.mongoClient);

        EditText title = (EditText) findViewById(R.id.catNameIns);
        String catName = title.getText().toString();


        HashMap<String,String> catIns = new HashMap<>();
        catIns.put("Name",catName);
        catIns.put("Display", "1");


        if(curCat != null){

            HashMap<String, ObjectId> filter = new HashMap<>();
            filter.put("_id", new ObjectId(curCat.getCatId()));
            categoriesMgr.updateDoument(catIns,filter);

            Intent returnIntent = new Intent();
            returnIntent.putExtra("result",1);
            setResult(RESULT_OK,returnIntent);
            finish();

        }else{
            categoriesMgr.insertDocument(catIns);

            finish();
            startActivity(getIntent());
            Toast toast = Toast.makeText(this, "Category Inserted", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void cancelCategory(View v){
        finish();
        startActivity(getIntent());
    }


}
