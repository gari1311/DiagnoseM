package com.example.mdt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView iv1, iv2, iv3, iv4, iv5,iv6;
    Button btn1, report;
    DatabaseHelper databaseHelper;
    ArrayList arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv1 = (ImageView)findViewById(R.id.iv1);
        iv2 = (ImageView)findViewById(R.id.iv2);
        iv3 = (ImageView)findViewById(R.id.iv3);
        iv4 = (ImageView)findViewById(R.id.iv4);
        iv5 = (ImageView)findViewById(R.id.iv5);
        iv6 = (ImageView)findViewById(R.id.iv6);
        btn1 = (Button)findViewById(R.id.btn1);
        report = (Button)findViewById(R.id.report);
        databaseHelper = new DatabaseHelper(this);
        arrayList = databaseHelper.getAllText();

        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(MainActivity.this, Position.class);
                startActivity(i1);
            }
        });

        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(MainActivity.this, Motion.class);
                startActivity(i2);
            }
        });

        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3 = new Intent(MainActivity.this, Sound.class);
                startActivity(i3);
            }
        });

        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i4 = new Intent(MainActivity.this, Hardware.class);
                startActivity(i4);
            }
        });

        iv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i5 = new Intent(MainActivity.this, Connectivity.class);
                startActivity(i5);
            }
        });

        iv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i6 = new Intent(MainActivity.this, Camera.class);
                startActivity(i6);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i27 = new Intent(MainActivity.this, Phoneinfo.class);
                startActivity(i27);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PdfDocument myPdfDocument = new PdfDocument();
                PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(300,1500,1).create();
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

                String myFilePath = Environment.getExternalStorageDirectory().getPath() + "/TestReport.pdf";
                File myFile = new File(myFilePath);
                try {
                    myPdfDocument.writeTo(new FileOutputStream(myFile));
                    Toast.makeText(getApplicationContext(), "Report Generated..", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    e.printStackTrace();

                }

                myPdfDocument.close();
            }
        });
    }
}