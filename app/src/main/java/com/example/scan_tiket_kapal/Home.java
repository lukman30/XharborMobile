package com.example.scan_tiket_kapal;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.scan_tiket_kapal.config.AppControler;
import com.example.scan_tiket_kapal.config.InternetDialog;
import com.example.scan_tiket_kapal.config.PrefManager;
import com.example.scan_tiket_kapal.config.Server;
import com.example.scan_tiket_kapal.config.SettingIp;
import com.example.scan_tiket_kapal.kargo.Kargo;
import com.example.scan_tiket_kapal.kargo.QrScanKargo;
import com.example.scan_tiket_kapal.penumpang.Penumpang;
import com.example.scan_tiket_kapal.penumpang.QrScannerPenumpang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.events.OnBannerClickListener;
import ss.com.bannerslider.views.BannerSlider;
import ss.com.bannerslider.views.indicators.IndicatorShape;

public class Home extends AppCompatActivity implements View.OnClickListener {

    private static final String NOTIFICATION_MSG = "NOTIFICATION MSG";

    private ProgressDialog pDialog;
    private static final String TAG_MESSAGE = "message";
    private SharedPreferences prefssatu, prefpassword;

    private FloatingActionButton fab;

    String ambilusername;


    private static final String TAG_MESSAGE1 = "message";
    private SharedPreferences prefssatu1, ipaplikasi;
    String alamat, pwd;


    Spinner spinner;
    SeekBar intervalSeekBar;
    private BannerSlider bannerSlider;
    SeekBar indicatorSizeSeekBar;
    SwitchCompat loopSlidesSwitch, mustAnimateIndicators;
    SwitchCompat hideIndicatorsSwitch;

    ProgressBar p1, p2, p3, p4;


    private PrefManager prefManager;
    int success;
    private static final String TAG_SUCCESS = "success";
    String tag_json_obj = "json_obj_req";

    String ip;

    TextView top;

    TextView ipserver;

    CardView scanpenumpang,scankargo,about;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        getSupportActionBar().setElevation(0);

        // CALL getInternetStatus() function to check for internet and display error dialog
        if(new InternetDialog(this).getInternetStatus()){
//            Toast.makeText(this, "INTERNET VALIDATION PASSED", Toast.LENGTH_SHORT).show();
        }

        prefssatu = this.getSharedPreferences(
                Login.SATU,
                Context.MODE_PRIVATE +
                        Context.MODE_PRIVATE | Context.MODE_PRIVATE);
        ambilusername = prefssatu.getString(
                Login.KEY_SATU, "NA");
        prefpassword = this.getSharedPreferences(
                Login.PASSOWRD,
                Context.MODE_PRIVATE +
                        Context.MODE_PRIVATE | Context.MODE_PRIVATE);
        pwd = prefpassword.getString(
                Login.KEY_PASSWORD, "NA");


        ipaplikasi =  this.getSharedPreferences(
                SettingIp.IPAPLIKASI,
                Context.MODE_PRIVATE +
                        Context.MODE_PRIVATE | Context.MODE_PRIVATE);
        ip=ipaplikasi.getString(
                SettingIp.KEYAPLIKASI, "Belum Disetting");

        ipserver=(TextView)findViewById(R.id.ipserver);
        if (ipserver.getText().toString()==""||ipserver.getText().toString().equals("")){
            ipserver.setText("Ip Server : Belum Disetting");
        }else{
            ipserver.setText("Ip Server : "+ip);
        }



        top=(TextView)findViewById(R.id.user) ;
        top.setText("Selamat Datang, "+ambilusername);


        scankargo=(CardView)findViewById(R.id.scankargo);
        scanpenumpang=(CardView)findViewById(R.id.scanpenumpang);
        about=(CardView)findViewById(R.id.about);

        scanpenumpang.setOnClickListener(this);
        scankargo.setOnClickListener(this);
        about.setOnClickListener(this);


        p1 = (ProgressBar) findViewById(R.id.p1);
//        p2 = (ProgressBar) findViewById(R.id.p2);
        bannerSlider = (BannerSlider) findViewById(R.id.banner_slider1);
        intervalSeekBar = (SeekBar) findViewById(R.id.seekbar_interval);
        spinner = (Spinner) findViewById(R.id.spinner_page_indicator);
        indicatorSizeSeekBar = (SeekBar) findViewById(R.id.seekbar_indicator_size);
        indicatorSizeSeekBar.setMax(getResources().getDimensionPixelSize(R.dimen.max_slider_indicator_size));
        loopSlidesSwitch = (SwitchCompat) findViewById(R.id.checkbox_loop_slides);
        mustAnimateIndicators = (SwitchCompat) findViewById(R.id.checkbox_animate_indicators);
        hideIndicatorsSwitch = (SwitchCompat) findViewById(R.id.checkbox_hide_indicators);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, SettingIp.class);
                startActivity(intent);
            }
        });


        TextView tv = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Xharbor");
        tv.setTextSize(20);
        tv.setTextColor(Color.parseColor("#FFFFFF"));
        Typeface tf = Typeface.createFromAsset(getAssets(), "font/NeoSansStdBlackTR.otf");
        tv.setTypeface(tf);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);


        ////informasi
        setupViews();
