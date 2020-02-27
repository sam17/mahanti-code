package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    Button enter;
    EditText name , pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        enter =  findViewById(R.id.login);
        name =  findViewById(R.id.username);
        pwd =  findViewById(R.id.password);



        enter.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(name.getText().toString().equals("Mahanti") &&
                        pwd.getText().toString().equals("hexa")) {
                    // Explicit Intent by specifying its class name
                    Intent i = new Intent(getApplicationContext(), ConnectActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    // Starts TargetActivity
                    getApplicationContext().startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
