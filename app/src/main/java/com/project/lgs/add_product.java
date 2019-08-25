package com.project.lgs;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class add_product extends AppCompatActivity {
    byte[] img = null;
    final int MAX_FILE_SIZE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sup_profile);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
        }

        EditText name = (EditText) findViewById(R.id.supNameIns);
        name.setText(MainActivity.supplierLogin.getName());

        EditText number = (EditText) findViewById(R.id.supPhoneIns);
        number.setText(MainActivity.supplierLogin.getPhoneNumber().replaceFirst("961",""));

        EditText desc = (EditText) findViewById(R.id.supDescIns);
        desc.setText(MainActivity.supplierLogin.getDescription());

        ImageView imageView = (ImageView) findViewById(R.id.supPhotoIns);
        byte[] supImage = MainActivity.supplierLogin.getImage();
        if (supImage == null){
            imageView.setImageResource(R.drawable.nopic);
        }
        else {
            Bitmap bmp = BitmapFactory.decodeByteArray(supImage, 0, supImage.length);
            imageView.setImageBitmap(bmp);
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
                    ImageView imageView = (ImageView) findViewById(R.id.supPhotoIns);
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

        SupplierMgr supplierMgr = new SupplierMgr(MainActivity.dbName, MainActivity.mongoClient);

        EditText name = (EditText) findViewById(R.id.supNameIns);
        String supName = name.getText().toString();

        EditText number = (EditText) findViewById(R.id.supPhoneIns);
        String supNumber = "961" + number.getText().toString();

        EditText desc = (EditText) findViewById(R.id.supDescIns);
        String supDesc = desc.getText().toString();

        MainActivity.supplierLogin.setName(supName);
        MainActivity.supplierLogin.setPhoneNumber(supNumber);
        MainActivity.supplierLogin.setDescription(supDesc);

        HashMap<String,String> supUpd = new HashMap<>();
        supUpd.put("Name",supName);
        supUpd.put("PhoneNumber", supNumber);
        supUpd.put("Description",supDesc);

        HashMap<String, ObjectId>filter = new HashMap<>();
        filter.put("_id", new ObjectId(MainActivity.supplierLogin.getId()));

        if (img !=null){

            HashMap<String, byte[]> supImg = new HashMap<>();
            supImg.put("Image", img);

            supplierMgr.updateDocumentWPic(supUpd,filter,supImg);
            MainActivity.supplierLogin.setImage(img);

        }else{

            supplierMgr.updateDocument(supUpd,filter);
        }

        finish();
        startActivity(getIntent());

    }

    public void cancelProduct(View v){
        finish();
        startActivity(getIntent());
    }
}