//        loadJson();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.logout:
                keluar();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void setupViews() {
//        setupToolbar();
        setupBannerSlider();
        setupPageIndicatorChooser();
        setupSettingsUi();
        addBanners();
    }

    private void setupSettingsUi() {

        intervalSeekBar.setMax(500);
        intervalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    bannerSlider.setInterval(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        indicatorSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    bannerSlider.setIndicatorSize(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        loopSlidesSwitch.setChecked(true);
        mustAnimateIndicators.setChecked(true);

        loopSlidesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                bannerSlider.setLoopSlides(b);
            }
        });

        mustAnimateIndicators.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                bannerSlider.setMustAnimateIndicators(b);
            }
        });


        hideIndicatorsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                bannerSlider.setHideIndicators(b);
            }
        });
    }


    private void setupBannerSlider() {

        bannerSlider.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void onClick(int position) {
//                Toast.makeText(Menu.this, "Banner with position " + String.valueOf(position) + " clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addBanners(){
        //Add banners using image urls
        bannerSlider.addBanner(new RemoteBanner(
                "https://i.imgsafe.org/9c/9c1dcb67aa.png"
        ));
        bannerSlider.addBanner(new RemoteBanner(
                "https://i.imgsafe.org/dc/dc74d9d025.jpeg"
        ));
        bannerSlider.addBanner(new RemoteBanner(
                "https://i.imgsafe.org/dc/dc74ea56f1.gif"
        ));
        bannerSlider.addBanner(new RemoteBanner(
                "https://i.imgsafe.org/dc/dc74e8900d.jpeg"
        ));

    }


    private void loadJson() {
//        pd.setMessage("Mengambil Data");
//        pd.setCancelable(false);
//        pd.show();
        p1.setVisibility(View.VISIBLE);

        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.POST, Server.URL + "web_service/slider.php", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        pd.cancel();
                        p1.setVisibility(View.GONE);
                        System.out.println(response);
                        if (response == null || response.equals("[]") || response.equals(null) || response.equals("")
                                || response.toString().equals("") || response.toString().equals("[]")) {


                        } else {
                            for (int i = 0; i < response.length(); i++) {
                                try {


                                    JSONObject data = response.getJSONObject(i);

//                                    bannerSlider.addBanner(new RemoteBanner(Server.URL + "assets/slider/" + data.getString("Slider")
//
//                                    ));

                                    System.out.println(Server.URL + "assets/slider/" + data.getString("image"));
                                    bannerSlider.addBanner(new RemoteBanner(
                                            Server.URL + "assets/slider/" + data.getString("image")
                                    ));


                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }

                            }

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        pd.cancel();
                        p1.setVisibility(View.GONE);
                        Log.d("volley", "error : " + error.getMessage());
                    }
                });

        AppControler.getInstance().addToRequestQueue(reqData);


    }

    private void setupPageIndicatorChooser() {

        String[] pageIndicatorsLabels = getResources().getStringArray(R.array.page_indicators);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                pageIndicatorsLabels
        );

        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        bannerSlider.setDefaultIndicator(IndicatorShape.CIRCLE);
                        break;
                    case 1:
                        bannerSlider.setDefaultIndicator(IndicatorShape.DASH);
                        break;
                    case 2:
                        bannerSlider.setDefaultIndicator(IndicatorShape.ROUND_SQUARE);
                        break;
                    case 3:
                        bannerSlider.setDefaultIndicator(IndicatorShape.SQUARE);
                        break;
                    case 4:
                        bannerSlider.setCustomIndicator(VectorDrawableCompat.create(getResources(),
                                R.drawable.selected_slide_indicator, null),
                                VectorDrawableCompat.create(getResources(),
                                        R.drawable.unselected_slide_indicator, null));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

//
//    public void Informasi() {
//
//        p2.setVisibility(View.VISIBLE);
//        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.POST,
//                Server.URL+"siaortu/informasi.php?username="+ambilusername+"&pwd="+pwd, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        p2.setVisibility(View.GONE);
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//                                JSONObject data = response.getJSONObject(i);
//                                ModelData md = new ModelData();
//
//                                md.setJudul(data.getString("judul"));
//                                md.setTanggalin(data.getString("tanggal"));
//                                md.setIsiin(data.getString("isi"));
//
//
//                                mItems.add(md);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
////                                System.out.println("data kosong");
//                            }
//                        }
//                        mAdapter.notifyDataSetChanged();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        p2.setVisibility(View.GONE);
//                        Toast.makeText(getApplicationContext(), "Server down,cobalah kembali lagi nanti", Toast.LENGTH_LONG).show();
//                    }
//                });
//
//        MyApplication_p.getInstance().addToRequestQueue(reqData);
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.about:

                About();

                break;

            case R.id.scanpenumpang:
                startActivity(new Intent(Home.this, Penumpang.class));


                break;

            case R.id.scankargo:
                startActivity(new Intent(Home.this, Kargo.class));


                break;
        }
    }

    public void keluar() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Anda yakin ingin keluar?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PrefManager prefManager = new PrefManager(getApplicationContext());

                        // make first time launch TRUE
                        prefManager.setFirstTimeLaunch(true);
                        startActivity(new Intent(Home.this, Login.class));
                        finish();

                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }



    void About(){
        final Dialog dialog1 = new Dialog(Home.this, R.style.df_dialog);
        dialog1.setContentView(R.layout.about);
        dialog1.setCancelable(true);
        dialog1.setCanceledOnTouchOutside(true);
        dialog1.findViewById(R.id.btnSpinAndWinRedeem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }

}
