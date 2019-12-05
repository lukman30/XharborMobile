package com.example.scan_tiket_kapal;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.scan_tiket_kapal.config.AppControler;
import com.example.scan_tiket_kapal.config.PrefManager;
import com.example.scan_tiket_kapal.config.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener {
    TextView daftar, login;
    static final int tampil_error = 1, cekupdateaplikasi = 2, tampilceklogin = 3, cekimei = 4;

    private SharedPreferences permissionStatus;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };
    private boolean sentToSettings = false;




    EditText username, password;

    public static final String SATU = "PREFS_WORLD_READABLE_WRITABLE";
    public static final String KEY_SATU = "KEY_WORLD_READ_WRITE";
    public static final String PASSOWRD = "password";
    public static final String KEY_PASSWORD = "key_password";


    public static final String IDUSER = "iduser";
    public static final String KEYIDUSER = "key_iduser";




    public static final String LEVEL = "level";
    public static final String KEYLEVEL = "key_level";


    private SharedPreferences prefssatu, prefpassword, level;
    private ProgressDialog pDialog;

    //String usernameambil,passwordambil;

    private PrefManager prefManager;
    int success;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";

    String ambiliduser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
        daftar = (TextView) findViewById(R.id.daftar);
        daftar.setOnClickListener(this);
        login = (TextView) findViewById(R.id.signin);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);


        login.setOnClickListener(this);

        prefssatu = getSharedPreferences(
                Login.IDUSER,
                Context.MODE_PRIVATE +
                        Context.MODE_PRIVATE | Context.MODE_PRIVATE);
        ambiliduser = prefssatu.getString(
                Login.KEYIDUSER, "username");


        String levell;
        level = getSharedPreferences(
                Login.LEVEL,
                Context.MODE_PRIVATE +
                        Context.MODE_PRIVATE | Context.MODE_PRIVATE);
        levell = level.getString(
                Login.KEYLEVEL, "level");



//
//
//        prefManager = new PrefManager(this);
//        if (!prefManager.isFirstTimeLaunch()) {
//
//
//
//            prefManager.setFirstTimeLaunch(false);
//            Intent i = new Intent(Login.this, MenuAwalHomeClass.class);
//            startActivity(i);
//            finish();
//
//
//
//
//
//
//        }

        cepermission();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.daftar:
//                Intent it = new Intent(Login.this, Daftar.class);
//                startActivity(it);
                break;
            case R.id.signin:
                if (username.length() < 1) {
                    Toast.makeText(getApplicationContext(), "Masukkan Username dengan benar", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 1) {
                    Toast.makeText(getApplicationContext(), "Masukkan Password dengan benar", Toast.LENGTH_SHORT).show();
                    return;
                }
                prefssatu = getSharedPreferences(Login.SATU,
                        Context.MODE_PRIVATE + Context.MODE_PRIVATE
                                | Context.MODE_PRIVATE);
                SharedPreferences.Editor worldReadWriteEdit = prefssatu.edit();
                worldReadWriteEdit.putString(Login.KEY_SATU, username.getText()
                        .toString());
                worldReadWriteEdit.commit();


                prefpassword = getSharedPreferences(Login.PASSOWRD,
                        Context.MODE_PRIVATE + Context.MODE_PRIVATE
                                | Context.MODE_PRIVATE);
                SharedPreferences.Editor worldReadWriteEdit1 = prefpassword.edit();
                worldReadWriteEdit1.putString(Login.KEY_PASSWORD, password.getText()
                        .toString());
                worldReadWriteEdit1.commit();

                Login();
//
//                Intent itt = new Intent(Login.this, MenuAwal.class);
//                startActivity(itt);
                break;

        }
    }


    public void cepermission() {
        if (ActivityCompat.checkSelfPermission(Login.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(Login.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(Login.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Login.this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(Login.this, permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(Login.this, permissionsRequired[2])) {
                //Show Information about why you need the permission
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Login.this);
                builder.setCancelable(false);
                builder.setTitle("Informasi");
                builder.setMessage("Untuk menggunakan aplikasi ini silahkan aktifkan permissions anda...");
                builder.setPositiveButton("Setuju", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(Login.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
//                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
                builder.show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(Login.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
            }

//            txtPermissions.setText("Permissions Required");

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0], true);
            editor.commit();
        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {
                proceedAfterPermission();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(Login.this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(Login.this, permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(Login.this, permissionsRequired[2])) {
//                txtPermissions.setText("Permissions Required");
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Login.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("Izinkan permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(Login.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
                builder.show();
            } else {
//                Toast.makeText(getBaseContext(),"Permission Diaktifkan",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(Login.this, permissionsRequired[0])
                    == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }

    private void proceedAfterPermission() {
//        txtPermissions.setText("We've got all permissions");
//        Toast.makeText(getBaseContext(), "Permissions Diaktifkan", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(Login.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }







    private void Login() {
        System.out.println(Server.URL + "web_service/login.php");
        //menampilkan progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Login...", " Mohon Tunggu...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URL + "web_service/login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);


                            if (success == 1) {
                                Toast.makeText(Login.this, jObj.getString("message"), Toast.LENGTH_LONG).show();




//
//
//                                prefManager.setFirstTimeLaunch(false);
//                                Intent i = new Intent(Login.this, MenuAwalHomeClass.class);
//                                startActivity(i);
//                                finish();
//



                            } else {
                                Toast.makeText(Login.this, jObj.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //menghilangkan progress dialog
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //menghilangkan progress dialog
                        loading.dismiss();

                        //menampilkan toast
                        Toast.makeText(Login.this, "Maaf Ada Kesalahan!!", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //membuat parameters
                Map<String, String> params = new HashMap<String, String>();
                String Login = username.getText().toString();
                String Password = password.getText().toString();


                params.put("Login", Login.trim());
                params.put("Password", Password.trim());

                return params;
            }
        };

        AppControler.getInstance().addToRequestQueue(stringRequest, "json");
    }

}

