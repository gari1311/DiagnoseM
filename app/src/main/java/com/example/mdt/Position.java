package com.example.mdt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Position extends AppCompatActivity {

    ImageView iv26, iv8, Img3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);
        iv8 = (ImageView)findViewById(R.id.iv8);
        iv26 = (ImageView)findViewById(R.id.iv26);
        Img3 = (ImageView)findViewById(R.id.Img3);
        iv26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i17 = new Intent(Position.this, Proximity.class);
                startActivity(i17);
            }
        });

        iv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i8 = new Intent(Position.this, Compass.class);
                startActivity(i8);
            }
        });

        Img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i8 = new Intent(Position.this, orientation.class);
                startActivity(i8);
            }
        });
    }
}