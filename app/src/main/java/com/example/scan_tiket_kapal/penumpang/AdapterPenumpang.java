package com.example.scan_tiket_kapal.penumpang;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.scan_tiket_kapal.R;
import com.example.scan_tiket_kapal.config.ModelData;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterPenumpang extends RecyclerView.Adapter<AdapterPenumpang.HolderData> {
    private List<ModelData> mItems;
    private Context context;
    String idresep;
    int success;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    SharedPreferences iduser;
    String ambiliduser;



    public AdapterPenumpang(Context context, List<ModelData> items) {
        this.mItems = items;
        this.context = context;
    }

    @Override
    public AdapterPenumpang.HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.listpenumpang, parent, false);
        AdapterPenumpang.HolderData holderData = new AdapterPenumpang.HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(AdapterPenumpang.HolderData holder, int position) {
        ModelData md = mItems.get(position);
        holder.nama.setText(": "+ md.getNama());
        holder.booking.setText(md.getKdbooking());
        holder.status.setText(": "+ md.getStatus());
        holder.umur.setText(" "+md.getUmur()+" Tahun");
//        System.out.println("aaaaaaaaaaaaaa");
//        System.out.println(md.getStatus());

//
//        holder.status.setBackgroundResource(R.drawable.cekin);
//
//
        if (md.getStatus()=="TERBAYAR"||md.getStatus().equals("TERBAYAR")){
//            holder.status.setBackgroundResource(R.drawable.terbayar);
            holder.img.setVisibility(View.GONE);
        }else if (md.getStatus()=="CHECK-IN"||md.getStatus().equals("CHECK-IN")){
//            holder.status.setBackgroundResource(R.drawable.cekin);
            holder.img.setVisibility(View.VISIBLE);
        }else{
            holder.img.setVisibility(View.GONE);
        }

        holder.md = md;


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class HolderData extends RecyclerView.ViewHolder {
        TextView booking,nama,status,umur;
        ImageView img;
        ModelData md;
        public HolderData(View view) {
            super(view);

            booking=(TextView)view.findViewById(R.id.booking);
            nama=(TextView)view.findViewById(R.id.nama);
            status=(TextView)view.findViewById(R.id.status);
            umur=(TextView)view.findViewById(R.id.umur);
            img=(ImageView)view.findViewById(R.id.img);


//
////
//            cardview.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    Intent update = new Intent(context, Detail.class);
//                    update.putExtra("update", 1);
//                    update.putExtra("idresep", md.getId());
//
//                    context.startActivity(update);
//
//
//                }
//            });

        }

    }


}