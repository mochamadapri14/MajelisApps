package com.example.majelistalim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.majelistalim.Adapter.AdapterData;
import com.example.majelistalim.Api.ApiInterface;
import com.example.majelistalim.Api.Retroserver;
import com.example.majelistalim.Model.JadwalModel;
import com.example.majelistalim.Model.ResponseModelJadwal;
import com.example.majelistalim.Session.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HalamanUtama extends AppCompatActivity {
    TextView nama, isi,level;
    FloatingActionButton post;
    SwipeRefreshLayout swipe;
    SessionManager sessionManager;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<JadwalModel> mItems = new ArrayList<>();
    boolean doubleBackToExitPressedOnce = false;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    ImageView foto;
    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce){
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this,"Tekan lagi untuk keluar", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;

            }
        }, 2000);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuindex,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem login = menu.findItem(R.id.masuk);
        MenuItem profil = menu.findItem(R.id.profil);
        MenuItem riwayat = menu.findItem(R.id.riwayat);
        MenuItem keluar = menu.findItem(R.id.keluar);
        MenuItem s = menu.findItem(R.id.cari);
        if(sessionManager.getLevel() == null) {
            login.setVisible(true);
            profil.setVisible(false);
            riwayat.setVisible(false);
            keluar.setVisible(false);
        }
        if (sessionManager.getLevel() != null){
            login.setVisible(false);
            profil.setVisible(true);
            riwayat.setVisible(true);
            keluar.setVisible(true);

        }
        if (sessionManager.getLevel().equals("Majelis")){
            s.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (R.id.keluar == id){
            sessionManager.logout();
            Intent i = new Intent(HalamanUtama.this,HalamanIndex.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            Toast.makeText(HalamanUtama.this,"Anda keluar!", Toast.LENGTH_LONG).show();
            finish();
        }
        if (R.id.profil == id){
            Intent i = new Intent(HalamanUtama.this, EditUser.class);
            i.putExtra("id_user", sessionManager.getId());
            i.putExtra("foto",sessionManager.getFoto());
            i.putExtra("nama_user",sessionManager.getNama());
            i.putExtra("username",sessionManager.getUsername());
            i.putExtra("password",sessionManager.getPassword());
            startActivity(i);
        }
        if (R.id.riwayat == id) {
            Intent i = new Intent(HalamanUtama.this, HistoryPesan.class);
            startActivity(i);
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_utama);
        toolbar = findViewById(R.id.tbIndex);
        setSupportActionBar(toolbar);
        foto = findViewById(R.id.imgView);
        nama = findViewById(R.id.txtNama);
        post = findViewById(R.id.btnInsert);
        mRecycler = findViewById(R.id.recyclerTemp);
        level = findViewById(R.id.txtLevel);
        isi = findViewById(R.id.tvTeks);
        swipe = findViewById(R.id.swipenya);
        progressDialog = new ProgressDialog(HalamanUtama.this);
        mManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecycler.setLayoutManager(mManager);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HalamanUtama.this, PostJadwal.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        sessionManager = new SessionManager(HalamanUtama.this);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tampil();
                swipe.setRefreshing(false);
            }
        });
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(HalamanUtama.this,ViewFoto.class);
                i.putExtra("foto",sessionManager.getFoto());
                i.putExtra("nama",sessionManager.getNama());
                startActivity(i);

            }
        });
        tampil();
        if (sessionManager.getFoto()==null){
            Glide.with(HalamanUtama.this).load(Retroserver.url_foto_awal)
                    .apply(RequestOptions.circleCropTransform()).into(foto);
        }else{
            Glide.with(HalamanUtama.this).load(Retroserver.url_foto_user+sessionManager.getFoto())
                    .apply(RequestOptions.circleCropTransform()).into(foto);
        }
        nama.setText(sessionManager.getNama());
        level.setText("Pengurus "+sessionManager.getLevel());




    }
    private void tampil() {
        int userid = sessionManager.getId();
        progressDialog.setMessage("Menunggu...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface api = Retroserver.getClient().create(ApiInterface.class);
        Call<ResponseModelJadwal> getData = api.getJadwalById(userid);
        getData.enqueue(new Callback<ResponseModelJadwal>() {
            @Override
            public void onResponse(Call<ResponseModelJadwal> call, Response<ResponseModelJadwal> response) {

                progressDialog.dismiss();
                mItems = response.body().getResultJadwal();
                if (mItems.size() > 0) {
                    mAdapter = new AdapterData(HalamanUtama.this, mItems);
                    mRecycler.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
                if (mItems.isEmpty()) {
                    isi.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onFailure(Call<ResponseModelJadwal> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }
}
