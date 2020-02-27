package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class Jog extends AppCompatActivity {

    ImageView PowerBtn, jog,killit,execute, create,home,px,nx,py,ny,zp,zn,an,ap;
    Boolean status = true;
    EditText value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jog);



        PowerBtn = findViewById(R.id.power);
        jog = findViewById(R.id.joghome);
        killit = findViewById(R.id.kill);
        execute = findViewById(R.id.executehome);
        create = findViewById(R.id.createhome);
        home = findViewById(R.id.homejog);
        px = findViewById(R.id.xPositive);
        nx = findViewById(R.id.xNegative);
        py = findViewById(R.id.yPositive);
        ny = findViewById(R.id.yNegative);
        zp = findViewById(R.id.zPositive);
        zn = findViewById(R.id.zNegative);
        an = findViewById(R.id.aNegative);
        ap = findViewById(R.id.aPositive);
        value = findViewById(R.id.edPoint);







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

                new Thread(new Jog.Thread3(code.toString())).start();
            }
        });

        execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Explicit Intent by specifying its class name
                Intent i = new Intent(Jog. this, Execute.class);

                // Starts TargetActivity
                startActivity(i);
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Explicit Intent by specifying its class name
                Intent i = new Intent(Jog. this, Home.class);

                // Starts TargetActivity
                startActivity(i);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Explicit Intent by specifying its class name
                Intent i = new Intent(Jog. this, Create.class);

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
                        Toast.makeText(Jog.this, "Socket is not connected.. Please connect first", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
