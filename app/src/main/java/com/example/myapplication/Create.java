package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class Create extends AppCompatActivity {

    ImageView PowerBtn, jog,killit,home, execute;
    Boolean status = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


        PowerBtn = findViewById(R.id.power);
        jog = findViewById(R.id.joghome);
        killit = findViewById(R.id.kill);
        home = findViewById(R.id.homeexecute);
        execute = findViewById(R.id.executecreate);


        PowerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject code = new JSONObject();
                try {
                    if (status) {
                        status = false;
                        code.accumulate("power", true);
                    } else {
                        status = true;
                        code.accumulate("power", false);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new Thread(new Create.Thread3(code.toString())).start();
            }
        });

        jog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Explicit Intent by specifying its class name
                Intent i = new Intent(Create. this, Jog.class);

                // Starts TargetActivity
                startActivity(i);
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Explicit Intent by specifying its class name
                Intent i = new Intent(Create. this, Home.class);

                // Starts TargetActivity
                startActivity(i);
            }
        });

        execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Explicit Intent by specifying its class name
                Intent i = new Intent(Create. this, Execute.class);

                // Starts TargetActivity
                startActivity(i);
            }
        });

    }

    private PrintWriter output;
    private BufferedReader input;
    class Thread3 implements Runnable {
        private String message;

        Thread3(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            if (output != null) {
                Log.d("Message", message);
                output.write(message);
                output.flush();
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Create.this, "Socket is not connected.. Please connect first", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}

