package com.example.mdt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Testvibration extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_REQUEST_CODE = 0;
    private static final int REQUEST_WRITE_PERMISSION = 0;
    Button save;
    DatabaseHelper databaseHelper;
    ArrayList arrayList;
    String s = "";
    EditText et;
    Button btn;
    TextView tv, tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testvibration);
        et = (EditText)findViewById(R.id.et);
        btn = (Button)findViewById(R.id.btn);
        tv = (TextView) findViewById(R.id.tv);
        tv1 = (TextView) findViewById(R.id.tv1);

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Start without a delay
        // Each element then alternates between vibrate, sleep, vibrate, sleep...
        long[] pattern = {0, 100, 1000, 300, 1000, 300, 500, 1000, 100};
        v.vibrate(pattern, -1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(et.getText().toString())==4){
                    tv.setText("Test Passed");
                    s = tv1.getText().toString()+"\n"+" ✔ "+"Vibration is working fine"+"\n\n";
                }
                else{
                    tv.setText("Test Failed");
                    s = tv1.getText().toString()+"\n"+" X "+"Vibration is not working fine"+"\n\n";
                }
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
}