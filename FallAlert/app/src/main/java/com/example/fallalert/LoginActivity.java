package com.example.fallalert;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    TextView button;
    Button btn_sign_in;

    EditText edit_loginemail;
    EditText edit_loginpassword;

    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    User user =new User();

    //Shared Preferences
    SharedPreferences sharedPreferences;
    private static final String SharedPreference_Name = "mypref";
    private static final String Key_Email = "email";
    private static final String Key_Password = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = findViewById(R.id.mtv_sign_up);
        btn_sign_in = findViewById(R.id.btn_sign_in);

        edit_loginemail = findViewById(R.id.edit_loginemail);
        edit_loginpassword = findViewById(R.id.edit_loginpassword);

        sharedPreferences = getSharedPreferences(SharedPreference_Name,MODE_PRIVATE);
        //Before opening activity check the shared preference data is available or not

        String useremail = sharedPreferences.getString(Key_Email,null);
        if(useremail != null)
        {
            //If user already login, then directly open profile page
            Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
            startActivity(intent);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor User = databaseHelper.loginuser(edit_loginemail.getText().toString(),edit_loginpassword.getText().toString());
                if(User!=null && User.moveToFirst())
                {
                    //First Put Data in Shared Preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Key_Email,edit_loginemail.getText().toString());
                    editor.putString(Key_Password,edit_loginpassword.getText().toString());
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}