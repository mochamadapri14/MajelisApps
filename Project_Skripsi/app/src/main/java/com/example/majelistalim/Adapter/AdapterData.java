package com.example.majelistalim.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.majelistalim.Api.ApiInterface;
import com.example.majelistalim.Api.Retroserver;
import com.example.majelistalim.DetailJadwal;
import com.example.majelistalim.EditJadwal;
import com.example.majelistalim.HalamanIndex;
import com.example.majelistalim.HalamanKomentar;
import com.example.majelistalim.HalamanUtama;
import com.example.majelistalim.Model.JadwalModel;
import com.example.majelistalim.Model.ResponseModelJadwal;
import com.example.majelistalim.R;
import com.example.majelistalim.Session.SessionManager;
import com.example.majelistalim.ViewFoto;
import com.example.majelistalim.ViewFotoMajelis;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> implements Filterable {

    private List<JadwalModel> mList;
    private List<JadwalModel> mListAll;
    private Context context;


    public AdapterData(Context context,List<JadwalModel> mList){
        this.context = context;
        this.mList = mList;
        mListAll = new ArrayList<>(mList);


    }



    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutlist, parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderData holder, int position) {
        final JadwalModel jadwalModel = mList.get(position);
        String url = Retroserver.url_foto + jadwalModel.getFoto();
        Picasso.get().load(url).fit().centerInside().into(holder.gmbr);
        holder.nama.setText(jadwalModel.getNama_majelis());
        SimpleDateFormat formatnya = new SimpleDateFormat("yyyy/MM/dd");
        Date baru2 = null;
        try {
            baru2 = formatnya.parse(jadwalModel.getTanggal());
            formatnya = new SimpleDateFormat("EEEE, d MMMM yyyy");
            String date= formatnya.format(baru2);
            holder.waktu.setText(date+ " - " +jadwalModel.getJam_mulai()+" WIB");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.jm = jadwalModel;

        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
        String currDate = format1.format(d);
        int waktu_sekarang = Integer.parseInt(currDate);


        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date baru = null;
        try {
            baru = format.parse(jadwalModel.getTanggal());
            format = new SimpleDateFormat("yyyyMMdd");
            String date= format.format(baru);

            int waktu = Integer.parseInt(date);
            if (waktu_sekarang > waktu ){
                holder.selesai.setVisibility(View.VISIBLE);
                holder.ubah.setVisibility(View.GONE);
                holder.belum.setVisibility(View.GONE);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }



        FusedLocationProviderClient flpc = LocationServices.getFusedLocationProviderClient(context);
        flpc.getLastLocation().addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null){
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    Location awal = new Location("Point A");
                    awal.setLongitude(latitude);
                    awal.setLongitude(longitude);

                    double lat =Double.parseDouble(holder.jm.getLatitude());
                    double lng = Double.parseDouble(holder.jm.getLongitude());
                    float result[] = new float[10];
                    Location tujuan = new Location("Point B");
                    tujuan.setLatitude(lat);
                    tujuan.setLongitude(lng);

                    Location.distanceBetween(latitude,longitude,lat,lng,result);

                    final String compress = String.format("%.02f",result[0]/1000);
                    holder.lokasi.setText(jadwalModel.getNama_alamat() + " ("+compress+" km)");

                }
            }
        });

     }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<JadwalModel> filterList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                filterList.addAll(mListAll);

            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (JadwalModel item: mListAll){
                    if (item.getNama_majelis().toLowerCase().contains(filterPattern)){
                        filterList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values =filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mList.clear();
            mList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    class HolderData extends RecyclerView.ViewHolder{


        TextView ket,nama,waktu,lokasi,majelis,tvJarak;
        ImageView gmbr;
        TextView ubah, hapus;
        JadwalModel jm;
        Button forum,detail;
        ProgressDialog progressDialog;
        SessionManager sessionManager;
        LinearLayout selesai, belum;
        public HolderData(View v){
            super(v);
            progressDialog = new ProgressDialog(context);
            sessionManager = new SessionManager(context);
            selesai = v.findViewById(R.id.selesai);
            belum = v.findViewById(R.id.belum);
            waktu = v.findViewById(R.id.tvWaktu);
            tvJarak = v.findViewById(R.id.tvJarak);
            forum = v.findViewById(R.id.btnForum);
            detail = v.findViewById(R.id.btnDetail);
            nama = v.findViewById(R.id.tvNama);
            lokasi = v.findViewById(R.id.tvLokasi);
            gmbr = v.findViewById(R.id.imgView);
            ubah = v.findViewById(R.id.btnUbah);
            hapus = v.findViewById(R.id.btnHapus);

            if (sessionManager.getLevel()!=null){
                if (sessionManager.getLevel().equals("Majelis")){
                    hapus.setVisibility(View.VISIBLE);
                    ubah.setVisibility(View.VISIBLE);

                }

            }
            gmbr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, ViewFotoMajelis.class);
                    i.putExtra("foto",jm.getFoto());
                    i.putExtra("nama",jm.getNama_majelis());
                    context.startActivity(i);
                }
            });


