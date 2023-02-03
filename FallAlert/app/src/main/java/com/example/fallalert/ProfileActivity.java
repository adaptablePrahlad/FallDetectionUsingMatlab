package com.example.fallalert;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity {

    TextView text_useremail,text_username,text_bg,text_econtact,fallstatustext;
    Button logoutbtn,btn_editpage;

    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    //Shared Preferences
    SharedPreferences sharedPreferences;
    private static final String SharedPreference_Name = "mypref";
    private static final String Key_Email = "email";
    private static final String Key_Password = "password";


    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fallstatustext = findViewById(R.id.fallstatustext);

        if(ActivityCompat.checkSelfPermission(ProfileActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        if(ActivityCompat.checkSelfPermission(ProfileActivity.this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.SEND_SMS}, 44);
        }
        if(ActivityCompat.checkSelfPermission(ProfileActivity.this,
                Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.INTERNET}, 44);
        }

        String text = null;

        try {

            InputStream is = getAssets().open("matlab.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);

        }catch (IOException ex){

            ex.printStackTrace();
        }

        if(Integer.parseInt(text)==1)
        {
            fallstatustext.setText("1");
            Intent intentfall = new Intent(getApplicationContext(),FallDetection.class);
            startActivity(intentfall);

        }
        else
        {
            fallstatustext.setText("0");
        }


        text_useremail = findViewById(R.id.text_useremail);
        text_username = findViewById(R.id.text_username);
        text_bg = findViewById(R.id.bloodgroup);
        text_econtact = findViewById(R.id.contact);


        logoutbtn = findViewById(R.id.logoutbtn);
        btn_editpage = findViewById(R.id.btn_editpage);


        sharedPreferences = getSharedPreferences(SharedPreference_Name,MODE_PRIVATE);

        String email = sharedPreferences.getString(Key_Email,null);
        String password = sharedPreferences.getString(Key_Password,null);

        if(email!=null || password!=null)
        {
            text_useremail.setText(email);
            Cursor c=databaseHelper.loginuser(email,password);
            if(c != null && !c.isClosed())
            {
                c.moveToFirst();
                do{
                    text_username.setText(c.getString(c.getColumnIndex("user_name")));
                    text_bg.setText(c.getString(c.getColumnIndex("user_bloodgroup")));
                    text_econtact.setText(c.getString(c.getColumnIndex("user_contact")));
                }while (c.moveToNext());
            }

        }

        //Clear the sharedreferences on Logout
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(ProfileActivity.this, "User Data Cleared", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_editpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),EditUser.class);
                startActivity(intent);
            }
        });




    }
}
