package com.example.mdt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Camera extends AppCompatActivity {

    ImageView iv24, iv25;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        iv24 = (ImageView)findViewById(R.id.iv24);

        iv24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i23 = new Intent(Camera.this, Backcamera.class);
                startActivity(i23);
            }
        });
    }
}