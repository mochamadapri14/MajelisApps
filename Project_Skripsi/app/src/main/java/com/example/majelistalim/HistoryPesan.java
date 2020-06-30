package com.example.majelistalim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.majelistalim.Adapter.AdapterData;
import com.example.majelistalim.Adapter.AdapterRiwayat;
import com.example.majelistalim.Api.ApiInterface;
import com.example.majelistalim.Api.Retroserver;
import com.example.majelistalim.Model.JadwalModel;
import com.example.majelistalim.Model.PesanModel;
import com.example.majelistalim.Model.ResponseModelJadwal;
import com.example.majelistalim.Model.ResponseModelPesan;
import com.example.majelistalim.Session.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryPesan extends AppCompatActivity {
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<PesanModel> mItems = new ArrayList<>();
    SessionManager sessionManager;
    Toolbar toolbar;
    SwipeRefreshLayout swipe;
    TextView jumlah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_pesan);
        toolbar = findViewById(R.id.tbIndex);
        toolbar= findViewById(R.id.tbIndex);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        jumlah = findViewById(R.id.jumlah);
        swipe = findViewById(R.id.swipenya);
        sessionManager = new SessionManager(HistoryPesan.this);
        mRecycler = findViewById(R.id.recyclerTemp);
        mManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecycler.setLayoutManager(mManager);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tampil();
                swipe.setRefreshing(false);
            }
        });
        tampil();


    }
    private void tampil(){
        String userid = Integer.toString(sessionManager.getId());
        ApiInterface api = Retroserver.getClient().create(ApiInterface.class);
        Call<ResponseModelPesan> getData = api.lihatRiwayat(userid);
        getData.enqueue(new Callback<ResponseModelPesan>() {
            @Override
            public void onResponse(Call<ResponseModelPesan> call, Response<ResponseModelPesan> response) {
                mItems = response.body().getResultPesan();
                String jumlax = response.body().getJumlah();
                jumlah.setText(jumlax + " Riwayat Pesan Anda");
                mAdapter = new AdapterRiwayat(HistoryPesan.this,mItems);
                mRecycler.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ResponseModelPesan> call, Throwable t) {

            }
        });
    }

}
