package com.example.mdt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Testgyroscope extends AppCompatActivity implements SensorEventListener {

    private static final int STORAGE_PERMISSION_REQUEST_CODE = 0;
    private static final int REQUEST_WRITE_PERMISSION = 0;
    Button save;
    DatabaseHelper databaseHelper;
    ArrayList arrayList;
    String s = "";
    private static final String TAG = "Testgyroscope";
    private SensorManager sensorManager;
    Sensor gyroscope;
    TextView xValue, yValue, zValue, tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testgyroscope);

        xValue = (TextView)findViewById(R.id.xValue);
        yValue = (TextView)findViewById(R.id.yValue);
        zValue = (TextView)findViewById(R.id.zValue);
        tv = (TextView)findViewById(R.id.tv);

        Log.d(TAG, "onCreate: Initializing Sensor Services");
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(Testgyroscope.this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "onCreate: Registered accelerometer listener");

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
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.d(TAG, "onSensorChanged: X: "+ sensorEvent.values[0]+"Y: "+sensorEvent.values[1]+ "Z: "+sensorEvent.values[2]);

        xValue.setText("X Value : "+ sensorEvent.values[0]);
        yValue.setText("Y Value : "+ sensorEvent.values[1]);
        zValue.setText("Z Value : "+ sensorEvent.values[2]);
        s = tv.getText().toString()+"\n"+" ✔ "+"Gyroscope is working fine"+"\n\n";
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i){

    }

}