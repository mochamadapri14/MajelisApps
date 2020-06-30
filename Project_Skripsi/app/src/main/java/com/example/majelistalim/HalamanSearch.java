package com.example.majelistalim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.majelistalim.Api.ApiInterface;
import com.example.majelistalim.Api.Retroserver;
import com.example.majelistalim.Model.ResponseModelJadwal;
import com.example.majelistalim.Session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HalamanSearch extends AppCompatActivity {
    TextView txtKota, latitude, longitude, tvDKI,tvJakpus, tvJakut, tvJaktim, tvJakbar, tvJaksel;
    TextView jmlDki,jmlJakut, jmlJakpus, jmlJaktim, jmlJakbar, jmlJaksel;

    SessionManager sessionManager;
    CardView  DKI, jakpus, jakut, jaktim, jakbar, jaksel;
    CardView nullDki, nullPusat, nullUtara,nullTimur, nullBarat, nullSelatan;
    CardView jdDki,jdPusat,jdUtara,jdTimur, jdBarat, jdSelatan;
    SwipeRefreshLayout swipe;
    ProgressDialog progressDialog;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_search);
        progressDialog = new ProgressDialog(this);
        toolbar = findViewById(R.id.tbIndex);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        nullBarat= findViewById(R.id.nullJakbar);
        nullPusat = findViewById(R.id.nullJakpus);
        nullSelatan = findViewById(R.id.nullJaksel);
        nullTimur = findViewById(R.id.nullJaktim);
        nullUtara  = findViewById(R.id.nullJakut);
        txtKota = findViewById(R.id.txtKota);

        jdBarat =findViewById(R.id.cdJadwalJakbar);
        jdPusat = findViewById(R.id.cdJadwalJakpus);
        jdSelatan = findViewById(R.id.cdJadwalJaksel);
        jdTimur =findViewById(R.id.cdJadwalJaktim);
        jdUtara = findViewById(R.id.cdJadwalJakut);

        latitude = findViewById(R.id.txtLatitude);
        longitude = findViewById(R.id.txtLongitude);
        swipe = findViewById(R.id.swipenya);
        jakpus = findViewById(R.id.cdJakpus);
        tvJakpus = findViewById(R.id.txtJakpus);
        jakut = findViewById(R.id.cdJakut);
        tvJakut = findViewById(R.id.txtJakut);
        jaktim = findViewById(R.id.cdJaktim);
        tvJaktim = findViewById(R.id.txtJaktim);
        jakbar = findViewById(R.id.cdJakbar);
        tvJakbar = findViewById(R.id.txtJakbar);
        jaksel = findViewById(R.id.cdJaksel);
        tvJaksel = findViewById(R.id.txtJaksel);

        jmlJakut = findViewById(R.id.txtJmlJakut);
        jmlJakpus = findViewById(R.id.txtJmlJakpus);
        jmlJaktim = findViewById(R.id.txtJmlJaktim);
        jmlJakbar = findViewById(R.id.txtJmlJakbar);
        jmlJaksel = findViewById(R.id.txtJmlJaksel);

        sessionManager = new SessionManager(HalamanSearch.this);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                view_byalamatjakpus();
                view_byalamatjakut();
                view_byalamatjaktim();
                view_byalamatjakbar();
                view_byalamatjaksel();
                swipe.setRefreshing(false);
            }
        });

