package com.example.majelistalim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.majelistalim.Adapter.AdapterPesan;
import com.example.majelistalim.Api.ApiInterface;
import com.example.majelistalim.Api.Retroserver;
import com.example.majelistalim.Model.JadwalModel;
import com.example.majelistalim.Model.PesanModel;
import com.example.majelistalim.Model.ResponseModelJadwal;
import com.example.majelistalim.Model.ResponseModelPesan;
import com.example.majelistalim.Session.SessionManager;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailJadwal extends AppCompatActivity {
    ImageView gambar;
    TextView namam,nama, tanggal,jam , lokasi, keterangan,kirim, idjadwal,isi;
    EditText etPesan;
    ProgressDialog progressDialog;
    SessionManager sessionManager;
    CardView lht;
    LinearLayout selesai,belum;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jadwal);
        toolbar= findViewById(R.id.tbIndex);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        gambar = findViewById(R.id.gambar);
        selesai = findViewById(R.id.selesai);
        belum = findViewById(R.id.belum);
        namam = findViewById(R.id.tNamam);
        nama = findViewById(R.id.tNama);
        tanggal =findViewById(R.id.tTanggal);
        jam = findViewById(R.id.tJam);
        lokasi = findViewById(R.id.tLokasi);
        keterangan = findViewById(R.id.tKet);
        idjadwal = findViewById(R.id.idnya);
        lht = findViewById(R.id.tvLihat);
        isi = findViewById(R.id.tvData);
        sessionManager = new SessionManager(DetailJadwal.this);
        progressDialog = new ProgressDialog(DetailJadwal.this);



        Intent data = getIntent();
        final String majelis_id = data.getStringExtra("id_jadwal");
        idjadwal.setText(majelis_id);
        String url = Retroserver.url_foto + data.getStringExtra("foto");
        Picasso.get().load(url)
                .fit().centerInside()
                .into(gambar);

        final String nama_m = data.getStringExtra("nama_majelis");
        final String nama_a = data.getStringExtra("nama_alamat");
        nama.setText(nama_m);
        SimpleDateFormat formatnya = new SimpleDateFormat("yyyy/MM/dd");
        Date baruw = null;
        try {
            baruw= formatnya.parse(data.getStringExtra("tanggal"));
            formatnya = new SimpleDateFormat("EEEE, d MMMM yyyy");
            String date= formatnya.format(baruw);
            tanggal.setText(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        final String tanggalx = data.getStringExtra("tanggal");
        final String jamx = data.getStringExtra("jam_mulai");
        jam.setText(jamx+ " WIB s/d Selesai");
        keterangan.setText(data.getStringExtra("keterangan"));
        final String lat = data.getStringExtra("latitude");
        final String lot = data.getStringExtra("longitude");
        final String tempat = data.getStringExtra("nama_alamat");
        lokasi.setText(data.getStringExtra("lokasi"));

        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
        String currDate = format1.format(d);
        int waktu_sekarang = Integer.parseInt(currDate);


        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date baru = null;
        try {
            baru = format.parse(tanggalx);
            format = new SimpleDateFormat("yyyyMMdd");
            String date= format.format(baru);

            int waktu = Integer.parseInt(date);
            if (waktu_sekarang > waktu ){
                selesai.setVisibility(View.VISIBLE);
                belum.setVisibility(View.GONE);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/maps?q=loc:"+lat +","+lot +" ("+tempat+")";
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                i.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
                startActivity(i);
//
            }
        });
        lht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JadwalModel jd = new JadwalModel();
                jd.setId_jadwal(majelis_id);
                jd.setNama_majelis(nama_m);
                jd.setNama_alamat(nama_a);
                jd.setTanggal(tanggalx);
                jd.setJam_mulai(jamx);
                Intent i = new Intent(DetailJadwal.this, HalamanKomentar.class);
                i.putExtra("id_jadwal",jd.getId_jadwal());
                i.putExtra("nama_majelis",jd.getNama_majelis());
                i.putExtra("nama_alamat",jd.getNama_alamat());
                i.putExtra("tanggal",jd.getTanggal());
                i.putExtra("jam_mulai",jd.getJam_mulai());
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        //TAMPILIN NAMA USER
        ApiInterface api = Retroserver.getClient().create(ApiInterface.class);
        Call<ResponseModelJadwal> lihat = api.getDetail(majelis_id);
        lihat.enqueue(new Callback<ResponseModelJadwal>() {
            @Override
            public void onResponse(Call<ResponseModelJadwal> call, Response<ResponseModelJadwal> response) {
                String kode = response.body().getKode();
                JadwalModel jadwalModel = response.body().getResultJadwal().get(0);
                if (kode.equals("1")){
                    namam.setText(jadwalModel.getNama_user().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseModelJadwal> call, Throwable t) {

            }
        });



    }

}
