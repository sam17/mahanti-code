package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;



import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectActivity extends AppCompatActivity {
    Button btnConnect;
    EditText edIp, edPort;
    String ipAddress, port;
    String regexPort = "([0-9]{1,4})";
    String regexIp = "\\b((?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(?:(?<!\\.)\\b|\\.)){4}";
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        btnConnect = findViewById(R.id.okButton);


        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(ConnectActivity.this);
                dialog.setContentView(R.layout.activity_connect);
                dialog.setTitle("Enter Server IP Address with Port number");
                TextView textSSID =  dialog.findViewById(R.id.textSSID1);

                Button dialogButton =  dialog.findViewById(R.id.okButton);
                edIp =  dialog.findViewById(R.id.textIp);
                edPort = dialog.findViewById(R.id.textPort);

                // if button is clicked, connect to the network;
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("ffff","hhhhh");
                        boolean status = true;
                        ipAddress = edIp.getText().toString();
                        port = edPort.getText().toString();
                        Intent i = new Intent(ConnectActivity.this, Home.class);

// Starts TargetActivity
                        startActivity(i);
                        if(port.isEmpty() || !port.matches(regexPort)){
                            edPort.setError("Invalid");
                            status = false;
                        }
                        if (ipAddress.isEmpty() || !ipAddress.matches(regexIp)) {
                            edIp.setError("Invalid");
                            status = false;
                        }

                        if(status){
                            new Thread(new Thread1()).start();
                        }
                    }
                });
                dialog.show();
            }
        });


    }



    private PrintWriter output;
    private BufferedReader input;
    class Thread1 implements Runnable {
        public void run() {
            Socket socket = null;
            try {
                Log.d("Socket","Connecting....");

                socket = new Socket(ipAddress, Integer.parseInt(port));
                output = new PrintWriter(socket.getOutputStream());
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Toast.makeText(ConnectActivity.this, "Connected....", Toast.LENGTH_SHORT).show();
                    }
                });
                new Thread(new Thread2()).start();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
    class Thread2 implements Runnable {
        @Override
        public void run() {
            while (true) {
                Log.d("server: " , "open to read" + "\n");
                try {
                    final String message = input.readLine();
                    Log.d("server: ", input.readLine() + "\n");
                    if (message != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //tvMessages.append("server: " + message + "\n");
                                Log.d("server: ", message + "\n");
                            }
                        });
                    } else {
                        new Thread(new Thread1()).start();
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }

    class Thread3 implements Runnable {
        private String message;
        Thread3(String message) {
            this.message = message;
        }
        @Override
        public void run() {
            if(output!=null) {
                Log.d("Message", message);
                output.write(message);
                output.flush();
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ConnectActivity.this, "Socket is not connected.. Please connect first", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }


}
