package com.example.mdt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Hardware extends AppCompatActivity {

    ImageView iv13, iv14, iv15, iv7, iv28, iv29;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware);
        iv13 = (ImageView)findViewById(R.id.iv13);
        iv14 = (ImageView)findViewById(R.id.iv14);
        iv15 = (ImageView)findViewById(R.id.iv15);
        iv28 = (ImageView)findViewById(R.id.iv28);
        //iv29 = (ImageView)findViewById(R.id.iv29);
        iv7 = (ImageView)findViewById(R.id.iv7);

        iv13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i14 = new Intent(Hardware.this, Buttons.class);
                startActivity(i14);
            }
        });

        iv14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i15 = new Intent(Hardware.this, Vibration.class);
                startActivity(i15);
            }
        });

        iv15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i16 = new Intent(Hardware.this, Fingerprint.class);
                startActivity(i16);
            }
        });

        iv28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i25 = new Intent(Hardware.this, Lightsensor.class);
                startActivity(i25);
            }
        });

        iv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i7 = new Intent(Hardware.this, Touchscreen.class);
                startActivity(i7);
            }
        });
    }
}