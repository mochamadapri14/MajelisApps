package com.example.majelistalim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.majelistalim.Api.ApiInterface;
import com.example.majelistalim.Api.Retroserver;
import com.example.majelistalim.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterJamaah extends AppCompatActivity {
    EditText e_nama, e_username, e_password, e_ulang;
    Button daftar;
    TextView tutup;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_jamaah);
        e_nama = findViewById(R.id.etNamauser);
        e_username = findViewById(R.id.etUsername);
        e_password = findViewById(R.id.etPassword);
        e_ulang = findViewById(R.id.etPassword2);
        daftar = findViewById(R.id.btnDaftar);
        tutup = findViewById(R.id.close);
        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        progressDialog = new ProgressDialog(this);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama_user = e_nama.getText().toString().trim();
                String username = e_username.getText().toString().trim();
                String password = e_password.getText().toString().trim();
                String password2 = e_ulang.getText().toString();
                if (nama_user.isEmpty()){
                    e_nama.setError("Nama harus diisi");
                }else if (username.isEmpty()){
                    e_username.setError("Username harus diisi");
                }else if (password.isEmpty()){
                    e_password.setError("Password harus diisi");
                }else if (password2.isEmpty()){
                    e_ulang.setError("Konfirmasi Password anda");
                }else if (!password2.equals(password)){
                    e_ulang.setError("Password harus sesuai");
                }
                else {
                    daftarJamaah(nama_user,username,password);
                }
            }
        });
    }
    public void daftarJamaah(final String nama_user, final String username, final String password ){
        progressDialog.setMessage("Mengirim data..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface api = Retroserver.getClient().create(ApiInterface.class);
        Call<ResponseModel> sendData = api.sendRegisJamaah(nama_user,username,password);
        sendData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call,@NonNull Response<ResponseModel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() !=null) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    if (kode.equals("1")) {
                        kosongkan();
                        Toast.makeText(RegisterJamaah.this, pesan, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegisterJamaah.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(RegisterJamaah.this, pesan, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call,@NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegisterJamaah.this, t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void kosongkan(){
        e_nama.setText("");
        e_username.setText("");
        e_password.setText("");
    }
}
