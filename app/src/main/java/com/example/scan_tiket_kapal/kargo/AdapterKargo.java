package com.example.scan_tiket_kapal.kargo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scan_tiket_kapal.R;
import com.example.scan_tiket_kapal.config.ModelData;
import com.example.scan_tiket_kapal.penumpang.AdapterPenumpang;

import java.util.List;

public class AdapterKargo extends RecyclerView.Adapter<AdapterKargo.HolderData> {
    private List<ModelData> mItems;
    private Context context;
    String idresep;
    int success;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    SharedPreferences iduser;
    String ambiliduser;



    public AdapterKargo(Context context, List<ModelData> items) {
        this.mItems = items;
        this.context = context;
    }

    @Override
    public AdapterKargo.HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.listkargo, parent, false);
        AdapterKargo.HolderData holderData = new AdapterKargo.HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(AdapterKargo.HolderData holder, int position) {
        ModelData md = mItems.get(position);
        holder.nama.setText(": "+ md.getNama());
        holder.booking.setText(md.getKdbooking());
        holder.status.setText(": "+ md.getStatus());
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
        TextView booking,nama,status;
        ImageView img;
        ModelData md;
        public HolderData(View view) {
            super(view);
            img=(ImageView)view.findViewById(R.id.img);


            booking=(TextView)view.findViewById(R.id.booking);
            nama=(TextView)view.findViewById(R.id.nama);
            status=(TextView)view.findViewById(R.id.status);



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