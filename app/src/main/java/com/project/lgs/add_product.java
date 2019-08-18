package com.project.lgs;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.lgs.CategoryClasses.Category;
import com.project.lgs.Database.CategoriesMgr;
import com.project.lgs.Database.ProductsMgr;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class add_product extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    byte[] img;
    Category selectedCat;
    final int MAX_FILE_SIZE = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CategoriesMgr catMgr = new CategoriesMgr(MainActivity.dbName, MainActivity.mongoClient);
        HashMap<String, String> catIns = new HashMap<String, String>();
        catIns.put("Display","1");
        HashMap<String, Integer> catSort = new HashMap<String, Integer>();
        catSort.put("Name", 1);

        ArrayList<Category> catList = catMgr.findDocument(catIns,catSort);

        Spinner spinner = (Spinner) findViewById(R.id.categoryList);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<Category> adapter =
        new ArrayAdapter<Category>(this,  android.R.layout.simple_spinner_dropdown_item, catList);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    public void SubmitProduct(View view) {


        HashMap<String,String> prod = new HashMap<>();
        HashMap<String,byte[]> prodImage = new HashMap<>();

        TextView proTxtV  = (TextView) findViewById(R.id.prodNameIns);
        prod.put("Title",(String)proTxtV.getText());

        proTxtV  = (TextView) findViewById(R.id.prodDescIns);
        prod.put("Description",(String)proTxtV.getText());

        prod.put("Rating","0");

        proTxtV  = (TextView) findViewById(R.id.prodPriceIns);
        prod.put("Price",(String)proTxtV.getText());

        prod.put("User","user1");

        prod.put("PDate",new SimpleDateFormat("dd/mm/yyyy").format(new Date()));

        prod.put("Category",selectedCat.getCatId());

        prodImage.put("Image",img);

        ProductsMgr productsMgr = new ProductsMgr(MainActivity.dbName,MainActivity.mongoClient);
        productsMgr.insertDocumentWPic(prod,prodImage);

    }

    public void insertPic(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == 1) {
                    Uri selectedImageUri = data.getData();
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    if (path != null) {
                        File f = new File(path);
                        selectedImageUri = Uri.fromFile(f);
                        img = this.read(f);
                    }
                    // Set the image in ImageView
                    ImageView imageView = (ImageView) findViewById(R.id.proPhotoIns);
                    imageView.setImageURI(selectedImageUri);
                }
            }
        } catch (Exception e) {
            Log.e("FileSelectorActivity", "File select error", e);
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        selectedCat = (Category)parent.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        selectedCat = (Category)parent.getItemAtPosition(1);
    }

    public byte[] read(File file) throws IOException{
        if (file.length() > MAX_FILE_SIZE) {
            throw new IOException("File too big");
        }

        byte[] buffer = new byte[(int) file.length()];
        InputStream ios = null;
        try {
            ios = new FileInputStream(file);
            if (ios.read(buffer) == -1) {
                throw new IOException(
                        "EOF reached while trying to read the whole file");
            }
        } finally {
            try {
                if (ios != null)
                    ios.close();
            } catch (IOException e) {
            }
        }
        return buffer;
    }

}
