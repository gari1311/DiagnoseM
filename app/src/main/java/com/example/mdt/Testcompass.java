package com.example.mdt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Testcompass extends AppCompatActivity implements SensorEventListener {

    private static final int STORAGE_PERMISSION_REQUEST_CODE = 0;
    private static final int REQUEST_WRITE_PERMISSION = 0;
    Button save;
    ImageView img_compass;
    TextView txt_azimuth, tv;
    int mAzimuth;
    private SensorManager mSensorManager;
    private Sensor mRotationV, mAccelerometer, mMagnetometer;
    float[] rMat = new float[9];
    float[] orientation = new float[9];
    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean haveSensor = false, haveSensor2 = false;
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;
    DatabaseHelper databaseHelper;
    ArrayList arrayList;
    String s = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testcompass);

        databaseHelper = new DatabaseHelper(this);
        arrayList = databaseHelper.getAllText();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        img_compass = (ImageView) findViewById(R.id.img_compass);
        txt_azimuth = (TextView) findViewById(R.id.txt_azimuth);
        tv = (TextView) findViewById(R.id.tv);
        save = (Button) findViewById(R.id.save);
        start();
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

    public void start() {
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)==null){
            if(mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)==null || mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)==null){
                mSensorAlert();
            }
            else{
                mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                haveSensor = mSensorManager.registerListener(this, mAccelerometer,SensorManager.SENSOR_DELAY_UI);
                haveSensor2 = mSensorManager.registerListener(this, mMagnetometer,SensorManager.SENSOR_DELAY_UI);
            }
        }
        else{
           mRotationV = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            haveSensor = mSensorManager.registerListener(this, mRotationV,SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void mSensorAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Your device doesn't support Compass").setCancelable(false)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
    }

    public void stop(){
        if(haveSensor && haveSensor2){
            mSensorManager.unregisterListener(this, mAccelerometer);
            mSensorManager.unregisterListener(this, mMagnetometer);
        }
        else{
            if(haveSensor){
                mSensorManager.unregisterListener(this, mRotationV);
            }
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        stop();
    }

    @Override
    protected void onResume(){
        super.onResume();
        start();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
            SensorManager.getRotationMatrixFromVector(rMat,event.values);
            mAzimuth = (int) ((Math.toDegrees(SensorManager.getOrientation(rMat,orientation)[0])+360)%360);

        }
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            System.arraycopy(event.values,0,mLastAccelerometer,0,event.values.length);
            mLastAccelerometerSet= true;

        }
        else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                System.arraycopy(event.values,0,mLastMagnetometer,0,event.values.length);
                mLastMagnetometerSet= true;
            }
        if(mLastMagnetometerSet && mLastAccelerometerSet){
            SensorManager.getRotationMatrix(rMat, null,mLastAccelerometer,mLastMagnetometer);
            SensorManager.getOrientation(rMat,orientation);
            mAzimuth = (int) ((Math.toDegrees(SensorManager.getOrientation(rMat,orientation)[0])+360)%360);
        }

        mAzimuth = Math.round(mAzimuth);
        img_compass.setRotation(-mAzimuth);

        String where = "NO";
        if(mAzimuth >= 350 || mAzimuth <= 10)
            where = "N";
        if(mAzimuth < 350 && mAzimuth > 280)
            where = "NW";
        if(mAzimuth <= 280 && mAzimuth > 260)
            where = "W";
        if(mAzimuth <= 260 && mAzimuth > 190)
            where = "SW";
        if(mAzimuth <= 190 && mAzimuth > 170)
            where = "S";
        if(mAzimuth <= 170 && mAzimuth > 100)
            where = "SE";
        if(mAzimuth <= 100 && mAzimuth > 80)
            where = "E";
        if(mAzimuth <= 350 && mAzimuth > 10)
            where = "NE";

        txt_azimuth.setText(mAzimuth+"° "+where);
        s = tv.getText().toString()+"\n"+" ✔ "+"Compass is working fine"+"\n\n";

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}