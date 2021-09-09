package com.example.mdt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Backcamera extends AppCompatActivity {

    Button btn18;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backcamera);
        btn18 = (Button)findViewById(R.id.btn18);

        btn18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Backcamera.this, Testcamera.class);
                startActivity(intent);
            }
        });
    }
}