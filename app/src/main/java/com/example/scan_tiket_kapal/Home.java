package com.example.scan_tiket_kapal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.scan_tiket_kapal.config.AppControler;
import com.example.scan_tiket_kapal.config.ModelData;
import com.example.scan_tiket_kapal.config.PrefManager;
import com.example.scan_tiket_kapal.config.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.events.OnBannerClickListener;
import ss.com.bannerslider.views.BannerSlider;
import ss.com.bannerslider.views.indicators.IndicatorShape;

public class Home extends AppCompatActivity implements View.OnClickListener {

    private static final String NOTIFICATION_MSG = "NOTIFICATION MSG";
    String jarak;
    Double latit, longit;
    static final int gagal = 1, info = 2, berhasil = 3, cekimei = 4;

    //get emai hp
    String IMEI_Number_Holder;
    TelephonyManager telephonyManager;


    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;


    private EditText kodetahun, mhsw;
    TextView ambiltahun, ambilmhsw;
    Button btnlihat;
    LinearLayout mainLayout;
    String kode1, kode2;
    private TextView infonilai;
    private int userchoice;

    private Button tampil;
    ProgressDialog pd;
    String ambilnim, ambilpwd;

    private ProgressDialog pDialog;
    private static final String TAG_MESSAGE = "message";
    TextView adadosen;
    private SharedPreferences prefssatu, prefpassword, prefnama;
    private LinearLayoutManager linearLayoutManager;
    public static final String AMBILNAMA = "ambilnama";
    public static final String KEY_NAMA = "key_nama";
    private SharedPreferences jadwalid;
    private SharedPreferences prefsPrivate;
    private String source, jml;
    private SwipeRefreshLayout swipeRefreshLayout;
    EditText nim;

    TextView alamatt;

    private FloatingActionButton fab;
    private Button tampil1;
    ProgressDialog pd1;
    String ambilnim1, ambilpwd1, ambilusername;

    private ProgressDialog pDialog1;
    private static final String TAG_MESSAGE1 = "message";
    TextView adadosen1;
    private SharedPreferences prefssatu1, prefpassword1;


    String alamat, pwd;


    Spinner spinner;
    SeekBar intervalSeekBar;
    private BannerSlider bannerSlider;
    SeekBar indicatorSizeSeekBar;
    SwitchCompat loopSlidesSwitch, mustAnimateIndicators;
    SwitchCompat hideIndicatorsSwitch;

    ProgressBar p1, p2, p3, p4;

    TextView tidakadajadwal;

//
//    private Adapter_List mAdapter;


    private PrefManager prefManager;
    int success;
    private static final String TAG_SUCCESS = "success";
    String tag_json_obj = "json_obj_req";


    ////list informasi
    RecyclerView mRecyclerview;
    RecyclerView.LayoutManager mManager;
    List<ModelData> mItems;
    RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

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


        ////informasi
        setupViews();
        loadJson();


    }


    private void setupViews() {
//        setupToolbar();
        setupBannerSlider();
        setupPageIndicatorChooser();
        setupSettingsUi();
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
                "https://assets.materialup.com/uploads/dcc07ea4-845a-463b-b5f0-4696574da5ed/preview.jpg"
        ));
        bannerSlider.addBanner(new RemoteBanner(
                "https://assets.materialup.com/uploads/4b88d2c1-9f95-4c51-867b-bf977b0caa8c/preview.gif"
        ));
        bannerSlider.addBanner(new RemoteBanner(
                "https://assets.materialup.com/uploads/76d63bbc-54a1-450a-a462-d90056be881b/preview.png"
        ));
        bannerSlider.addBanner(new RemoteBanner(
                "https://assets.materialup.com/uploads/05e9b7d9-ade2-4aed-9cb4-9e24e5a3530d/preview.jpg"
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

//            case R.id.off:
//
//                keluar();

//                break;
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
//                        prefManager.setFirstTimeLaunch(true);
//
//                        startActivity(new Intent(MenuAwalHomeClass.this, Login.class));
//                        finish();

                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }

}
