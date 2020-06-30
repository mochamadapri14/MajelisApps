package com.example.majelistalim.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.majelistalim.DetailJadwal;
import com.example.majelistalim.HalamanKomentar;
import com.example.majelistalim.Model.PesanModel;
import com.example.majelistalim.R;
import com.example.majelistalim.Session.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterRiwayat extends RecyclerView.Adapter<AdapterRiwayat.HolderData> {
    private List<PesanModel> mListA;
    private Context context;

    public AdapterRiwayat(Context context, List<PesanModel> mListA){
        this.context = context;
        this.mListA = mListA;

    }

    @NonNull
    @Override
    public AdapterRiwayat.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.listriwayat, parent,false);
        AdapterRiwayat.HolderData holderData = new AdapterRiwayat.HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRiwayat.HolderData holder, int position) {
        final PesanModel pesanModel = mListA.get(position);

        holder.namamajelis.setText(pesanModel.getNama_majelis());
        SimpleDateFormat formatnya = new SimpleDateFormat("yyyy/MM/dd");
        Date baru2 = null;
        try {
            baru2 = formatnya.parse(pesanModel.getTanggal());
            formatnya = new SimpleDateFormat("EEEE, d MMMM yyyy");
            String date= formatnya.format(baru2);
            holder.tempat.setText(pesanModel.getNama_alamat()+ " " +date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.pesan.setText(pesanModel.getPesan());

        SimpleDateFormat formatnya2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date baru = null;
        try {
            baru = formatnya2.parse(pesanModel.getWaktuy());
            formatnya2 = new SimpleDateFormat("dd MMMM, HH:mm");
            String date2= formatnya2.format(baru);
            holder.waktu.setText(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.pm = pesanModel;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String iduser = Integer.toString(holder.sessionManager.getId());
                holder.sessionManager.setId(holder.sessionManager.getId());
                Intent intent = new Intent(context, HalamanKomentar.class);
                intent.putExtra("id_jadwal",pesanModel.getId_jadwal());
                intent.putExtra("tanggal",pesanModel.getTanggal());
                intent.putExtra("jam_mulai",pesanModel.getJam_mulai());
                intent.putExtra("nama_majelis",pesanModel.getNama_majelis());
                intent.putExtra("nama_alamat",pesanModel.getNama_alamat());
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
       return  mListA.size();
    }

    class HolderData extends  RecyclerView.ViewHolder{
        TextView namamajelis, tempat, pesan, waktu;
        PesanModel pm;
        SessionManager sessionManager;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            namamajelis = itemView.findViewById(R.id.namaMajelis);
            tempat = itemView.findViewById(R.id.tempat);
            pesan = itemView.findViewById(R.id.pesan);
            waktu = itemView.findViewById(R.id.waktu);
            sessionManager = new SessionManager(context);


        }
    }
}
