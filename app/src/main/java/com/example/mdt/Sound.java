package com.example.mdt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Sound extends AppCompatActivity {

    ImageView iv9, iv10, iv11, iv12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
        iv9 = (ImageView)findViewById(R.id.iv9);
        iv10 = (ImageView)findViewById(R.id.iv10);
        iv11 = (ImageView)findViewById(R.id.iv11);
        iv12 = (ImageView)findViewById(R.id.iv12);

        iv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i10 = new Intent(Sound.this, Speaker.class);
                startActivity(i10);
            }
        });

        iv10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i11 = new Intent(Sound.this, Microphone.class);
                startActivity(i11);
            }
        });

        iv11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i12 = new Intent(Sound.this, Earpiece.class);
                startActivity(i12);
            }
        });

        iv12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i13 = new Intent(Sound.this, Headphones.class);
                startActivity(i13);
            }
        });
    }
}