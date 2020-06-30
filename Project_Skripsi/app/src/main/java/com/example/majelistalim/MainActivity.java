package com.example.majelistalim;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.majelistalim.Api.ApiInterface;
import com.example.majelistalim.Api.Retroserver;
import com.example.majelistalim.Model.ResponseModel;
import com.example.majelistalim.Model.UserModel;
import com.example.majelistalim.Session.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button login,register,registerJamaah;
    private ProgressDialog progressDialog;
    private static final String TAG= MainActivity.class.getSimpleName();
    private SessionManager sessionManager;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.tbIndex);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        username = findViewById(R.id.fUsername);
        password = findViewById(R.id.fPassword);
        login = findViewById(R.id.btnLogin);
        register = findViewById(R.id.btnRegister);
        registerJamaah = findViewById(R.id.btnRegisterJamaah);
        progressDialog = new ProgressDialog(this);
        sessionManager = new SessionManager(this);
        progressDialog.setMessage("Tunggu..");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String f_user = username.getText().toString();
                String f_pass = password.getText().toString();

                ApiInterface api = Retroserver.getClient().create(ApiInterface.class);
                Call<ResponseModel> loginnih = api.login(f_user,f_pass);
                loginnih.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        progressDialog.dismiss();
//                        Log.d("Debug", response.message());
//                        Log.d("debug", String.valueOf(response.raw()));
//                        Toast.makeText(MainActivity.this,"response",Toast.LENGTH_SHORT).show();

                        String kode = response.body().getKode();
                        String pesan = response.body().getPesan();
                        List<UserModel> user = response.body().getResult();

                        if (kode.equals("1")){
                            UserModel userModel = user.get(0);
                            sessionManager.setId(userModel.getId_user());
                            sessionManager.setFoto(userModel.getFoto());
                            sessionManager.setNama(userModel.getNama_user());
                            sessionManager.setUsername(userModel.getUsername());
                            sessionManager.setPassword(userModel.getPassword());
                            sessionManager.setLevel(userModel.getLevel());
//                            sessionManager.storeLogin(user.get(0).getUsername(), user.get(0).getNama_user());
                            Toast.makeText(MainActivity.this,pesan, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(MainActivity.this, HalamanIndex.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(MainActivity.this,pesan, Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.d("Error", t.getMessage());
                        Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Register.class);
                startActivity(i);
            }
        });
        registerJamaah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterJamaah.class);
                startActivity(intent);
            }
        });
    }
}
