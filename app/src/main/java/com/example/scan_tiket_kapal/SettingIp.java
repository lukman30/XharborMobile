package com.example.scan_tiket_kapal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingIp extends Activity implements View.OnClickListener {
TextView simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        simpan=(TextView)findViewById(R.id.simpan);

        simpan.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(SettingIp.this,Home.class));
    }
}