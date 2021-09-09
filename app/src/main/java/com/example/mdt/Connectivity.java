package com.example.mdt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Connectivity extends AppCompatActivity {

    ImageView iv19, iv20, iv21, iv22, iva;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connectivity);
        iv19 = (ImageView)findViewById(R.id.iv19);
        iv20 = (ImageView)findViewById(R.id.iv20);
        iv21 = (ImageView)findViewById(R.id.iv21);
        iv22 = (ImageView)findViewById(R.id.iv22);
        iva = (ImageView)findViewById(R.id.iva);

        iv19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i18 = new Intent(Connectivity.this, Network.class);
                startActivity(i18);
            }
        });

        iv20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i19 = new Intent(Connectivity.this, Bluetooth.class);
                startActivity(i19);
            }
        });

        iv21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i20 = new Intent(Connectivity.this, Cellular.class);
                startActivity(i20);
            }
        });

        iv22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i21 = new Intent(Connectivity.this, GPS.class);
                startActivity(i21);
            }
        });

        iva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ia = new Intent(Connectivity.this, Airplanemode.class);
                startActivity(ia);
            }
        });
    }
}