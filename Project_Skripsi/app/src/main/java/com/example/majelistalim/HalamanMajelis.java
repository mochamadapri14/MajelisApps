package com.example.majelistalim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.majelistalim.Adapter.AdapterData;
import com.example.majelistalim.Api.ApiInterface;
import com.example.majelistalim.Api.Retroserver;
import com.example.majelistalim.Model.JadwalModel;
import com.example.majelistalim.Model.ResponseModelJadwal;
import com.example.majelistalim.Session.SessionManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HalamanMajelis extends AppCompatActivity {
    TextView kota, dki, jakpus,jakut,jaktim,jakbar,jaksel;
    ProgressDialog progressDialog;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<JadwalModel> mItems = new ArrayList<>();
    Toolbar toolbar;
    SwitchCompat dekat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_majelis);
        toolbar = findViewById(R.id.tbIndex);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mRecycler = findViewById(R.id.recyclerTemp);
        dekat = findViewById(R.id.cDekat);
        kota = findViewById(R.id.txtKota);
        dki = findViewById(R.id.textDKI);
        jakpus =findViewById(R.id.textJakpus);
        jakut = findViewById(R.id.textJakut);
        jaktim = findViewById(R.id.textJaktim);
        jakbar = findViewById(R.id.textJakbar);
        jaksel = findViewById(R.id.textJaksel);
        progressDialog = new ProgressDialog(HalamanMajelis.this);
        mManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecycler.setLayoutManager(mManager);
        dekat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dekat.isChecked()){
                    terdekat();
                }else{
                    view_byalamat();
                }
            }
        });
        Intent data = getIntent();
        dki.setText(data.getStringExtra("DKI Jakarta"));
        jakpus.setText(data.getStringExtra("Jakarta Pusat"));
        jakut.setText(data.getStringExtra("Jakarta Utara"));
        jaktim.setText(data.getStringExtra("Jakarta Timur"));
        jakbar.setText(data.getStringExtra("Jakarta Barat"));
        jaksel.setText(data.getStringExtra("Jakarta Selatan"));
        String DKI = dki.getText().toString();
        String pusat = jakpus.getText().toString();
        String utara = jakut.getText().toString();
        String timur = jaktim.getText().toString();
        String barat = jakbar.getText().toString();
        String selatan = jaksel.getText().toString();

        if (DKI.equals("DKI Jakarta")){
            view_all();
            setTitle("DKI Jakarta");
        }else if (pusat.equals("Jakarta Pusat")){
            kota.setText("jakarta pusat");
            setTitle("Jakarta Pusat");
            view_byalamat();
        }else if (utara.equals("Jakarta Utara")){
            kota.setText("jkt utara");
            setTitle("Jakarta Utara");
            view_byalamat();
        }else if (timur.equals("Jakarta Timur")){
            kota.setText("jakarta timur");
            setTitle("Jakarta Timur");
            view_byalamat();

        }else if (barat.equals("Jakarta Barat")){
            kota.setText("jakarta barat");
            setTitle("Jakarta Barat");
            view_byalamat();
        }else if (selatan.equals("Jakarta Selatan")){
            kota.setText("jakarta selatan");
            setTitle("Jakarta Selatan");
            view_byalamat();

        }

    }
    private void view_byalamat(){
        String lokasi = kota.getText().toString();
        ApiInterface apiInterface = Retroserver.getClient().create(ApiInterface.class);
        Call<ResponseModelJadwal> byAlamat = apiInterface.getJadwalByAlamat(lokasi);
        byAlamat.enqueue(new Callback<ResponseModelJadwal>() {
            @Override
            public void onResponse(Call<ResponseModelJadwal> call, Response<ResponseModelJadwal> response) {
                progressDialog.dismiss();
                mItems = response.body().getResultJadwal();
                mAdapter = new AdapterData(HalamanMajelis.this,mItems);
                mRecycler.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ResponseModelJadwal> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
    private void terdekat(){
        FusedLocationProviderClient flpc = LocationServices.getFusedLocationProviderClient(HalamanMajelis.this);
        flpc.getLastLocation().addOnSuccessListener(HalamanMajelis.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    Location awal = new Location("Point A");
                    awal.setLongitude(latitude);
                    awal.setLongitude(longitude);

                    List<String> listJarak = new ArrayList<>() ;

                    for (int i = 0 ; i < mItems.size();i++){
                        double lat =Double.parseDouble(mItems.get(i).getLatitude());
                        double lng = Double.parseDouble(mItems.get(i).getLongitude());
                        Location tujuan = new Location("Point B");
                        tujuan.setLatitude(lat);
                        tujuan.setLongitude(lng);
                        float result[] = new float[1];

                        Location.distanceBetween(latitude,longitude,lat,lng,result);
                        String val = String.format("%.02f",result[0]/1000);
                        listJarak.add(val);
                        mItems.get(i).setJarak_terdekat(val);

                    }
                    Collections.sort(mItems, new Comparator<JadwalModel>() {
                        @Override
                        public int compare(JadwalModel jadwalModel, JadwalModel t1) {
                            return jadwalModel.getJarak_terdekat().compareTo(t1.getJarak_terdekat());
                        }
                    });
                    mAdapter = new AdapterData(HalamanMajelis.this,mItems);
                    mRecycler.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();





                }
            }
        });
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
                mItems = response.body().getResultJadwal();

//                    String lat = mItems.get(0).getLatitude();
//                    String lng = mItems.get(0).getLongitude();
//
//                    latitude.setText(lat);
//                    longitude.setText(lng);

                    mAdapter = new AdapterData(HalamanMajelis.this,mItems);
                    mRecycler.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ResponseModelJadwal> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
