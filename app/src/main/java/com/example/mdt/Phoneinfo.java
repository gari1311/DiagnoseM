package com.example.mdt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Phoneinfo extends AppCompatActivity {

    ImageView iv30, iv31, iv32, iv33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneinfo);
        iv30 = (ImageView)findViewById(R.id.iv30);
        iv31 = (ImageView)findViewById(R.id.iv31);
        iv32 = (ImageView)findViewById(R.id.iv32);
        iv33 = (ImageView)findViewById(R.id.iv33);

        iv30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i28 = new Intent(Phoneinfo.this, Memoryinfo.class);
                startActivity(i28);
            }
        });

        iv31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i29 = new Intent(Phoneinfo.this, Deviceinfo.class);
                startActivity(i29);
            }
        });

        iv32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i30 = new Intent(Phoneinfo.this, Batteryinfo.class);
                startActivity(i30);
            }
        });

        iv33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i31 = new Intent(Phoneinfo.this, CPUinfo.class);
                startActivity(i31);
            }
        });
    }
}