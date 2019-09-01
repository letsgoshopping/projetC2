package com.project.lgs.AdminClasses;

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
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.project.lgs.Database.MongoConnect;
import com.project.lgs.Login;
import com.project.lgs.MainActivity;
import com.project.lgs.OrdersClasses.OrderHistory;
import com.project.lgs.R;

public class AdminActivity extends AppCompatActivity {

    public static RemoteMongoClient mongoClient;
    public static final String dbName = "LGS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary)); //status bar or the time bar at the top
        }

        MongoConnect mongo = new MongoConnect();
        mongoClient = mongo.connect();

        if (mongoClient == null) {

            setContentView(R.layout.activity_problem);

        } else {
            setContentView(R.layout.activity_admin);
        }
    }

    public void categories(View v){

        Intent i = new Intent(this, CategoriesManagement.class);
        this.startActivity(i);

    }

    public void suppliers(View v){

        Intent i = new Intent(this, SuppliersManagement.class);
        this.startActivity(i);

    }

    public void orders(View v){

        Intent i = new Intent(this, OrderHistory.class);
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
                            Intent i = new Intent(AdminActivity.this, Login.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            AdminActivity.this.startActivity(i);
                        }
                    });


        }

        return true;
    }

}
