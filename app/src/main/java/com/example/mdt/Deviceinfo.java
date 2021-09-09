package com.example.mdt;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Deviceinfo extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_REQUEST_CODE = 0;
    private static final int REQUEST_WRITE_PERMISSION = 0;
    Button report, save;
    TextView textView;
    TextView tv89;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;
    private static final int BUFFER_SIZE = 100;
    DeviceInfoDatabaseHelper databaseHelper;
    ArrayList arrayList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deviceinfo);
        databaseHelper = new DeviceInfoDatabaseHelper(this);

        arrayList = databaseHelper.getAllText();
        loadIMEI();
        textView = (TextView)findViewById(R.id.TV3);
        save = (Button)findViewById(R.id.save);
        String  details = " ✔ "+"DEVICE NAME : "+Build.DEVICE
                +"\n\n"+" ✔ "+"ANDROID VERSION : "+ Build.VERSION.RELEASE
                //+"\n\n"+" ✔ "+"VERSION.INCREMENTAL : "+Build.VERSION.INCREMENTAL
                +"\n\n"+" ✔ "+"VERSION.SDK.NUMBER : "+Build.VERSION.SDK_INT
                +"\n\n"+" ✔ "+"IMEI Number1 :" + doPermissionGrantedStuffs1()
                +"\n\n"+" ✔ "+"IMEI Number2 :" + doPermissionGrantedStuffs2()
                +"\n\n"+" ✔ "+"Kernal Version :" + readKernelVersion()
                +"\n\n"+" ✔ "+"BOARD : "+Build.BOARD
                +"\n\n"+" ✔ "+"BOOTLOADER : "+Build.BOOTLOADER
                +"\n\n"+" ✔ "+"BRAND : "+Build.BRAND
                //+"\n\n"+" ✔ "+"CPU_ABI : "+Build.CPU_ABI
                +"\n\n"+" ✔ "+"DISPLAY : "+Build.DISPLAY
                +"\n\n"+" ✔ "+"FINGERPRINT : "+Build.FINGERPRINT
                +"\n\n"+" ✔ "+"HARDWARE : "+Build.HARDWARE
                +"\n\n"+" ✔ "+"HOST : "+Build.HOST
                +"\n\n"+" ✔ "+"ANDROID ID : "+Build.ID
                +"\n\n"+" ✔ "+"MANUFACTURER : "+Build.MANUFACTURER
                +"\n\n"+" ✔ "+"MODEL : "+Build.MODEL
                +"\n\n"+" ✔ "+"PRODUCT : "+Build.PRODUCT
                +"\n\n"+" ✔ "+"SERIAL : "+Build.SERIAL
               // +"\n\n"+" ✔ "+"TAGS : "+Build.TAGS
                +"\n\n"+" ✔ "+"TIME : "+Build.TIME
                +"\n\n"+" ✔ "+"TYPE : "+Build.TYPE
                +"\n\n"+" ✔ "+"USER : "+Build.USER;

        textView.setText(details);
        report = (Button) findViewById(R.id.report);
        tv89 = (TextView) findViewById(R.id.tv89);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = textView.getText().toString();
                if (!text.isEmpty()){
                    if(databaseHelper.addText(text)){
                        Toast.makeText(getApplicationContext(), "Data Saved...", Toast.LENGTH_SHORT).show();
                        arrayList.clear();
                        arrayList.addAll(databaseHelper.getAllText());
                    }
                }
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMyPDF(view);
            }
        });
    }
    
    public void createMyPDF(View view){

        PdfDocument myPdfDocument = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(300,600,1).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);

        Paint myPaint = new Paint();
        String myString = "";
        int size = arrayList.size();

        for(int i = 0; i<size; i++){
            myString = myString+ arrayList.get(i);
        }
        //String myString = arrayList.toString();
        int x = 10, y=25;

        for (String line:myString.split("\n")){
            myPage.getCanvas().drawText(line, x, y, myPaint);
            y+=myPaint.descent()-myPaint.ascent();
        }

        myPdfDocument.finishPage(myPage);

        String myFilePath = Environment.getExternalStorageDirectory().getPath() + "/DeviceInfo.pdf";
        File myFile = new File(myFilePath);
        try {
            myPdfDocument.writeTo(new FileOutputStream(myFile));
            Toast.makeText(getApplicationContext(), "Report Generated..", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
            textView.setText("ERROR");
        }

        myPdfDocument.close();
    }

    public static String readKernelVersion() {
        try {
            Process p = Runtime.getRuntime().exec("uname -a");
            InputStream is = null;
            if (p.waitFor() == 0) {
                is = p.getInputStream();
            } else {
                is = p.getErrorStream();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is),
                    BUFFER_SIZE);
            String line = br.readLine();
            br.close();
            return line;
        } catch (Exception ex) {
            return "ERROR: " + ex.getMessage();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void loadIMEI() {
        // Check if the READ_PHONE_STATE permission is already available.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // READ_PHONE_STATE permission has not been granted.
            requestReadPhoneStatePermission();
        } else {
            // READ_PHONE_STATE permission is already been granted.
            doPermissionGrantedStuffs1();
            doPermissionGrantedStuffs2();
        }
    }

    /**
     * Requests the READ_PHONE_STATE permission.
     * If the permission has been denied previously, a dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    private void requestReadPhoneStatePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            new AlertDialog.Builder(Deviceinfo.this)
                    .setTitle("Permission Request")
                    .setMessage("Permission read phone state rationale")
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //re-request
                            ActivityCompat.requestPermissions(Deviceinfo.this,
                                    new String[]{Manifest.permission.READ_PHONE_STATE},
                                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                        }
                    })
                    .show();
        } else {
            // READ_PHONE_STATE permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            // Received permission result for READ_PHONE_STATE permission.est.");
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // READ_PHONE_STATE permission has been granted, proceed with displaying IMEI Number
                //alertAlert(getString(R.string.permision_available_read_phone_state));
                doPermissionGrantedStuffs1();
                doPermissionGrantedStuffs2();
            } else {
                alertAlert("Permission Not Granted");
            }
        }
    }

    private void alertAlert(String msg) {
        new AlertDialog.Builder(Deviceinfo.this)
                .setTitle("Permission Request")
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do somthing here
                    }
                })
                .show();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public String doPermissionGrantedStuffs1() {
        //Have an  object of TelephonyManager
        TelephonyManager tm =(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        //Get IMEI Number of Phone  //////////////// for this example i only need the IMEI
        String IMEINumber1=tm.getImei(0);
        //String IMEINumber2=tm.getImei(1);
        return IMEINumber1;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String doPermissionGrantedStuffs2() {
        //Have an  object of TelephonyManager
        TelephonyManager tm =(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        //Get IMEI Number of Phone  //////////////// for this example i only need the IMEI
        //String IMEINumber1=tm.getImei(0);
        String IMEINumber2=tm.getImei(1);
        return IMEINumber2;
    }

}