package com.project.lgs.ProductClasses;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.project.lgs.CategoryClasses.Category;
import com.project.lgs.Database.CategoriesMgr;
import com.project.lgs.Database.ProductsMgr;
import com.project.lgs.Database.SupplierMgr;
import com.project.lgs.MainActivity;
import com.project.lgs.R;

import org.bson.types.ObjectId;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class add_product extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    byte[] img = null;
    ArrayList<Category> catList = new ArrayList<>();
    String selCat;
    Product curPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
        }

        CategoriesMgr categoriesMgr = new CategoriesMgr(MainActivity.dbName,MainActivity.mongoClient);
        catList = categoriesMgr.findDocument(new HashMap<String, String>(),new HashMap<String, Integer>());
        ArrayList<String> catName = new ArrayList<>();

        for(Category c:catList){
            catName.add(c.getCatName());
        }

        Product currentProduct = (Product) getIntent().getSerializableExtra("Product");
        curPro = currentProduct;

        if(curPro != null){
            img = curPro.getImage();
            ImageView proImg  = (ImageView) this.findViewById(R.id.proPhotoIns);
            if (img == null){
                proImg.setImageResource(R.drawable.nopic);
            }
            else {
                Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length);
                proImg.setImageBitmap(bmp);
            }

            TextView proTitle  = (TextView) this.findViewById(R.id.proTitleIns);
            proTitle.setText(curPro.getTitle());

            TextView proRat  = (TextView) this.findViewById(R.id.proCodeIns);
            proRat.setText(curPro.getCode());

            TextView proPrice  = (TextView) this.findViewById(R.id.proPriceIns);
            proPrice.setText(curPro.getPrice());

            TextView proDesc  = (TextView) this.findViewById(R.id.proDescIns);
            proDesc.setText(curPro.getDescription());

            for (Category c:catList){
                if (c.getCatId().equals(curPro.getCategory())) {

                    selCat = c.getCatName();
                    break;
                }
            }
        }

        Spinner spinner = (Spinner) findViewById(R.id.proCatIns);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, catName);
        dataAdapter.setDropDownViewResource(R.layout.spinnerlayout);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);
        if (selCat!= null) {
            spinner.setSelection(dataAdapter.getPosition(selCat));
        }
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

                    }

                    InputStream iStream =   getContentResolver().openInputStream(selectedImageUri);
                    try {
                        img = getBytes(iStream);
                    } finally {
                        // close the stream
                        try {
                            iStream.close();
                        } catch (IOException ignored) { }
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

    public byte[] getBytes(InputStream inputStream) throws IOException {

        byte[] bytesResult = null;
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        try {
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            bytesResult = byteBuffer.toByteArray();
        } finally {
            // close the stream
            try{ byteBuffer.close(); } catch (IOException ignored){ }
        }
        return bytesResult;
    }

    public void submitProduct(View v){

        ProductsMgr productsMgr = new ProductsMgr(MainActivity.dbName, MainActivity.mongoClient);

        EditText title = (EditText) findViewById(R.id.proTitleIns);
        String proTitle = title.getText().toString();

        EditText desc = (EditText) findViewById(R.id.proDescIns);
        String proDesc = desc.getText().toString();
        String proRating = "";

        EditText price = (EditText) findViewById(R.id.proPriceIns);
        String proPrice = price.getText().toString();

        String proUser = MainActivity.supplierLogin.getId();
        Log.d("app",MainActivity.supplierLogin.getId());

        String prodate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());

        EditText code = (EditText) findViewById(R.id.proCodeIns);
        String proCode = code.getText().toString();

        String proCat = selCat;

        HashMap<String,String> proIns = new HashMap<>();
        proIns.put("Title",proTitle);
        proIns.put("Description", proDesc);
        proIns.put("Rating",proRating);
        proIns.put("Price",proPrice);
        proIns.put("User",proUser);
        proIns.put("PDate",prodate);
        proIns.put("Category",proCat);
        proIns.put("Code",proCode);

        HashMap<String, byte[]> proImg = new HashMap<>();

        if (img !=null){

            proImg.clear();
            proImg.put("Image", img);

        }else{

            proImg.clear();
            proImg.put("Image", null);
        }

        if(curPro != null){

            HashMap<String,ObjectId> filter = new HashMap<>();
            filter.put("_id", new ObjectId(curPro.getId()));
            productsMgr.updateDocumentWPic(proIns,filter,proImg);

            Intent returnIntent = new Intent();
            returnIntent.putExtra("result",1);
            setResult(RESULT_OK,returnIntent);
            finish();

        }else{
            productsMgr.insertDocumentWPic(proIns,proImg);

            finish();
            startActivity(getIntent());
            Toast toast = Toast.makeText(this, "Product Inseted", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void cancelProduct(View v){
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = parent.getItemAtPosition(position).toString();
        ((TextView) parent.getChildAt(0)).setTextColor(Color.DKGRAY);

        for(Category c:catList){
            if (item.equals(c.getCatName())){selCat = c.getCatId(); break;}
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
