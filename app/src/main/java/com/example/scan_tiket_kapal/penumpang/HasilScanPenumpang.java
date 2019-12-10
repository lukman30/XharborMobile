/*
 * Copyright (c) UPT Pusat Data dan Informasi Created by : Moh. Lukman Sholeh ,S.Kom
 */

package com.example.scan_tiket_kapal.penumpang;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.scan_tiket_kapal.Login;
import com.example.scan_tiket_kapal.R;
import com.example.scan_tiket_kapal.config.AppControler;
import com.example.scan_tiket_kapal.config.Server;
import com.example.scan_tiket_kapal.config.SettingIp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HasilScanPenumpang extends AppCompatActivity {
    String  ambilid;

    TelephonyManager telephonyManager;
    int success;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String ambilusername;
    String nama,ktp,url;
    SharedPreferences username,ipaplikasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hasilscanpenumpang);
        Intent data = getIntent();
        final int update = data.getIntExtra("update", 0);
        ambilid = data.getStringExtra("idtiket");
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        ipaplikasi = getSharedPreferences(
                SettingIp.IPAPLIKASI,
                Context.MODE_PRIVATE +
                        Context.MODE_PRIVATE | Context.MODE_PRIVATE);
        url="http://"+ipaplikasi.getString(
                SettingIp.KEYAPLIKASI, "192.xxx.xxx.xxx")+"/xharbor/";

//        IMEI_Number_Holder = telephonyManager.getDeviceId();
//
//
//        today = new Time(Time.getCurrentTimezone());
//        today.setToNow();
//


        username = getSharedPreferences(
                Login.SATU,
                Context.MODE_PRIVATE +
                        Context.MODE_PRIVATE | Context.MODE_PRIVATE);
        ambilusername = username.getString(
                Login.KEY_SATU, "username");



        if (update == 1) {
            CekTiket();

        }

    }



    private void CekTiket() {
        //menampilkan progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Loading...", " Mohon Tunggu...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+ "web_service/cektiket.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);






                            if (success == 1) {
                                berhasil();
                            }else if (success==2){
                                nama=jObj.getString("nama");
                                ktp=jObj.getString("ktp");
                                sudah();

                            }else if (success==4){
                                tidakditemukan();
                            }else if (success==0){
                                Toast.makeText(getApplicationContext(), "Scan Gagal!!", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.dismiss();


                        Toast.makeText(getApplicationContext(), "Maaf Ada Kesalahan!!", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("idtiket", ambilid);
                params.put("username", ambilusername);


                return params;
            }
        };

        AppControler.getInstance().addToRequestQueue(stringRequest, "json");
    }


    public void berhasil() {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Informasi")
                .setCancelText("Cancel")
                .setContentText("Status Tiket Berhasil Dirubah!!!")
                .setConfirmText("Oke")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        finish();

                    }
                })
                .show();
    }

    public void sudah() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Informasi")
//                .setCancelText("Cancel")
                .setContentText("Maaf,tiket sudah diScan !!!\n  \n  Nama : "+nama+"\n No.Ktp : "+ktp)
                .setConfirmText("Oke")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        finish();

                    }
                })
                .show();
    }

    public void tidakditemukan() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Informasi")
//                .setCancelText("Cancel")
                .setContentText("Maaf,Data tiket tidak ditemukan!!!")
                .setConfirmText("Oke")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        finish();

                    }
                })
                .show();
    }
}