package com.example.fallalert;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditUser extends AppCompatActivity {

    Button btn_edit;

    EditText edit_fullname;
    EditText edit_password;
    EditText edit_bloodgroup;
    EditText edit_emergencycontact;

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
        setContentView(R.layout.activity_edit_user);

        btn_edit = findViewById(R.id.btn_edit);
        edit_fullname = findViewById(R.id.edit_fullname);
        edit_password = findViewById(R.id.edit_password);
        edit_bloodgroup = findViewById(R.id.edit_bloodgroup);
        edit_emergencycontact =findViewById(R.id.edit_emergencycontact);

        sharedPreferences = getSharedPreferences(SharedPreference_Name,MODE_PRIVATE);

        String email = sharedPreferences.getString(Key_Email,null);
        String password = sharedPreferences.getString(Key_Password,null);

        if(email!=null || password!=null)
        {
            edit_password.setText(password);
            Cursor c=databaseHelper.loginuser(email,password);
            if(c != null && !c.isClosed())
            {
                c.moveToFirst();
                do{
                    edit_fullname.setText(c.getString(c.getColumnIndex("user_name")));
                    edit_bloodgroup.setText(c.getString(c.getColumnIndex("user_bloodgroup")));
                    edit_emergencycontact.setText(c.getString(c.getColumnIndex("user_contact")));
                }while (c.moveToNext());
            }

        }

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = edit_fullname.getText().toString();
                String contactTXT = edit_emergencycontact.getText().toString();
                String bgTXT = edit_bloodgroup.getText().toString();
                String passwordTXT = edit_password.getText().toString();

                Boolean checkupdatedata = databaseHelper.updateuserdata(email, nameTXT, passwordTXT,bgTXT,contactTXT);
                if(checkupdatedata==true) {
                    Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                    startActivity(intent);                }
                else
                    Toast.makeText(getApplicationContext(), "User Data Not Updated", Toast.LENGTH_SHORT).show();
            }
        });



    }
}