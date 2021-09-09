package com.example.mdt;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Testnetwork extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_REQUEST_CODE = 0;
    private static final int REQUEST_WRITE_PERMISSION = 0;
    Button save;
    DatabaseHelper databaseHelper;
    ArrayList arrayList;
    String s = "";
    ImageView mConStatusIv;
    Button mConStatusBtn;
    TextView mConStatusTv, localip, publicip, mac, netid, speed, ssid, freq, region, tv;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testnetwork);

        mConStatusBtn = (Button)findViewById(R.id.conStatusBtn);
        mConStatusTv = (TextView)findViewById(R.id.conStatusTv);
        mConStatusIv = (ImageView)findViewById(R.id.networkstatusicon);
        localip = (TextView)findViewById(R.id.localip);
        publicip = (TextView)findViewById(R.id.publicip);
        mac = (TextView)findViewById(R.id.mac);
        netid = (TextView)findViewById(R.id.netid);
        speed = (TextView)findViewById(R.id.speed);
        ssid = (TextView)findViewById(R.id.ssid);
        freq = (TextView)findViewById(R.id.freq);
        tv = (TextView)findViewById(R.id.tv);

        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL myIp = new URL("https://checkip.amazonaws.com/");
            URLConnection c = myIp.openConnection();
            c.setConnectTimeout(1000);
            c.setReadTimeout(1000);
            BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
            publicip.setText("Public IP : "+ in.readLine());

        } catch (Exception e) {
            e.printStackTrace();
        }

        WifiManager ip = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String cip = Formatter.formatIpAddress(ip.getConnectionInfo().getIpAddress());
        WifiInfo wifiInfo = ip.getConnectionInfo();
        //String macad =Formatter.formatIpAddress(Integer.parseInt(ip.getConnectionInfo().getMacAddress()));
        int networkId = wifiInfo.getNetworkId();
        int linkspeed = wifiInfo.getLinkSpeed();
        localip.setText("Local IP : "+cip);
        mac.setText("MAC Address : "+wifiInfo.getMacAddress());
        speed.setText("Link Speed : "+ linkspeed);
        netid.setText("Network ID : "+networkId);
        ssid.setText("SSID : "+ wifiInfo.getSSID());
        freq.setText("WiFi Frequency : "+wifiInfo.getFrequency()+" MHz");

        s = tv.getText().toString()+"\n"+" ✔ "+"Network is working fine"+"\n\n";
        
        mConStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkNetworkConnectionStatus();
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

    private void checkNetworkConnectionStatus() {
        boolean wifiConnected;
        boolean mobileConnected;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeinfo = connectivityManager.getActiveNetworkInfo();
        if(activeinfo != null && activeinfo.isConnected()){
            wifiConnected = activeinfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeinfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if(wifiConnected){
                mConStatusIv.setImageResource(R.drawable.wifitesticon);
                mConStatusTv.setText("Status : "+"Connected with Wifi");
            }else if(mobileConnected){
                mConStatusIv.setImageResource(R.drawable.dataconnected);
                mConStatusTv.setText("Status : "+"Connected with Mobile Data");
            }
        }
        else{
            mConStatusIv.setImageResource(R.drawable.notconnected);
            mConStatusTv.setText("Status : "+"No Connection");
        }
    }
}