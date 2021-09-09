package com.example.mdt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Fingerprint extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 0;
    private static final int REQUEST_WRITE_PERMISSION = 0;
    Button save;
    DatabaseHelper databaseHelper;
    ArrayList arrayList;
    String s = "";
    Button btn11;
    TextView tv, tv50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);
        btn11 = (Button)findViewById(R.id.btn11);
        tv = (TextView)findViewById(R.id.tv);
        tv50 = (TextView)findViewById(R.id.tv50);

        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if we're running on Android 6.0 (M) or higher
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //Fingerprint API only available on from Android 6.0 (M)
                    FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
                    if (!fingerprintManager.isHardwareDetected()) {
                        // Device doesn't support fingerprint authentication
                        tv.setText("Device doesn't support fingerprint authentication");
                        s = tv50.getText().toString()+"\n"+" X "+"Fingerprint Sensor is not working fine"+"\n\n";
                        Toast.makeText(Fingerprint.this,"X Test Failed", Toast.LENGTH_LONG).show();
                    } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                        // User hasn't enrolled any fingerprints to authenticate with
                        tv.setText("User hasn't enrolled any fingerprints");
                        s = tv50.getText().toString()+"\n"+" ✔ "+"Fingerprint Sensor is working fine"+"\n\n";
                        Toast.makeText(Fingerprint.this,"✔ Test Passed but fingerprint not enrolled", Toast.LENGTH_LONG).show();
                    } else {
                        // Everything is ready for fingerprint authentication
                        tv.setText("Fingerprint Sensor is working fine and user has also enrolled the fingerprint(s)");
                        s = tv50.getText().toString()+"\n"+" ✔ "+"Fingerprint Sensor is working fine"+"\n\n";
                        Toast.makeText(Fingerprint.this,"✔ Test Passed", Toast.LENGTH_LONG).show();
                    }
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