package com.example.mdt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Frontcamera extends AppCompatActivity {

    Button btn17;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontcamera);
        btn17 = (Button)findViewById(R.id.btn17);

        btn17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Frontcamera.this, Testfrontcamera.class);
                startActivity(i);
            }
        });
    }
}