//            forum.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, HalamanKomentar.class);
//                    intent.putExtra("id_jadwal",jm.getId_jadwal());
//                    intent.putExtra("nama_majelis", jm.getNama_majelis());
//                    intent.putExtra("nama_alamat",jm.getNama_alamat());
//                    intent.putExtra("tanggal", jm.getTanggal());
//                    intent.putExtra("jam_mulai",jm.getJam_mulai());
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    context.startActivity(intent);
//                }
//            });
            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(context)
                            .setTitle("Menghapus "+jm.getNama_majelis()+", di "+jm.getNama_alamat())
                            .setMessage("Apakah anda yakin ingin menghapus ?")
                            .setPositiveButton("Iya",null)
                            .setNegativeButton("Tidak", null)
                            .show();
                    Button positif = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positif.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progressDialog.setMessage("Menunggu");
                            progressDialog.setCancelable(false);
                            progressDialog.show();

                            ApiInterface apiInterface = Retroserver.getClient().create(ApiInterface.class);
                            Call<ResponseModelJadwal> delete = apiInterface.hapus(jm.getId_jadwal());
                            delete.enqueue(new Callback<ResponseModelJadwal>() {
                                @Override
                                public void onResponse(Call<ResponseModelJadwal> call, Response<ResponseModelJadwal> response) {
                                    progressDialog.dismiss();
                                    String kode = response.body().getKode();
                                    String pesan = response.body().getPesan();
                                    if (kode.equals("1")){
                                        Toast.makeText(context,pesan, Toast.LENGTH_SHORT).show();
                                        mList.remove(getAdapterPosition());
                                        notifyDataSetChanged();
                                        alertDialog.dismiss();
                                    }else{
                                        Toast.makeText(context,pesan, Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseModelJadwal> call, Throwable t) {
                                    progressDialog.dismiss();
                                    Toast.makeText(context,t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    });
                }
            });
            ubah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditJadwal.class);
                    intent.putExtra("id_jadwal",jm.getId_jadwal());
                    intent.putExtra("foto" , jm.getFoto());
                    intent.putExtra("nama_majelis", jm.getNama_majelis());
                    intent.putExtra("tanggal", jm.getTanggal());
                    intent.putExtra("jam_mulai" ,jm.getJam_mulai());
                    intent.putExtra("nama_alamat",jm.getNama_alamat());
                    intent.putExtra("lokasi",jm.getLokasi());
                    intent.putExtra("keterangan", jm.getKeterangan());
                    intent.putExtra("latitude",jm.getLatitude());
                    intent.putExtra("longitude", jm.getLongitude());
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
            });
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(context, DetailJadwal.class);
                    intent.putExtra("id_jadwal",jm.getId_jadwal());
                    intent.putExtra("user_id",jm.getUser_id());
                    intent.putExtra("nama_user", jm.getNama_user());
                    intent.putExtra("foto" , jm.getFoto());
                    intent.putExtra("nama_majelis", jm.getNama_majelis());
                    intent.putExtra("tanggal", jm.getTanggal());
                    intent.putExtra("jam_mulai" ,jm.getJam_mulai());
                    intent.putExtra("nama_alamat",jm.getNama_alamat());
                    intent.putExtra("lokasi",jm.getLokasi());
                    intent.putExtra("keterangan", jm.getKeterangan());
                    intent.putExtra("waktu", jm.getWaktu());
                    intent.putExtra("latitude",jm.getLatitude());
                    intent.putExtra("longitude", jm.getLongitude());
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
            });

        }
    }

}
