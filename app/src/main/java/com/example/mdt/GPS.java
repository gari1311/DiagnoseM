package com.example.mdt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.Telephony;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GPS extends AppCompatActivity {

    Button btn16;
    TextView tv61;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_p_s);
        btn16 = (Button) findViewById(R.id.btn16);
        tv61 = (TextView) findViewById(R.id.tv61);

        btn16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent I1 = new Intent(GPS.this, Testgps.class);
               startActivity(I1);
            }
        });

    }


}