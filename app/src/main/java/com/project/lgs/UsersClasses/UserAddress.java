package com.project.lgs.UsersClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.project.lgs.Database.UsersMgr;
import com.project.lgs.MainActivity;
import com.project.lgs.R;

import org.bson.types.ObjectId;
import org.w3c.dom.Text;

import java.util.HashMap;

public class UserAddress extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
        }

        user = MainActivity.userLogin;

        TextView conName = (TextView)findViewById(R.id.contract_name);
        conName.setText(user.getConName());

        TextView phoneCode = (TextView)findViewById(R.id.phone_code);
        phoneCode.setText(user.getPhoneCode() == null? "961":user.getPhoneCode());

        TextView phoneNum = (TextView)findViewById(R.id.phone_num);
        phoneNum.setText(user.getPhoneNum());

        TextView city = (TextView)findViewById(R.id.city);
        city.setText(user.getCity());

        TextView street = (TextView)findViewById(R.id.street);
        street.setText(user.getStreet());

        TextView floor = (TextView)findViewById(R.id.floor);
        floor.setText(user.getFloor());

    }

    public void cancelAddress (View v){

        startActivity(getIntent());

    }

    public void submitAddress (View v){

        TextView conName = (TextView)findViewById(R.id.contract_name);
        String name = conName.getText().toString();
        MainActivity.userLogin.setConName(name);

        TextView phoneCode = (TextView)findViewById(R.id.phone_code);
        String code = phoneCode.getText().toString();
        MainActivity.userLogin.setPhoneCode(code);

        TextView phoneNum = (TextView)findViewById(R.id.phone_num);
        String num = phoneNum.getText().toString();
        MainActivity.userLogin.setPhoneNum(num);

        TextView city = (TextView)findViewById(R.id.city);
        String ccity = city.getText().toString();
        MainActivity.userLogin.setCity(ccity);

        TextView street = (TextView)findViewById(R.id.street);
        String sstreet = street.getText().toString();
        MainActivity.userLogin.setStreet(sstreet);

        TextView floor = (TextView)findViewById(R.id.floor);
        String ffloor = floor.getText().toString();
        MainActivity.userLogin.setFloor(ffloor);


        HashMap<String, String> usIns = new HashMap<>();
        usIns.put("ContractName",name);
        usIns.put("PhoneCode",code);
        usIns.put("PhoneNum",num);
        usIns.put("Country","Lebanon");
        usIns.put("City",ccity);
        usIns.put("Street",sstreet);
        usIns.put("Floor",ffloor);

        HashMap<String, ObjectId> filter = new HashMap<>();
        filter.put("_id", new ObjectId(MainActivity.userLogin.getId()));

        UsersMgr usersMgr = new UsersMgr(MainActivity.dbName, MainActivity.mongoClient);
        usersMgr.updateDocument(usIns,filter);

        Toast toast = Toast.makeText(this, "Address Updated", Toast.LENGTH_SHORT);
        toast.show();

    }
}
