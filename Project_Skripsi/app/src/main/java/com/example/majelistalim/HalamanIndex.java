package com.example.majelistalim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.majelistalim.Adapter.AdapterData;
import com.example.majelistalim.Api.ApiInterface;
import com.example.majelistalim.Api.Retroserver;
import com.example.majelistalim.Model.JadwalModel;
import com.example.majelistalim.Model.PesanModel;
import com.example.majelistalim.Model.ResponseModelJadwal;
import com.example.majelistalim.Session.SessionManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HalamanIndex extends AppCompatActivity {
    ProgressDialog progressDialog;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<JadwalModel> mItems = new ArrayList<>();
    final static int REQUEST_LOCATION = 1;
    boolean doubleBackToExitPressedOnce = false;
    SwipeRefreshLayout swipe;
    TextView txtKota, latitude,longitude,nama,level,msk, txtNull;
    CardView cdnama;
    ImageView foto;
    SwitchCompat dekat;
    Button cari;
    SessionManager sessionManager;
    LinearLayout x;
    Toolbar toolbar;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_index);
        toolbar = findViewById(R.id.tbIndex);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Jadwal Majelis");
        cari = findViewById(R.id.btnCari);
        mRecycler = findViewById(R.id.recyclerTemp);
        foto = findViewById(R.id.imgView);
        txtKota = findViewById(R.id.txtKota);
        nama = findViewById(R.id.txtNama);
        cdnama = findViewById(R.id.cdNama);
        latitude = findViewById(R.id.txtLatitude);
        longitude = findViewById(R.id.txtLongitude);
        swipe = findViewById(R.id.swipenya);
        dekat = findViewById(R.id.cDekat);
        level = findViewById(R.id.txtLevel);
        msk = findViewById(R.id.login);
        x = findViewById(R.id.example);
        txtNull = findViewById(R.id.tvTeks);

        sessionManager = new SessionManager(HalamanIndex.this);
        progressDialog = new ProgressDialog(HalamanIndex.this);
        mManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecycler.setLayoutManager(mManager);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                view_all();
                swipe.setRefreshing(false);
            }
        });
        view_all();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(this,new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                },REQUEST_LOCATION);

            }
        }
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Intent i = new Intent(HalamanIndex.this,ViewFoto.class);
            i.putExtra("foto",sessionManager.getFoto());
            i.putExtra("nama",sessionManager.getNama());
            startActivity(i);

            }
        });
        if (sessionManager.getLevel() != null ){

            cdnama.setVisibility(View.VISIBLE);
            if (sessionManager.getFoto()==null){
                foto.setEnabled(false);
                Glide.with(HalamanIndex.this).load(Retroserver.url_foto_awal)
                        .apply(RequestOptions.circleCropTransform()).into(foto);
            }else{
                Glide.with(HalamanIndex.this).load(Retroserver.url_foto_user+sessionManager.getFoto())
                        .apply(RequestOptions.circleCropTransform()).into(foto);
            }

            nama.setText(sessionManager.getNama());
            level.setText(sessionManager.getLevel());
            if (sessionManager.getLevel().equals("Majelis")){
                Intent i = new Intent(HalamanIndex.this,HalamanUtama.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }

        }
        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HalamanIndex.this,HalamanSearch.class);
                startActivity(i);
            }
        });
        msk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HalamanIndex.this, MainActivity.class);
                startActivity(i);

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuindex,menu);
        MenuItem item = menu.findItem(R.id.cari);
        SearchView searchView = (SearchView) item.getActionView();
//        searchView.getContext().getResources().getIdentifier("Nama Majelis..",null,null);
        searchView.setQueryHint("Nama majelis..");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter = new AdapterData(HalamanIndex.this,mItems);

                ((AdapterData) mAdapter).getFilter().filter(newText);
                if (newText.equals("")){
                    view_all();
                }
                mRecycler.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem login = menu.findItem(R.id.masuk);
        MenuItem profil = menu.findItem(R.id.profil);
        MenuItem riwayat = menu.findItem(R.id.riwayat);
        MenuItem keluar = menu.findItem(R.id.keluar);
        if(sessionManager.getLevel() == null) {
            login.setVisible(false);
            profil.setVisible(false);
            riwayat.setVisible(false);
            keluar.setVisible(false);
        }
        if (sessionManager.getLevel() != null){
            msk.setVisibility(View.GONE);
            x.setVisibility(View.GONE);
            login.setVisible(false);
            profil.setVisible(true);
            riwayat.setVisible(true);
            keluar.setVisible(true);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (R.id.profil == id){
            Intent i = new Intent(HalamanIndex.this, EditUser.class);
            i.putExtra("id_user", sessionManager.getId());
            i.putExtra("foto",sessionManager.getFoto());
            i.putExtra("nama_user",sessionManager.getNama());
            i.putExtra("username",sessionManager.getUsername());
            i.putExtra("password",sessionManager.getPassword());
            startActivity(i);
        }
        if (R.id.riwayat == id) {
            Intent i = new Intent(HalamanIndex.this, HistoryPesan.class);
            startActivity(i);
        }
        if (R.id.keluar == id){
            sessionManager.logout();
            Intent i = new Intent(HalamanIndex.this,HalamanIndex.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            Toast.makeText(HalamanIndex.this,"Anda keluar!", Toast.LENGTH_LONG).show();
            finish();
        }
        return true;
    }
    private void view_all(){

        progressDialog.setMessage("Menunggu...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface api = Retroserver.getClient().create(ApiInterface.class);
        Call<ResponseModelJadwal> getData = api.getJadwal();
        getData.enqueue(new Callback<ResponseModelJadwal>() {
            @Override
            public void onResponse(Call<ResponseModelJadwal> call, Response<ResponseModelJadwal> response) {
                progressDialog.dismiss();
                if(response.body().getResultJadwal() != null) {
                    mItems = response.body().getResultJadwal();
                    mAdapter = new AdapterData(HalamanIndex.this, mItems);
                    mRecycler.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
                if (mItems.isEmpty()) {
                    txtNull.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onFailure(Call<ResponseModelJadwal> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

}
