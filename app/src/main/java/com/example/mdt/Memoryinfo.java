package com.example.mdt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;

public class Memoryinfo extends AppCompatActivity {

    TextView mTvPercRam, mTvPercRom;
    ProgressBar mPBRam, mPBRom;
    TextView TV4, TV6, tvram, tvrom;
    Button report, save;
    MemoryInfoDatabaseHelper databaseHelper;
    ArrayList arrayList;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 0;
    private static final int REQUEST_WRITE_PERMISSION = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memoryinfo);
        //String totalInternalMemory = formatSize(getTotalInternalMemorySize());
        //String availableInternalMemory = formatSize(getAvailableInternalMemorySize());
        //String usedInternalMemory = formatSize(getTotalInternalMemorySize()-getAvailableInternalMemorySize());
        //String totalRAM = formatSize(totalRamMemorySize());
        //String availableRAM = formatSize(freeRamMemorySize());
        //String usedRAM = formatSize(totalRamMemorySize()-freeRamMemorySize());
        save = (Button)findViewById(R.id.save);
        mTvPercRam = findViewById(R.id.tv_perc_ram);
        mTvPercRom = findViewById(R.id.tv_perc_rom);
        mPBRam = findViewById(R.id.pbRAM);
        mPBRom = findViewById(R.id.pbROM);
        TV4 = (TextView)findViewById(R.id.TV4);
        TV6 = (TextView)findViewById(R.id.TV6);
        tvram = (TextView)findViewById(R.id.tvram);
        tvrom = (TextView)findViewById(R.id.tvrom);
        report = (Button)findViewById(R.id.report);
        ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        float totalMem = memoryInfo.totalMem/(1024*1024);
        float freeMem = memoryInfo.availMem/(1024*1024);
        float usedMem = totalMem -freeMem;
        float freeMemPerc = freeMem/totalMem*100;
        float usedMemPerc = usedMem/totalMem*100;
        NumberFormat numFormFreePerc = NumberFormat.getNumberInstance();
        numFormFreePerc.setMinimumFractionDigits(1);
        numFormFreePerc.setMaximumFractionDigits(1);
        String mFreeMemPerc = numFormFreePerc.format(freeMemPerc);
        NumberFormat numFormUsedPerc = NumberFormat.getNumberInstance();
        numFormFreePerc.setMinimumFractionDigits(2);
        numFormFreePerc.setMinimumFractionDigits(2);
        String mUsedMemPerc = numFormFreePerc.format(usedMemPerc);

        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        float blockSize = stat.getBlockSize();
        float totalBlocks = stat.getBlockCount();
        float availableBlocks = stat.getAvailableBlocks();
        float totalROM =(totalBlocks*blockSize)/ (1024*1024);
        float freeROM = (availableBlocks*blockSize)/ (1024*1024);
        float usedROM = totalROM - freeROM;
        float freeROMPerc = (freeROM/totalROM)*100;
        float usedROMPerc = (usedROM/totalROM)*100;
        NumberFormat numFormatTotalROM = NumberFormat.getNumberInstance();
        numFormatTotalROM.setMinimumFractionDigits(2);
        numFormatTotalROM.setMaximumFractionDigits(2);
        String mTotalROM = numFormFreePerc.format(totalROM);
        NumberFormat numFormatFreeROM = NumberFormat.getNumberInstance();
        numFormatFreeROM.setMinimumFractionDigits(2);
        numFormatFreeROM.setMaximumFractionDigits(2);
        String mFreeROM = numFormFreePerc.format(freeROM);
        NumberFormat numFormatUsedROM = NumberFormat.getNumberInstance();
        numFormatUsedROM.setMinimumFractionDigits(2);
        numFormatUsedROM.setMaximumFractionDigits(2);
        String mUsedROM = numFormFreePerc.format(usedROM);

        NumberFormat numFormatFreeROMperc = NumberFormat.getNumberInstance();
        numFormatFreeROMperc.setMinimumFractionDigits(2);
        numFormatFreeROMperc.setMaximumFractionDigits(2);
        String mFreeROMperc = numFormFreePerc.format(freeROMPerc);
        NumberFormat numFormatUsedROMperc = NumberFormat.getNumberInstance();
        numFormatUsedROMperc.setMinimumFractionDigits(2);
        numFormatUsedROMperc.setMaximumFractionDigits(2);
        String mUsedROMperc = numFormFreePerc.format(usedROMPerc);


        TV6.setText( " ✔ "+" Total : "+ totalMem+ " MB"+ "\n"+
                " ✔ "+" Available : "+ freeMem+ " MB" + "("+ mFreeMemPerc + "%)" +"\n"+
                " ✔ "+" Used :"+ usedMem + "MB" + "("+ mUsedMemPerc + "%)");
        mTvPercRam.setText(mUsedMemPerc+ "% \n"+"Used");
        mPBRam.setProgress((int)usedMemPerc);
        TV4.setText(" ✔ "+" Total : "+ totalROM + "MB" + "\n"+
                " ✔ "+" Available : "+ freeROM + "MB" + "("+ mFreeROMperc + "%)" +"\n"+
                " ✔ "+" Used : "+ usedROM + "MB" + "("+ mUsedROMperc + "%)");
        mTvPercRom.setText(mUsedROMperc+ "% \n"+"Used");
        mPBRom.setProgress((int) ((usedROM/totalROM)*100));

        databaseHelper = new MemoryInfoDatabaseHelper(this);
        arrayList = databaseHelper.getAllText();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = tvram.getText().toString()+"\n"+TV4.getText().toString()+
                        "\n\n"+tvrom.getText().toString()+"\n"+TV6.getText().toString();
                if (!text.isEmpty()){
                    if(databaseHelper.addText(text)){
                        Toast.makeText(getApplicationContext(), "Data Saved...", Toast.LENGTH_SHORT).show();
                        arrayList.clear();;
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



    /*public static float getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        float BlockSize = stat.getBlockSize();
        float TotalBlocks = stat.getBlockCount();
        return TotalBlocks * BlockSize;
    }

    public static float getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        float blockSize = stat.getBlockSize();
        float availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }*/

    private long freeRamMemorySize() {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long availableMegs = mi.availMem / 1048576L;

        return availableMegs;
    }

    private long totalRamMemorySize() {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long availableMegs = mi.totalMem / 1048576L;
        return availableMegs;
    }

    public void createMyPDF(View view){

        PdfDocument myPdfDocument = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(300,600,1).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);

        Paint myPaint = new Paint();
        String myString = arrayList.toString();
        int x = 10, y=25;

        for (String line:myString.split("\n")){
            myPage.getCanvas().drawText(line, x, y, myPaint);
            y+=myPaint.descent()-myPaint.ascent();
        }

        myPdfDocument.finishPage(myPage);

        String myFilePath = Environment.getExternalStorageDirectory().getPath() + "/MemoryInfo.pdf";
        File myFile = new File(myFilePath);
        try {
            myPdfDocument.writeTo(new FileOutputStream(myFile));
        }
        catch (Exception e){
            e.printStackTrace();
            TV4.setText("ERROR");
            TV6.setText("ERROR");
            tvram.setText("ERROR");
            tvrom.setText("ERROR");
        }

        myPdfDocument.close();
    }

    /*public static String formatSize(float size) {
        String suffixSize = null;

        if (size >= 1024) {
            suffixSize = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffixSize = "MB";
                size /= 1024;
                if (size>=1024){
                    suffixSize = "GB";
                    size /=1024;
                }
            }
        }

        StringBuilder BufferSize = new StringBuilder(
                Float.toString(size));

        int commaOffset = BufferSize.length() - 3;
        while (commaOffset > 0) {
            BufferSize.insert(commaOffset, ',');
            commaOffset -= 3;
        }

        if (suffixSize != null) BufferSize.append(suffixSize);
        return BufferSize.toString();
    }*/
}