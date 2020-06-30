package com.example.majelistalim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.majelistalim.Api.ApiInterface;
import com.example.majelistalim.Api.Retroserver;
import com.example.majelistalim.Model.ResponseModel;
import com.example.majelistalim.Model.ResponseModelJadwal;
import com.example.majelistalim.Model.UserModel;
import com.example.majelistalim.Session.SessionManager;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUser extends AppCompatActivity {
    EditText e_nama, e_username, e_password;
    Button simpan;
    TextView ganti,tutup, gantiFoto;
    ImageView foto;
    ProgressDialog progressDialog;
    SessionManager sessionManager;
     public String alamat="";
    final int IMG_REQUEST = 1;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        tutup= findViewById(R.id.close);
        gantiFoto = findViewById(R.id.btnGanti);
        foto = findViewById(R.id.imgView);
        e_nama = findViewById(R.id.etNamauser);
        e_username = findViewById(R.id.etUsername);
        e_password = findViewById(R.id.etPassword);
        ganti = findViewById(R.id.txtGanti);
        simpan = findViewById(R.id.btnSimpan);
        progressDialog = new ProgressDialog(EditUser.this);
        sessionManager = new SessionManager(EditUser.this);
        if (sessionManager.getFoto()==null){
            Glide.with(EditUser.this).load(Retroserver.url_foto_awal)
                    .apply(RequestOptions.circleCropTransform()).into(foto);
        }else{
            Glide.with(EditUser.this).load(Retroserver.url_foto_user+sessionManager.getFoto())
                    .apply(RequestOptions.circleCropTransform()).into(foto);
        }


        final Intent data =  getIntent();
        e_password.setText(data.getStringExtra("password"));

        e_nama.setText(data.getStringExtra("nama_user"));
        e_username.setText(data.getStringExtra("username"));
        ganti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent i = new Intent(EditUser.this, EditPassword.class);
              startActivity(i);
            }
        });
        gantiFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pilihGambar();
            }
        });
        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fotolama="";
                final String nama_user = e_nama.getText().toString();
                final String username = e_username.getText().toString();
                fotolama = sessionManager.getFoto();
                String id = Integer.toString(sessionManager.getId());
                if (alamat.equals("")){
                    progressDialog.setMessage("Mengubah data..");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    ApiInterface api = Retroserver.getClient().create(ApiInterface.class);
                    Call<ResponseModel> ubah = api.ubahData(id,fotolama,nama_user,username);
                    ubah.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            Log.d("Check success",response.message());
                            progressDialog.dismiss();
                            String kode = response.body().getKode();
                            String pesan = response.body().getPesan();
                            if (kode.equals("1")) {
                                sessionManager.setNama(nama_user);
                                sessionManager.setUsername(username);
                                Toast.makeText(EditUser.this, pesan, Toast.LENGTH_SHORT).show();
                                Intent i= new Intent(EditUser.this, HalamanIndex.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(EditUser.this, pesan, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            progressDialog.dismiss();
                            Log.d("Check success",t.getMessage());
                            Toast.makeText(EditUser.this, t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    if (fotolama == null){
                        fotolama ="";
                    }

                    RequestBody iduser =  RequestBody.create(MediaType.parse("multipart/form-file"),id);

                    File file = templateFile(bitmap);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), file);
                    final MultipartBody.Part partImage = MultipartBody.Part.createFormData("foto", file.getName(), requestBody);
                    RequestBody fotolamaR = RequestBody.create(MediaType.parse("multipart/form-file"), fotolama);
                    RequestBody nama_userR = RequestBody.create(MediaType.parse("multipart/form-file"), nama_user);
                    RequestBody usernameR = RequestBody.create(MediaType.parse("multipart/form-file"), username);
                    progressDialog.setMessage("Menunggu..");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    ApiInterface apiInterface = Retroserver.getClient().create(ApiInterface.class);
                    Call<ResponseModel> editJadwal = apiInterface.ubahFoto(iduser,partImage,fotolamaR,nama_userR,usernameR);
                    editJadwal.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful() && response.body() != null) {
                                String kode = response.body().getKode();
                                String pesan = response.body().getPesan();
                                List<UserModel> userModels = response.body().getResult();
                                if (kode.equals("1")) {
                                    sessionManager.setFoto(userModels.get(0).getFoto());
                                    sessionManager.setNama(userModels.get(0).getNama_user());
                                    sessionManager.setUsername(userModels.get(0).getUsername());
                                    Toast.makeText(EditUser.this, pesan, Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(EditUser.this, HalamanIndex.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);

                                } else {
                                    Toast.makeText(EditUser.this, pesan, Toast.LENGTH_SHORT).show();

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(EditUser.this,t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

    }

    private File templateFile(Bitmap bitmap){
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                System.currentTimeMillis() + "_image.webp");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP,70,byteArrayOutputStream);
        byte[] databitmap = byteArrayOutputStream.toByteArray();
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(databitmap);
            fileOutputStream.flush();
            fileOutputStream.close();

        }catch (IOException e){
            e.printStackTrace();
        }
        return file;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == IMG_REQUEST) {
                Uri path = data.getData();


                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                    alamat = path.toString();
                    foto.setImageBitmap(bitmap);
                    Glide.with(EditUser.this).load(alamat)
                            .apply(RequestOptions.circleCropTransform()).into(foto);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void pilihGambar(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, IMG_REQUEST);

    }
}
