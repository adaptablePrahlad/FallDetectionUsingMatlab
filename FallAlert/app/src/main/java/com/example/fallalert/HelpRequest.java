package com.example.fallalert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

public class HelpRequest extends AppCompatActivity {

    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    //Shared Preferences
    SharedPreferences sharedPreferences;
    private static final String SharedPreference_Name = "mypref";
    private static final String Key_Email = "email";
    private static final String Key_Password = "password";

    protected LocationManager locationManager;

    private String currentLocation;

    private String emergencyContact, Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_request);

        if(ActivityCompat.checkSelfPermission(HelpRequest.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(HelpRequest.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        if(ActivityCompat.checkSelfPermission(HelpRequest.this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(HelpRequest.this, new String[]{Manifest.permission.SEND_SMS}, 44);
        }
        if(ActivityCompat.checkSelfPermission(HelpRequest.this,
                Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(HelpRequest.this, new String[]{Manifest.permission.INTERNET}, 44);
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000L, 1.0f, (LocationListener) this);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        currentLocation = location.getLatitude() + "," + location.getLongitude();

        sharedPreferences = getSharedPreferences(SharedPreference_Name, MODE_PRIVATE);

        String email = sharedPreferences.getString(Key_Email,null);
        String password = sharedPreferences.getString(Key_Password,null);

        if(email!=null || password!=null)
        {
            Cursor c=databaseHelper.loginuser(email,password);
            if(c != null && !c.isClosed())
            {
                c.moveToFirst();
                do{
                    Name = c.getString(c.getColumnIndex("user_name"));
                    emergencyContact = c.getString(c.getColumnIndex("user_contact"));

                    SmsManager smsManager = SmsManager.getDefault();
                    String locationnew = Name + "needs help! Kindly reach https://maps.google.com/?q=" + currentLocation;
                    smsManager.sendTextMessage(emergencyContact, null, locationnew, null, null);
                    Toast.makeText(getApplicationContext(), "SMS SENT",
                            Toast.LENGTH_LONG).show();
                }while (c.moveToNext());
            }

        }


    }
}