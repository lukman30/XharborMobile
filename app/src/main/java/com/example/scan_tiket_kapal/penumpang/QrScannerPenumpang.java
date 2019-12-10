package com.example.scan_tiket_kapal.penumpang;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.example.scan_tiket_kapal.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrScannerPenumpang extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView qrScanner;
    private String remoteUrl;
    AQuery aQuery;
    String getEmail, getPwd,getlongi,getlati;
    private String source, hasil;
    private String dataHuruf1;
    String s_pre, s_preID;
    ProgressDialog pd;
    static final int berhasil = 1, mhswtidakada = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrscannerpenumpang);


        TextView tv = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Xharbor Penumpang");
        tv.setTextSize(20);
        tv.setTextColor(Color.parseColor("#FFFFFF"));
        Typeface tf = Typeface.createFromAsset(getAssets(), "font/NeoSansStdBlackTR.otf");
        tv.setTypeface(tf);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);

        qrScanner = new ZXingScannerView(this);
        setContentView(qrScanner);
        qrScanner.setResultHandler(this);
        qrScanner.startCamera();
        pd = new ProgressDialog(QrScannerPenumpang.this);


    }

    @Override
    protected void onPause() {
        super.onPause();
        qrScanner.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        String ambilresult = result.getText().toString();


//        System.out.println(result);



        Vibrator v;
        v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(600);
        qrScanner.resumeCameraPreview(this);
        finish();


        Intent update = new Intent(this, HasilScanPenumpang.class);
        update.putExtra("update",1);
        update.putExtra("idtiket",ambilresult);


        startActivity(update);


//        } else {
//            Vibrator v;
//            v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
//
//            v.vibrate(600);
//            Toast.makeText(getApplicationContext(), "Pastikan anda scan QR CODE Presensi", Toast.LENGTH_LONG).show();
//            finish();
//        }
    }




}
