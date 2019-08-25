package com.project.lgs.SupplierClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.project.lgs.CartClasses.UserProfile;
import com.project.lgs.Login;
import com.project.lgs.MainActivity;
import com.project.lgs.R;

public class SupplierMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_menu);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
        }
    }

    public void supProfile(View v){

        Intent i = new Intent(this, SupProfile.class);
        this.startActivity(i);

    }

    public void supCart(View v){

        Intent i = new Intent(this, UserProfile.class);
        this.startActivity(i);

    }

    public void productList(View v){

        Intent i = new Intent(this, ProductList.class);
        this.startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logoutmenu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.logout) {

            Login.mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            Log.d("google", "sign out completed");
                        }
                    });


            Login.mGoogleSignInClient.revokeAccess()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {

                            MainActivity.userLogin = null;
                            MainActivity.supplierLogin = null;
                            Intent i = new Intent(SupplierMenu.this, Login.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            SupplierMenu.this.startActivity(i);
                        }
                    });


        }

        return true;
    }

}
