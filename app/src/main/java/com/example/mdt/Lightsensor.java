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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Lightsensor extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_REQUEST_CODE = 0;
    private static final int REQUEST_WRITE_PERMISSION = 0;
    Button save;
    DatabaseHelper databaseHelper;
    ArrayList arrayList;
    String s = "";
    Button btn20;
    TextView TV, tv70;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lightsensor);
        btn20 = (Button)findViewById(R.id.btn20);
        TV = (TextView)findViewById(R.id.TV);
        tv70 = (TextView)findViewById(R.id.tv70);
        btn20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SensorManager mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

                Sensor lightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                if(lightSensor != null){
                    TV.setText("Sensor.TYPE_LIGHT Available");
                    Toast.makeText(getApplicationContext()," ✔ "+"Light Sensor is working fine",Toast.LENGTH_LONG).show();
                    s = tv70.getText().toString()+"\n"+" ✔ "+"Light Sensor is working fine"+"\n\n";
                    mySensorManager.registerListener(
                            lightSensorListener,
                            lightSensor,
                            SensorManager.SENSOR_DELAY_NORMAL);

                } else {
                    TV.setText("Sensor.TYPE_LIGHT NOT Available");
                    Toast.makeText(getApplicationContext()," X "+"Device doesn't support light sensor",Toast.LENGTH_LONG).show();
                    s = tv70.getText().toString()+"\n"+" X "+"Device doesn't support light sensor"+"\n\n";
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

    private final SensorEventListener lightSensorListener
            = new SensorEventListener(){

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_LIGHT){
                TV.setText("LIGHT: " + event.values[0]);
            }
        }

    };
}