package com.example.fallalert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {

    TextView button;
    Button signup_button;

    EditText edit_fullname;
    EditText edit_email;
    EditText edit_bloodgroup;
    EditText edit_emergencycontact;
    EditText edit_password;

    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    User user =new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        button = findViewById(R.id.mtv_sign_in);
        signup_button = findViewById(R.id.btn_sign_up);

        edit_fullname = findViewById(R.id.edit_fullname);
        edit_email = findViewById(R.id.edit_email);
        edit_bloodgroup = findViewById(R.id.edit_bloodgroup);
        edit_emergencycontact =findViewById(R.id.edit_emergencycontact);
        edit_password = findViewById(R.id.edit_password);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setName(edit_fullname.getText().toString());
                user.setEmail(edit_email.getText().toString());
                user.setBloodGroup(edit_bloodgroup.getText().toString());
                user.setContact(edit_emergencycontact.getText().toString());
                user.setPassword(edit_password.getText().toString());

                Boolean checkinsertdata = databaseHelper.addUser(user);
                if(checkinsertdata==true) {
                    Toast.makeText(RegisterActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(RegisterActivity.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                }
                }
        });


    }

}