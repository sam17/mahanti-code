package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class Home extends AppCompatActivity {

    ImageView PowerBtn, jog,killit,execute, create;
    Boolean status = true, button = false, coolant =false;
    RadioButton spindle,fluid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        PowerBtn = findViewById(R.id.power);
        jog = findViewById(R.id.joghome);
        killit = findViewById(R.id.kill);
        execute = findViewById(R.id.executehome);
        create = findViewById(R.id.createhome);
        spindle = findViewById(R.id.spindlebutton);
        fluid = findViewById(R.id.coolanthome);

        spindle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                JSONObject code = new JSONObject();
                try {
                    if (button) {
                        button = false;
                        code.accumulate("power", true);
                    } else {
                        button = true;
                        code.accumulate("power", false);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new Thread(new Thread3(code.toString())).start();
            }

        });


        fluid.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                JSONObject code = new JSONObject();
                try {
                    if (coolant) {
                        coolant = false;
                        code.accumulate("power", true);
                    } else {
                        coolant = true;
                        code.accumulate("power", false);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new Thread(new Thread3(code.toString())).start();
            }

        });


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

                new Thread(new Thread3(code.toString())).start();
            }
        });

        jog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Explicit Intent by specifying its class name
                Intent i = new Intent(Home. this, Jog.class);

                // Starts TargetActivity
                startActivity(i);
            }
        });


        execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Explicit Intent by specifying its class name
                Intent i = new Intent(Home. this, Execute.class);

                // Starts TargetActivity
                startActivity(i);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Explicit Intent by specifying its class name
                Intent i = new Intent(Home. this, Create.class);

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
                        Toast.makeText(Home.this, "Socket is not connected.. Please connect first", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}