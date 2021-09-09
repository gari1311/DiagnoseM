package com.example.mdt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Motion extends AppCompatActivity {

    ImageView iv8, iv27, iV6, IV3, IV4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion);
        iv27 = (ImageView)findViewById(R.id.iv27);
        iV6 = (ImageView)findViewById(R.id.iV6);
        IV3 = (ImageView)findViewById(R.id.IV3);
        IV4 = (ImageView)findViewById(R.id.IV4);


        iv27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i9 = new Intent(Motion.this, Accelerometer.class);
                startActivity(i9);
            }
        });
        iV6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Motion.this, Gyroscope.class);
                startActivity(in);
            }
        });
        IV3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Motion.this, Pedometer.class);
                startActivity(intent);
            }
        });
        IV4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Motion.this, Gravity.class);
                startActivity(intent1);
            }
        });
    }
}