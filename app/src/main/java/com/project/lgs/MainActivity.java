package com.project.lgs;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.android.material.tabs.TabLayout;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.project.lgs.CartClasses.UserProfile;
import com.project.lgs.Database.CartMgr;
import com.project.lgs.Database.MongoConnect;
import com.project.lgs.Database.ProductsMgr;
import com.project.lgs.Database.SupplierMgr;
import com.project.lgs.Database.UsersMgr;
import com.project.lgs.SearchClasses.SearchActivity;
import com.project.lgs.SupplierClasses.Supplier;
import com.project.lgs.SupplierClasses.SupplierMenu;
import com.project.lgs.UsersClasses.User;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;


public class MainActivity extends AppCompatActivity{

    public static RemoteMongoClient mongoClient;
    public static Context mcontext;
    public static final String dbName = "LGS";
    public static User userLogin;
    public static Supplier supplierLogin;
    public static boolean isSupp;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
        }

        MongoConnect mongo = new MongoConnect();
        mongoClient = mongo.connect();
        mcontext = this;

        if (mongoClient == null) {

            setContentView(R.layout.activity_problem);

        } else {

            String email = (String) getIntent().getStringExtra("Email");
            getLoginInfo(email);

            if (isSupp == false){

                UsersMgr userMgr = new UsersMgr(MainActivity.dbName, MainActivity.mongoClient);

                HashMap<String, String> usIns = new HashMap<String, String>();
                usIns.put("Email", email);

                ArrayList<User> user = userMgr.findDocument(usIns, new HashMap<String, Integer>(), 1);

                userLogin = user.get(0);
                supplierLogin = null;
            }

            setContentView(R.layout.activity_main);
           /* toolbar = findViewById(R.id.toolBar);
            setSupportActionBar(toolbar);*/
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);


            viewPager = findViewById(R.id.pager);
            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(viewPagerAdapter);

            tabLayout = findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.promenu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.itemmenu).getActionView();
        ComponentName componentName = new ComponentName(this, SearchActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.profilemenu) {
            if (isSupp ==true && supplierLogin != null){
                Intent i = new Intent(this, SupplierMenu.class);
                this.startActivity(i);
                return true;

            }else{
                if(isSupp == false && userLogin != null){

                    Intent i = new Intent(this, UserProfile.class);
                    this.startActivity(i);
                    return true;
                }
            }
        }

        return true;
    }

    private void getLoginInfo (String email){

        SupplierMgr supplierMgr = new SupplierMgr(MainActivity.dbName, MainActivity.mongoClient);

       /* HashMap<String, String> supIns = new HashMap<String, String>();
        supIns.put("Name", "Maya");
        supIns.put("Description", "Welcome to my shopping palace");
        supIns.put("PhoneNumber", "96101101101");
        supIns.put("JoinDate", "01/01/2019");
        supIns.put("Email", "mayaak.943@gmail.com");

        HashMap<String, byte[]> p = new HashMap<>();
        p.put("Image",null);
        supplierMgr.insertDocumentWPic(supIns,p);*/

        HashMap<String, String> prodIns = new HashMap<String, String>();
        prodIns.put("Email", email);

        HashMap<String, Integer> prodSort = new HashMap<String, Integer>();

        ArrayList<Supplier> sup = supplierMgr.findDocument(prodIns, prodSort, 1);

        if (sup.size()>0) {

            supplierLogin = sup.get(0);
            userLogin = null;
            isSupp = true;

        }else {

            UsersMgr userMgr = new UsersMgr(MainActivity.dbName, MainActivity.mongoClient);

            HashMap<String, String> usIns = new HashMap<String, String>();
            usIns.put("Email", email);

            HashMap<String, Integer> usSort = new HashMap<String, Integer>();

            ArrayList<User> user = userMgr.findDocument(usIns, usSort, 1);
            if (user.size() == 0) {
                userMgr.insertDocumentWait(usIns);
            }

            isSupp = false;
        }
    }

}
