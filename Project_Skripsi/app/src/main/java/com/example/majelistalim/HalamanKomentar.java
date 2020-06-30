package com.example.majelistalim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.majelistalim.Adapter.AdapterData;
import com.example.majelistalim.Adapter.AdapterPesan;
import com.example.majelistalim.Api.ApiInterface;
import com.example.majelistalim.Api.Retroserver;
import com.example.majelistalim.Model.PesanModel;
import com.example.majelistalim.Model.ResponseModelPesan;
import com.example.majelistalim.Session.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HalamanKomentar extends AppCompatActivity {
    TextView kirim, idjadwal,majelis,tgl, isi, login;
    EditText etPesan;
    ProgressDialog progressDialog;
    SessionManager sessionManager;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<PesanModel> mItems = new ArrayList<>();
    Toolbar toolbar;
//    SwipeRefreshLayout swipe;
    LinearLayout selesai,belum, logindulu, komen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_komentar);
        login = findViewById(R.id.btnMasuk);
        toolbar= findViewById(R.id.tbIndex);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        etPesan  = findViewById(R.id.etPesan);
        selesai = findViewById(R.id.selesai);
        belum = findViewById(R.id.belum);
        komen = findViewById(R.id.lKomentar);
//        swipe =findViewById(R.id.swipenya);
        kirim = findViewById(R.id.btnSend);
        idjadwal = findViewById(R.id.idnya);
        majelis = findViewById(R.id.majelis);
        tgl = findViewById(R.id.tgl);
        isi = findViewById(R.id.tvData);
        logindulu = findViewById(R.id.logindulu);

        mRecycler = findViewById(R.id.recyclerTemp);
        sessionManager = new SessionManager(HalamanKomentar.this);
        progressDialog = new ProgressDialog(HalamanKomentar.this);
        mManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecycler.setLayoutManager(mManager);

        final Intent data = getIntent();
        final String majelis_id = data.getStringExtra("id_jadwal");

        progressDialog.setMessage("Menunggu..");
        progressDialog.setCancelable(false);
        progressDialog.show();


        SimpleDateFormat formatnya = new SimpleDateFormat("yyyy/MM/dd");
        Date baruw = null;
        try {
            baruw= formatnya.parse(data.getStringExtra("tanggal"));
            formatnya = new SimpleDateFormat("EEEE, d MMMM yyyy");
            String date= formatnya.format(baruw);
            tgl.setText(date+" - "+data.getStringExtra("jam_mulai")+" WIB");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        majelis.setText(data.getStringExtra("nama_majelis")+", di "+data.getStringExtra("nama_alamat"));


        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
        String currDate = format1.format(d);
        int waktu_sekarang = Integer.parseInt(currDate);


        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date baru = null;
        try {
            baru = format.parse(data.getStringExtra("tanggal"));
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
        idjadwal.setText(majelis_id);
        tampil();

        if (sessionManager.getLevel() == null){
            etPesan.setEnabled(false);
            kirim.setEnabled(false);
            logindulu.setVisibility(View.VISIBLE);
            komen.setVisibility(View.GONE);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HalamanKomentar.this, MainActivity.class);
                startActivity(i);
            }
        });
        etPesan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null){
                    kirim.setVisibility(View.VISIBLE);
                }
                if (charSequence.toString().equals("")){
                    kirim.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid = Integer.toString(sessionManager.getId());
                String pesan = etPesan.getText().toString();
                komentar(majelis_id,userid,pesan);
            }
        });
    }
    private void tampil(){
        String majelis_id = idjadwal.getText().toString();
        ApiInterface apiInterface = Retroserver.getClient().create(ApiInterface.class);
        Call<ResponseModelPesan> lihatP = apiInterface.lihatPesan(majelis_id);
        lihatP.enqueue(new Callback<ResponseModelPesan>() {
            @Override
            public void onResponse(Call<ResponseModelPesan> call, Response<ResponseModelPesan> response) {
                progressDialog.dismiss();
                mItems = response.body().getResultPesan();

                int id = sessionManager.getId();
                if (mItems.isEmpty()){
                    isi.setVisibility(View.VISIBLE);
                }
                if (mItems.size()>0) {
                    isi.setVisibility(View.GONE);
                    mAdapter = new AdapterPesan(HalamanKomentar.this, mItems);
                    mRecycler.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onFailure(Call<ResponseModelPesan> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
           }
    public void komentar(final String majelis_id, final String userid, final String pesan){
        progressDialog.setMessage("Mengirim data..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface api = Retroserver.getClient().create(ApiInterface.class);
        Call<ResponseModelPesan> sendKomentar = api.sendPesan(majelis_id,userid,pesan);
        sendKomentar.enqueue(new Callback<ResponseModelPesan>() {
            @Override
            public void onResponse(Call<ResponseModelPesan> call, Response<ResponseModelPesan> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null){
                    String kode = response.body().getKode();
                    String msg = response.body().getMessage();
                    if (kode.equals("1")){
                        isi.setVisibility(View.GONE);
                        etPesan.setText("");
                        Toast.makeText(HalamanKomentar.this,msg,Toast.LENGTH_SHORT).show();
                        tampil();
                    }else{
                        Toast.makeText(HalamanKomentar.this,"Pesan anda kosong",Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModelPesan> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(HalamanKomentar.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
