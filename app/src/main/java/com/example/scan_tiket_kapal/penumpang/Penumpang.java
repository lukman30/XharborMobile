package com.example.scan_tiket_kapal.penumpang;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.scan_tiket_kapal.Login;
import com.example.scan_tiket_kapal.R;
import com.example.scan_tiket_kapal.config.AppControler;
import com.example.scan_tiket_kapal.config.ModelData;
import com.example.scan_tiket_kapal.config.Server;
import com.example.scan_tiket_kapal.config.SettingIp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Penumpang extends AppCompatActivity {

    RecyclerView mRecyclerview;
    RecyclerView.Adapter mAdapter;
    List<ModelData> mItems;
    Button btnInsert, btnDelete;
    private Button tampil;
    ProgressDialog pd;
    RecyclerView.LayoutManager mManager;
    SharedPreferences iduser,ipaplikasi;
    String ambiliduser;

    ImageView img;
    TextView label;

    Intent data1;
    String tipe,tanggal;
    EditText pencarian;

    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.penumpang);

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
        getSupportActionBar().setElevation(0);
        mRecyclerview = (RecyclerView)findViewById(R.id.recyclerviewTemp);
        pd = new ProgressDialog(this);


        ipaplikasi = getSharedPreferences(
                SettingIp.IPAPLIKASI,
                Context.MODE_PRIVATE +
                        Context.MODE_PRIVATE | Context.MODE_PRIVATE);
        url="http://"+ipaplikasi.getString(
                SettingIp.KEYAPLIKASI, "192.xxx.xxx.xxx")+"/xharbor/";


        pencarian=(EditText) findViewById(R.id.search_text);

        pencarian.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mItems.clear();
                    MengambilData();
                    return true;
                }
                return false;
            }
        });


        mItems = new ArrayList<>();
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerview.setLayoutManager(mManager);
        mAdapter = new AdapterPenumpang(this, mItems);
        mRecyclerview.setAdapter(mAdapter);

        iduser =getSharedPreferences(
                Login.SATU,
                Context.MODE_PRIVATE +
                        Context.MODE_PRIVATE | Context.MODE_PRIVATE);
        ambiliduser = (iduser.getString(
                Login.KEY_SATU, "NA"));
        img = (ImageView) findViewById(R.id.img);
        label = (TextView) findViewById(R.id.label);

        data1 = getIntent();
        final int update = data1.getIntExtra("update", 0);
        tipe = data1.getStringExtra("tipe");
        tanggal = data1.getStringExtra("tanggal");




//        if (update == 1) {
        MengambilData();
//        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Penumpang.this, QrScannerPenumpang.class));
            }
        });



    }

    private void MengambilData() {
        pd.setMessage("Please Wait...");
        pd.setCancelable(true);
        pd.show();
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET,
                url + "web_service/listpenumpang.php?cari="+pencarian.getText().toString(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ModelData md = new ModelData();
                                md.setKdbooking(data.getString("kdbooking"));
                                md.setNama(data.getString("nama"));
                                md.setStatus(data.getString("status"));
                                md.setUmur(data.getString("umur"));


                                mItems.add(md);

                                if (data.getString("message") == "kosong"|| data.getString("message").equals("kosong")) {
                                    img.setVisibility(View.VISIBLE);
                                    label.setVisibility(View.VISIBLE);
                                } else {
                                    img.setVisibility(View.GONE);
                                    label.setVisibility(View.GONE);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
//                                System.out.println("data kosong");

                            }
                        }

                        mAdapter.notifyDataSetChanged();
                        pd.cancel();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Toast.makeText(Penumpang.this, "Server Down", Toast.LENGTH_LONG).show();
                    }
                });

        AppControler.getInstance().addToRequestQueue(reqData, "json");
    }



}
