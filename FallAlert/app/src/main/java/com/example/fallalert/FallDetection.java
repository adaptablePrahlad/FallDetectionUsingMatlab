package com.example.fallalert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class FallDetection extends AppCompatActivity {
    Button buttonyes,buttonno;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fall_detection);

        buttonyes = findViewById(R.id.buttonyes);
        buttonno = findViewById(R.id.buttonno);

        buttonyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentyes = new Intent(getApplicationContext(),HelpRequest.class);
                startActivity(intentyes);
            }
        });

        buttonno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    FileOutputStream output = openFileOutput("matlab.txt",Context.MODE_PRIVATE);
                    OutputStreamWriter outwriter = new OutputStreamWriter(output);
                    outwriter.write(0);
                    outwriter.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }


                Intent intentno = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(intentno);
            }
        });

        //Handler is used to create a delay in the splash screen
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),HelpRequest.class));
                finish();
            }
        },10000);


    }
}