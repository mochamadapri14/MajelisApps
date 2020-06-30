package com.example.majelistalim.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.majelistalim.Api.ApiInterface;
import com.example.majelistalim.Api.Retroserver;
import com.example.majelistalim.DetailJadwal;
import com.example.majelistalim.HalamanIndex;
import com.example.majelistalim.HalamanKomentar;
import com.example.majelistalim.HalamanUtama;
import com.example.majelistalim.Model.JadwalModel;
import com.example.majelistalim.Model.PesanModel;
import com.example.majelistalim.Model.ResponseModelJadwal;
import com.example.majelistalim.Model.ResponseModelPesan;
import com.example.majelistalim.PostJadwal;
import com.example.majelistalim.R;
import com.example.majelistalim.Session.SessionManager;
import com.example.majelistalim.ViewFoto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterPesan extends RecyclerView.Adapter<AdapterPesan.HolderData> {
    private List<PesanModel> mList;
    private Context context;

    public AdapterPesan(Context context, List<PesanModel> mList){
        this.context = context;
        this.mList = mList;

    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.listpesan, parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        final PesanModel pesanModel = mList.get(position);
        String waktunya = pesanModel.getWaktuy();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date baru = null;
        try {
            baru = format.parse(waktunya);
            format = new SimpleDateFormat("dd MMMM, HH:mm");
            String date= format.format(baru);
            holder.waktu.setText(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (pesanModel.getFoto() == null){
            Glide.with(context).load(Retroserver.url_foto_awal).apply(RequestOptions.circleCropTransform())
                    .into(holder.foto);
            holder.foto.setEnabled(false);
        }else{
            Glide.with(context).load(Retroserver.url_foto_user+pesanModel.getFoto())
                    .apply(RequestOptions.circleCropTransform()).into(holder.foto);
        }
        holder.foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, ViewFoto.class);
                i.putExtra("foto",pesanModel.getFoto());
                i.putExtra("nama",pesanModel.getNama_user());
                context.startActivity(i);

            }
        });
        holder.pesan.setText(pesanModel.getPesan());
        holder.nama.setText(pesanModel.getNama_user());
        holder.pm = pesanModel;
        String iduser = Integer.toString(pesanModel.getUserid());
        String id = Integer.toString(holder.sessionManager.getId());
        if (!id.equals(iduser) && pesanModel.getLevel().equals("Jamaah")){
            holder.level.setVisibility(View.GONE);
            holder.hapus.setVisibility(View.GONE);
            ColorStateList warna_bg = holder.bgJamaah.getCardBackgroundColor();
            holder.bgpesan.setCardBackgroundColor(warna_bg);

        }
        if (id.equals(iduser) && pesanModel.getLevel().equals("Jamaah")){
            holder.level.setVisibility(View.GONE);

        }
        if (!id.equals(iduser)){
            holder.hapus.setVisibility(View.GONE);
            ColorStateList warna_bg = holder.bgJamaah.getCardBackgroundColor();
            holder.bgpesan.setCardBackgroundColor(warna_bg);

        }
        if (id.equals(iduser) && pesanModel.getLevel().equals("Majelis")){
            holder.level.setVisibility(View.VISIBLE);
        }
        if (!id.equals(iduser) && pesanModel.getLevel().equals("Majelis")){
            holder.level.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView nama,pesan, waktu,hapus,level;
        PesanModel pm;
        ImageView foto;
        SessionManager sessionManager;
        ProgressDialog progressDialog;
        CardView bgpesan,bgJamaah, bgPengurus;
        public HolderData(View v) {
            super(v);
            sessionManager = new SessionManager(context);
            progressDialog = new ProgressDialog(context);
            bgpesan = v.findViewById(R.id.cdPesan);
            bgJamaah = v.findViewById(R.id.cdPesan2);
            nama = v.findViewById(R.id.tvNamaP);
            level = v.findViewById(R.id.tvLevel);
            pesan = v.findViewById(R.id.tvPesan);
            foto = v.findViewById(R.id.imgView);
            waktu = v.findViewById(R.id.tvWaktu);
            hapus = v.findViewById(R.id.hapus);

            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(context)
                            .setMessage("Hapus pesan ini ?")
                            .setPositiveButton("Iya", null)
                            .setNegativeButton("Tidak", null)
                            .show();
                    final Button positif = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positif.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progressDialog.setMessage("Menunggu");
                            progressDialog.setCancelable(false);
                            progressDialog.show();

                            String idpesan = Integer.toString(pm.getId_pesan());
                            ApiInterface apiInterface = Retroserver.getClient().create(ApiInterface.class);
                            Call<ResponseModelPesan> delete = apiInterface.hapusPesan(idpesan);
                            delete.enqueue(new Callback<ResponseModelPesan>() {
                                @Override
                                public void onResponse(Call<ResponseModelPesan> call, Response<ResponseModelPesan> response) {
                                    progressDialog.dismiss();
                                    String kode = response.body().getKode();
                                    String pesan = response.body().getMessage();

                                    if (kode.equals("1")) {
                                        Toast.makeText(context, pesan, Toast.LENGTH_SHORT).show();
                                        mList.remove(getAdapterPosition());
                                        notifyDataSetChanged();
                                        alertDialog.dismiss();

                                    } else {
                                        Toast.makeText(context, pesan, Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseModelPesan> call, Throwable t) {
                                    progressDialog.dismiss();
                                    Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    });
                }
            });

        }


    }
}
