package com.example.scan_tiket_kapal.config;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.scan_tiket_kapal.Home;
import com.example.scan_tiket_kapal.Login;
import com.example.scan_tiket_kapal.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SettingIPAwal extends Activity implements View.OnClickListener {
    TextView simpan;
    EditText ip;



    public static final String IPAPLIKASI = "IPAPLIKASI";
    public static final String KEYAPLIKASI = "KEYAPLIKASI";





    private SharedPreferences ipaplikasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        simpan=(TextView)findViewById(R.id.simpan);

        simpan.setOnClickListener(this);
        ip=(EditText)findViewById(R.id.ip);


        ipaplikasi = getSharedPreferences(
                SettingIp.IPAPLIKASI,
                Context.MODE_PRIVATE +
                        Context.MODE_PRIVATE | Context.MODE_PRIVATE);
        ip.setText(ipaplikasi.getString(
                SettingIp.KEYAPLIKASI, "192.xxx.xxx.xxx"));




    }

    @Override
    public void onClick(View v) {

        ipaplikasi = getSharedPreferences(SettingIp.IPAPLIKASI,
                Context.MODE_PRIVATE + Context.MODE_PRIVATE
                        | Context.MODE_PRIVATE);
        SharedPreferences.Editor worldReadWriteEdit = ipaplikasi.edit();
        worldReadWriteEdit.putString(SettingIp.KEYAPLIKASI, ip.getText()
                .toString());
        worldReadWriteEdit.commit();

        berhasil();

//        startActivity(new Intent(SettingIp.this, Home.class));
    }


    public void berhasil() {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Informasi")
//                .setCancelText("Cancel")
                .setContentText("IP Berhasil Dirubah !!!")
                .setConfirmText("Oke")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        finish();
                        startActivity(new Intent(SettingIPAwal.this, Login.class));

                    }
                })
                .show();
    }
}