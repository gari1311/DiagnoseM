package com.example.mdt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Testcamera extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_REQUEST_CODE = 0;
    private static final int REQUEST_WRITE_PERMISSION = 0;
    Button save;
    DatabaseHelper databaseHelper;
    ArrayList arrayList;
    String s = "";
    TextView tv;
    Button btnCaptureImage;
    ImageView imageDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testcamera);

        btnCaptureImage = (Button)findViewById(R.id.btn_captureImage);
        imageDisplay = (ImageView)findViewById(R.id.imageCapture);
        tv = (TextView)findViewById(R.id.tv);

        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        databaseHelper = new DatabaseHelper(this);
        arrayList = databaseHelper.getAllText();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = s;
                if (!text.isEmpty()){
                    if(databaseHelper.addText(text)){
                        Toast.makeText(getApplicationContext(), "Data Saved...", Toast.LENGTH_SHORT).show();
                        arrayList.clear();
                        arrayList.addAll(databaseHelper.getAllText());
                    }
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageDisplay.setImageBitmap(bitmap);
        Toast.makeText(getApplicationContext()," ✔ "+"Camera is working fine",Toast.LENGTH_LONG).show();
        s = tv.getText().toString()+"\n"+" ✔ "+"Camera is working fine"+"\n\n";
    }
}