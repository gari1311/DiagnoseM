package com.example.mdt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Testfrontcamera extends AppCompatActivity {

    Button btn_fcaptureImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testfrontcamera);
        btn_fcaptureImage = (Button)findViewById(R.id.btn_fcaptureImage);
        btn_fcaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}