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

public class Register extends AppCompatActivity {
    EditText e_nama, e_username, e_password, e_ulang;
    TextView tutup;
    Button daftar;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        e_nama = findViewById(R.id.etNamauser);
        e_username = findViewById(R.id.etUsername);
        e_password = findViewById(R.id.etPassword);
        e_ulang = findViewById(R.id.etPassword2);
        daftar = findViewById(R.id.btnDaftar);
        tutup = findViewById(R.id.close);
        progressDialog = new ProgressDialog(this);
        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String nama_user = e_nama.getText().toString().trim();
                String username = e_username.getText().toString().trim();
                String password = e_password.getText().toString();
                String password2 = e_ulang.getText().toString();
                if (nama_user.isEmpty()){
                    e_nama.setError("Nama harus diisi");
                }else if (username.isEmpty()){
                    e_username.setError("Username harus diisi");
                }else if (password.isEmpty()){
                    e_password.setError("Password harus diisi");
                }else if (password2.isEmpty()){
                    e_ulang.setError("Konfirmasi password");
                }else if (!password2.equals(password)){
                    e_ulang.setError("Password harus sesuai");
                }
                    else {
                      daftarUser(nama_user,username,password);
                }
            }
        });
    }
    private void kosongkan(){
        e_nama.setText("");
        e_username.setText("");
        e_password.setText("");
        e_ulang.setText("");
    }
    private void daftarUser(final String nama_user,final String username,final String password){
        progressDialog.setMessage("Mengirim data..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface api = Retroserver.getClient().create(ApiInterface.class);
        Call<ResponseModel> send = api.sendRegis(nama_user,username,password);
        send.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call,@NonNull Response<ResponseModel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() !=null) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    if (kode.equals("1")) {
                        kosongkan();
                        Toast.makeText(Register.this, pesan, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Register.this, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                    } else {
                        Toast.makeText(Register.this, pesan, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call,@NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Register.this, t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }
}
