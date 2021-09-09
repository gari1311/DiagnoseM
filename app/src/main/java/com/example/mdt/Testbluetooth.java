package com.example.mdt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Testbluetooth extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_REQUEST_CODE = 0;
    private static final int REQUEST_WRITE_PERMISSION = 0;
    Button save;
    DatabaseHelper databaseHelper;
    ArrayList arrayList;
    String s = "";
    TextView bluetoothStatusTv, tv;
    ImageView bluetoothStatus;
    Button bluetoothStatusBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testbluetooth);
        bluetoothStatus = (ImageView)findViewById(R.id.bluetoothstatusicon);
        bluetoothStatusTv = (TextView)findViewById(R.id.blueStatusTv);
        tv = (TextView)findViewById(R.id.tv);
        bluetoothStatusBtn = (Button)findViewById(R.id.bluetoothStatusBtn);

        bluetoothStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    Toast.makeText(getApplicationContext()," X "+"Device does not support bluetooth",Toast.LENGTH_LONG).show();
                    s = tv.getText().toString()+"\n"+" X "+"Device does not support bluetooth"+"\n\n";
                } else if (!mBluetoothAdapter.isEnabled())
                {
                    // Bluetooth is not enabled :)
                    bluetoothStatus.setImageResource(R.drawable.bluetoothdisabled);
                    bluetoothStatusTv.setText("Status : "+ "Bluetooth is disabled");
                    Toast.makeText(getApplicationContext()," ✔ "+"Bluetooth is working fine",Toast.LENGTH_LONG).show();
                    s = tv.getText().toString()+"\n"+" ✔ "+"Bluetooth is working fine"+"\n\n";
                } else {
                    // Bluetooth is enabled
                    bluetoothStatus.setImageResource(R.drawable.bluetoothenabled);
                    bluetoothStatusTv.setText("Status : "+ "Bluetooth is enabled");
                    Toast.makeText(getApplicationContext()," ✔ "+"Bluetooth is working fine",Toast.LENGTH_LONG).show();
                    s = tv.getText().toString()+"\n"+" ✔ "+"Bluetooth is working fine"+"\n\n";
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