//        DKI.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tvDKI.setText("DKI Jakarta");
//                String dki = tvDKI.getText().toString();
//                Intent intent = new Intent(HalamanSearch.this,HalamanMajelis.class);
//                intent.putExtra("DKI Jakarta", dki);
//                startActivity(intent);
//            }
//        });
        jakpus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvJakpus.setText("Jakarta Pusat");
                String jkpus = tvJakpus.getText().toString();
                Intent intent = new Intent(HalamanSearch.this,HalamanMajelis.class);
                intent.putExtra("Jakarta Pusat", jkpus);
                startActivity(intent);
            }
        });
        jakut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvJakut.setText("Jakarta Utara");
                String jkut = tvJakut.getText().toString();
                Intent intent = new Intent(HalamanSearch.this,HalamanMajelis.class);
                intent.putExtra("Jakarta Utara", jkut);
                startActivity(intent);
            }
        });
        jaktim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvJaktim.setText("Jakarta Timur");
                String jktim = tvJaktim.getText().toString();
                Intent intent = new Intent(HalamanSearch.this,HalamanMajelis.class);
                intent.putExtra("Jakarta Timur", jktim);
                startActivity(intent);
            }
        });
        jakbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvJakbar.setText("Jakarta Barat");
                String jkbar = tvJakbar.getText().toString();
                Intent intent = new Intent(HalamanSearch.this,HalamanMajelis.class);
                intent.putExtra("Jakarta Barat", jkbar);
                startActivity(intent);
            }
        });
        jaksel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvJaksel.setText("Jakarta Selatan");
                String jksel = tvJaksel.getText().toString();
                Intent intent = new Intent(HalamanSearch.this,HalamanMajelis.class);
                intent.putExtra("Jakarta Selatan", jksel);
                startActivity(intent);
            }
        });


            view_byalamatjakpus();
            view_byalamatjakut();
            view_byalamatjaktim();
            view_byalamatjakbar();
            view_byalamatjaksel();


    }
    private void view_byalamatjakpus(){
        String kota = "jakarta pusat";
        ApiInterface apiInterface = Retroserver.getClient().create(ApiInterface.class);
        Call<ResponseModelJadwal> byAlamat = apiInterface.getJadwalByAlamat(kota);
        byAlamat.enqueue(new Callback<ResponseModelJadwal>() {
            @Override
            public void onResponse(Call<ResponseModelJadwal> call, Response<ResponseModelJadwal> response) {
                progressDialog.dismiss();

                String jml = response.body().getJumlah();
                if (jml.equals("0")){
                    jdPusat.setVisibility(View.GONE);
                    nullPusat.setVisibility(View.VISIBLE);
                    jakpus.setEnabled(false);

                }else{
                    jmlJakpus.setText("Ada "+jml+" jadwal");

                }

            }

            @Override
            public void onFailure(Call<ResponseModelJadwal> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
    private void view_byalamatjakut(){
        String kota = "jkt utara";
        ApiInterface apiInterface = Retroserver.getClient().create(ApiInterface.class);
        Call<ResponseModelJadwal> byAlamat = apiInterface.getJadwalByAlamat(kota);
        byAlamat.enqueue(new Callback<ResponseModelJadwal>() {
            @Override
            public void onResponse(Call<ResponseModelJadwal> call, Response<ResponseModelJadwal> response) {
                progressDialog.dismiss();

                String jml = response.body().getJumlah();
                if (jml.equals("0")){
                    nullUtara.setVisibility(View.VISIBLE);
                    jdUtara.setVisibility(View.GONE);
                    jakut.setEnabled(false);
                }else{
                    jmlJakut.setText("Ada "+jml+" jadwal");
                }

            }

            @Override
            public void onFailure(Call<ResponseModelJadwal> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
    private void view_byalamatjaktim(){
        String kota = "jakarta timur";
        ApiInterface apiInterface = Retroserver.getClient().create(ApiInterface.class);
        Call<ResponseModelJadwal> byAlamat = apiInterface.getJadwalByAlamat(kota);
        byAlamat.enqueue(new Callback<ResponseModelJadwal>() {
            @Override
            public void onResponse(Call<ResponseModelJadwal> call, Response<ResponseModelJadwal> response) {
                progressDialog.dismiss();

                String jml = response.body().getJumlah();
                if (jml.equals("0")){
                    nullTimur.setVisibility(View.VISIBLE);
                    jdTimur.setVisibility(View.GONE);
                    jaktim.setEnabled(false);

                }else{
                    jmlJaktim.setText("Ada "+jml+" jadwal");

                }

            }

            @Override
            public void onFailure(Call<ResponseModelJadwal> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
    private void view_byalamatjakbar(){
        String kota = "jakarta barat";
        ApiInterface apiInterface = Retroserver.getClient().create(ApiInterface.class);
        Call<ResponseModelJadwal> byAlamat = apiInterface.getJadwalByAlamat(kota);
        byAlamat.enqueue(new Callback<ResponseModelJadwal>() {
            @Override
            public void onResponse(Call<ResponseModelJadwal> call, Response<ResponseModelJadwal> response) {
                progressDialog.dismiss();

                String jml = response.body().getJumlah();
                if (jml.equals("0")){
                    nullBarat.setVisibility(View.VISIBLE);
                    jdBarat.setVisibility(View.GONE);
                    jakbar.setEnabled(false);

                }else{
                    jmlJakbar.setText("Ada "+jml+" jadwal");
                }

            }

            @Override
            public void onFailure(Call<ResponseModelJadwal> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
    private void view_byalamatjaksel(){
        String kota = "jakarta selatan";
        ApiInterface apiInterface = Retroserver.getClient().create(ApiInterface.class);
        Call<ResponseModelJadwal> byAlamat = apiInterface.getJadwalByAlamat(kota);
        byAlamat.enqueue(new Callback<ResponseModelJadwal>() {
            @Override
            public void onResponse(Call<ResponseModelJadwal> call, Response<ResponseModelJadwal> response) {
                progressDialog.dismiss();

                String jml = response.body().getJumlah();
                if (jml.equals("0")){
                    nullSelatan.setVisibility(View.VISIBLE);
                    jdSelatan.setVisibility(View.GONE);
                    jaksel.setEnabled(false);
                }else{
                    jmlJaksel.setText("Ada "+jml+" jadwal");

                }

            }

            @Override
            public void onFailure(Call<ResponseModelJadwal> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }




//        FusedLocationProviderClient flpc = LocationServices.getFusedLocationProviderClient(this);
//        flpc.getLastLocation().addOnSuccessListener( this, new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//
//                if (location != null){
//                    double latit = location.getLatitude();
//                    double longit = location.getLongitude();
//                    Location awal = new Location("Point A");
//                    awal.setLongitude(latit);
//                    awal.setLongitude(longit);
////
//                    String l1 = latitude.getText().toString();
//                    String l2 = longitude.getText().toString();
//                    final double lat = Double.parseDouble(l1);
//                    final double lng = Double.parseDouble(l2);
//
//                    Location tujuan = new Location("Point B");
//                    tujuan.setLatitude(lat);
//                    tujuan.setLongitude(lng);
//
//                    Location.distanceBetween(latit,longit,lat,lng,result);
//                    TextView t = findViewById(R.id.txtLatLng);
//
//
//                    for (int i = 0; i < result.length; i++){
//                       myStringArray.add(result[i]);
//
//                    }
//
//
//
////
//                }
//            }
//        });


//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.kota,android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        kota.setAdapter(adapter);
//
//        kota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String text = parent.getItemAtPosition(position).toString();
//                if (text.equals("DKI Jakarta")){
//                    view_all();
//                }else if (text.equals("Jakarta Utara")){
//                    txtKota.setText("utara");
//                    view_byalamat();
//                }else if (text.equals("Jakarta Pusat")){
//                    txtKota.setText("pusat");
//                    view_byalamat();
//                }else if (text.equals("Jakarta Timur")){
//                    txtKota.setText("timur");
//                    view_byalamat();
//                }else if (text.equals("Jakarta Barat")){
//                    txtKota.setText("barat");
//                    view_byalamat();
//                }else if (text.equals("Jakarta Selatan")){
//                    txtKota.setText("selatan");
//                    view_byalamat();
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//



    private void view_all(){
        ApiInterface api = Retroserver.getClient().create(ApiInterface.class);
        Call<ResponseModelJadwal> getData = api.getJadwal();
        getData.enqueue(new Callback<ResponseModelJadwal>() {
            @Override
            public void onResponse(Call<ResponseModelJadwal> call, Response<ResponseModelJadwal> response) {
                progressDialog.dismiss();
                String jml = response.body().getJumlah();
                if (jml.equals("0")){
                    nullDki.setVisibility(View.VISIBLE);
                    jdDki.setVisibility(View.GONE);
                    DKI.setEnabled(false);

                }else{
                    jmlDki.setText("Ada "+jml+" jadwal");

                }

//                    String lat = mItems.get(0).getLatitude();
//                    String lng = mItems.get(0).getLongitude();
//
//                    latitude.setText(lat);
//                    longitude.setText(lng);

            }

            @Override
            public void onFailure(Call<ResponseModelJadwal> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
