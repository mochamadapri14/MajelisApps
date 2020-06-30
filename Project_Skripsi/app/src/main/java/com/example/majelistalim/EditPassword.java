package com.example.majelistalim;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.majelistalim.Api.ApiInterface;
import com.example.majelistalim.Api.Retroserver;
import com.example.majelistalim.Model.ResponseModel;
import com.example.majelistalim.Session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPassword extends AppCompatActivity {
    EditText password1, password2, username;
    TextView tutup;
    Button simpan;
    ProgressDialog progressDialog;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        password1 = findViewById(R.id.etPassword);
        password2 = findViewById(R.id.etPassword2);
        simpan = findViewById(R.id.btnSimpan);
        tutup = findViewById(R.id.close);
        progressDialog = new ProgressDialog(EditPassword.this);
        sessionManager = new SessionManager(EditPassword.this);


        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String pass1 = password1.getText().toString();
                final String pass2 = password2.getText().toString();

                if (pass1.isEmpty()){
                    password1.setError("Password Baru Kosong");
                }else if (pass2.isEmpty()){
                    password2.setError("Konfirmasi password baru");
                }
                else if (!pass2.equals(pass1)){
                    password2.setError("Password harus sesuai");
                }
                else {
                    progressDialog.setMessage("Menyimpan data..");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    String id = Integer.toString(sessionManager.getId());

                    ApiInterface api = Retroserver.getClient().create(ApiInterface.class);
                    Call<ResponseModel> ubah = api.ubahPass(id, pass1);
                    ubah.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            Log.d("Check success", response.message());
                            progressDialog.dismiss();
                            String kode = response.body().getKode();
                            String pesan = response.body().getPesan();
                            if (kode.equals("1")) {
                                sessionManager.logout();
                                Toast.makeText(EditPassword.this, pesan, Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(EditPassword.this, HalamanIndex.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                Toast.makeText(EditPassword.this, "Silahkan Login ulang!", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(EditPassword.this, pesan, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            progressDialog.dismiss();
                            Log.d("Check success", t.getMessage());
                            Toast.makeText(EditPassword.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
