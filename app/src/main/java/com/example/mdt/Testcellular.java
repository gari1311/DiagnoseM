package com.example.mdt;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.kirianov.multisim.MultiSimTelephonyManager;

import java.util.ArrayList;

public class Testcellular extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_REQUEST_CODE = 0;
    private static final int REQUEST_WRITE_PERMISSION = 0;
    Button save;
    DatabaseHelper databaseHelper;
    ArrayList arrayList;
    String s = "";
    TextView cell1txt, cell2txt, tv;
    static boolean isDouble;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testcellular);
        cell1txt = (TextView) findViewById(R.id.cell1txt);
        cell2txt = (TextView) findViewById(R.id.cell2txt);
        tv = (TextView) findViewById(R.id.tv);
        String mtyb = android.os.Build.BRAND;
        String mtype = android.os.Build.MODEL;

        // for DualSimManager
        DualSimManager info = new DualSimManager(this);

        String imsiSIM1 = info.getIMSI(0);
        String imsiSIM2 = info.getIMSI(1);

        boolean isRoaming1=info.isRoaming(0);
        boolean isRoaming2=info.isRoaming(1);

        String Signalstrength1 = info.getSIM_NETWORK_SIGNAL_STRENGTH(0);
        String Signalstrength2 = info.getSIM_NETWORK_SIGNAL_STRENGTH(1);

        String imeiSIM1 = info.getIMEI(0);
        String imeiSIM2 = info.getIMEI(1);

        boolean isSIM1Ready = info.isFirstSimActive();
        boolean isSIM2Ready = info.isSecondSimActive();

        String operatorSIM1 = info.getNETWORK_OPERATOR_NAME(0);
        String operatorSIM2 = info.getNETWORK_OPERATOR_NAME(1);
        String NetworkSIM1 = info.getNETWORK_TYPE(0);
        String NetworkSIM2 = info.getNETWORK_TYPE(1);

        String simowner1 = info.getmSimOperatorName(0);
        String simowner2 = info.getmSimOperatorName(1);

        int Simcellid1 = info.getSIM_CELLID(0);
        int Simcellid2 = info.getSIM_CELLID(1);

        String Operator1 =info.getmSimOperatorName(0);
        String Operator2 =info.getmSimOperatorName(1);


        isDouble = info.isDualSIMSupported();

        s = tv.getText().toString()+"\n"+" ✔ "+"Cellular is working fine"+"\n\n";

        cell1txt.setText(
                        " ✔ "+" IMSI : " + imsiSIM1 + "\n" +
                        " ✔ "+" IMEI : " + imeiSIM1 + "\n" +
                        " ✔ "+" Roaming : " + isRoaming1 + "\n" +
                        " ✔ "+" Signal Strength  : " + Signalstrength1 + "\n" +
                        " ✔ "+" IS SIM1 READY : " + isSIM1Ready + "\n" +
                        " ✔ "+" Network SIM1 : " + NetworkSIM1 + "\n" +
                        " ✔ "+" Operator SIM1 : " + operatorSIM1 + "\n"+
                        " ✔ "+" Owner SIM1 : " + simowner1 + "\n"+
                        " ✔ "+" SIM1 Cell ID : " + Simcellid1 + "\n"
                );

        cell2txt.setText(
                        " ✔ "+" IMSI2 : " + imsiSIM2 + "\n" +
                        " ✔ "+" IMEI2 : " + imeiSIM2 + "\n" +
                        " ✔ "+" Roaming2 : " + isRoaming2 + "\n" +
                        " ✔ "+" Signal Strength 2 : " + Signalstrength2 + "\n" +
                        " ✔ "+" IS SIM2 READY : " + isSIM2Ready + "\n" +
                        " ✔ "+" Network SIM2 : " + NetworkSIM2 + "\n" +
                        " ✔ "+" OperatorSIM2 : " + operatorSIM2 + "\n" +
                        " ✔ "+" Owner SIM2 : " + simowner2 + "\n"+
                        " ✔ "+" SIM2 Cell ID : " + Simcellid2 + "\n"+
                        " ✔ "+" SIM2 Operator Name : " + Operator2 + "\n"
        );

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}