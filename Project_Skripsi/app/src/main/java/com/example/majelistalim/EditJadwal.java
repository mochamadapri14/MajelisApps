package com.example.majelistalim;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


import com.example.majelistalim.Api.ApiInterface;
import com.example.majelistalim.Api.Retroserver;
import com.example.majelistalim.Model.JadwalModel;
import com.example.majelistalim.Model.ResponseModelJadwal;
import com.example.majelistalim.Session.SessionManager;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditJadwal extends AppCompatActivity {
    EditText txtNama, txtTanggal, txtJam, txtLokasi, txtKet, id, txtFoto,txtLengkap,txtLatitude,txtLongitude;
    TextView btnLokasi,btnTanggal,btnJam, ganti;
    ImageView gambar, save;
    ProgressDialog progressDialog;
    final int IMG_REQUEST = 1;
    final int AUTOCOMPLETE_REQUEST_CODE = 2;
    String API_KEY = "AIzaSyCdleWRh80PRCHRWspYqq46d8GSKxlspnY";
    Bitmap bitmap;
    SessionManager sessionManager;
    Toolbar toolbar;
    public String alamat= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_jadwal);
        toolbar= findViewById(R.id.tbIndex);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        save = findViewById(R.id.btnSave);
        btnLokasi = findViewById(R.id.btnLocation);
        btnTanggal = findViewById(R.id.btnTanggal);
        btnJam = findViewById(R.id.btnJam);
        txtNama = findViewById(R.id.etNamaMajelis);
        txtTanggal = findViewById(R.id.etTanggal);
        txtJam = findViewById(R.id.etJam);
        txtLokasi = findViewById(R.id.etLokasi);
        txtKet = findViewById(R.id.etKeterangan);
        ganti = findViewById(R.id.btnGanti);
        gambar = findViewById(R.id.imgView);
        id = findViewById(R.id.etId);
        txtLatitude = findViewById(R.id.etLatitude);
        txtLongitude = findViewById(R.id.etLongitude);
        txtLengkap = findViewById(R.id.etLengkap);
        txtFoto = findViewById(R.id.etFoto);
        progressDialog = new ProgressDialog(EditJadwal.this);
        sessionManager = new SessionManager(EditJadwal.this);
        final Intent data = getIntent();
        String idjadwal = data.getStringExtra("id_jadwal");
        id.setText(idjadwal);
        String foto = data.getStringExtra("foto");
        txtFoto.setText(foto);
        Picasso.get().load(Retroserver.url_foto+foto)
                .fit()
                .centerInside().into(gambar);

        txtNama.setText(data.getStringExtra("nama_majelis"));
        txtTanggal.setText(data.getStringExtra("tanggal"));
        txtJam.setText(data.getStringExtra("jam_mulai"));
        txtLokasi.setText(data.getStringExtra("nama_alamat"));
        txtLengkap.setText(data.getStringExtra("lokasi"));
        txtKet.setText(data.getStringExtra("keterangan"));
        txtLatitude.setText(data.getStringExtra("latitude"));
        txtLongitude.setText(data.getStringExtra("longitude"));

        ganti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihGambar();
            }
        });
        final List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        Places.initialize(getApplicationContext(), API_KEY);
        txtLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(EditJadwal.this);
                startActivityForResult(i, AUTOCOMPLETE_REQUEST_CODE);

            }
        });
        btnLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(EditJadwal.this);
                startActivityForResult(i, AUTOCOMPLETE_REQUEST_CODE);

            }
        });

        txtTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               inputTanggal();
            }
        });

        btnTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               inputTanggal();
            }
        });
        txtJam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               inputJam();

            }
        });
        btnJam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputJam();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id_jadwal = id.getText().toString();
                String fotolama = data.getStringExtra("foto");
                String nama_majelis = txtNama.getText().toString();
                String tanggal = txtTanggal.getText().toString();
                String jam_mulai = txtJam.getText().toString();
                String nama_alamat = txtLokasi.getText().toString();
                String lokasi = txtLengkap.getText().toString();
                String keterangan = txtKet.getText().toString();
                String latitude = txtLatitude.getText().toString();
                String longitude = txtLongitude.getText().toString();

                if(alamat.equals("")){
                    progressDialog.setMessage("Menunggu..");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    ApiInterface apiInterface = Retroserver.getClient().create(ApiInterface.class);
                    Call<ResponseModelJadwal> editJdwl = apiInterface.updateJadwal(id_jadwal,fotolama,nama_majelis,tanggal,jam_mulai,nama_alamat,lokasi,keterangan,latitude,longitude);
                    editJdwl.enqueue(new Callback<ResponseModelJadwal>() {
                        @Override
                        public void onResponse(Call<ResponseModelJadwal> call, Response<ResponseModelJadwal> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful() && response.body() != null) {
                                String kode = response.body().getKode();
                                String pesan = response.body().getPesan();
                                if (kode.equals("1")) {
                                    Toast.makeText(EditJadwal.this, pesan, Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(EditJadwal.this, HalamanUtama.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);

                                } else {
                                    Toast.makeText(EditJadwal.this, pesan, Toast.LENGTH_SHORT).show();

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModelJadwal> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(EditJadwal.this,t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                    });
                }else{

                    RequestBody idjadwal =  RequestBody.create(MediaType.parse("multipart/form-file"),id.getText().toString());

                    File file = templateFile(bitmap);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), file);
                    MultipartBody.Part partImage = MultipartBody.Part.createFormData("foto", file.getName(), requestBody);
                    RequestBody nama = RequestBody.create(MediaType.parse("multipart/form-file"), nama_majelis);
                    RequestBody tgl = RequestBody.create(MediaType.parse("multipart/form-file"), tanggal);
                    RequestBody jam = RequestBody.create(MediaType.parse("multipart/form-file"), jam_mulai);
                    RequestBody alamat = RequestBody.create(MediaType.parse("multipart/form-file"), nama_alamat);
                    RequestBody lokasinya = RequestBody.create(MediaType.parse("multipart/form-file"), lokasi);
                    RequestBody ket = RequestBody.create(MediaType.parse("multipart/form-file"), keterangan);
                    RequestBody lat = RequestBody.create(MediaType.parse("multipart/form-file"),latitude);
                    RequestBody lot = RequestBody.create(MediaType.parse("multipart/form-file"),longitude);
                    RequestBody fotolamaR = RequestBody.create(MediaType.parse("multipart/form-file"), fotolama);
//                    edit(idjadwal,partImage,nama,tgl,jam,alamat,lokasinya,ket,lat,lot,fotolamaR);
                    progressDialog.setMessage("Menunggu..");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    ApiInterface apiInterface = Retroserver.getClient().create(ApiInterface.class);
                    Call<ResponseModelJadwal> editJadwal = apiInterface.updateJadwalFoto(idjadwal,partImage,nama,tgl,jam,alamat,lokasinya,ket,lat,lot,fotolamaR);
                    editJadwal.enqueue(new Callback<ResponseModelJadwal>() {
                        @Override
                        public void onResponse(Call<ResponseModelJadwal> call, Response<ResponseModelJadwal> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful() && response.body() != null) {
                                String kode = response.body().getKode();
                                String pesan = response.body().getPesan();
                                if (kode.equals("1")) {
                                    Toast.makeText(EditJadwal.this, pesan, Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(EditJadwal.this, HalamanUtama.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);

                                } else {
                                    Toast.makeText(EditJadwal.this, pesan, Toast.LENGTH_SHORT).show();

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModelJadwal> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(EditJadwal.this,t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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

        if (resultCode == RESULT_OK){
            if (requestCode ==  IMG_REQUEST){
                Uri path = data.getData();


                try{
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                    alamat = path.toString();
                    gambar.setImageBitmap(bitmap);
                    Picasso.get().load(alamat)
                            .fit()
                            .centerInside().into(gambar);

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (requestCode == AUTOCOMPLETE_REQUEST_CODE){
                Place place = Autocomplete.getPlaceFromIntent(data);
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
                String address = String.valueOf(place.getAddress());
                String loc = place.getName().toString();
                txtLatitude.setText(latitude);
                txtLongitude.setText(longitude);
                txtLengkap.setText(address);
                txtLokasi.setText(loc);

            }

        }

    }
    private void inputTanggal(){
        final Calendar calendar = Calendar.getInstance();
        final int hari = calendar.get(Calendar.DAY_OF_MONTH);
        final int bulan = calendar.get(Calendar.MONTH);
        final int tahun = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(EditJadwal.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                cal.set(Calendar.MONTH, month);
                String format = new SimpleDateFormat("yyyy/MM/dd").format(cal.getTime());

                txtTanggal.setText(format);
            }
        },tahun,bulan,hari);
        datePickerDialog.show();
    }
    private void inputJam(){
        final Calendar calendar = Calendar.getInstance();
        final int jam = calendar.get(Calendar.HOUR_OF_DAY);
        final int menit = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(EditJadwal.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (minute < 10){
                    txtJam.setText(hourOfDay+":0"+minute);
                }else if (hourOfDay < 10){
                    txtJam.setText("0"+hourOfDay+":"+minute);
                }
                else if (hourOfDay == 0 ){
                    txtJam.setText(hourOfDay+"0:"+minute);
                }
                else {
                    txtJam.setText(hourOfDay+":"+minute);
                }

            }
        },jam,menit,android.text.format.DateFormat.is24HourFormat(EditJadwal.this));
        timePickerDialog.show();
    }

    private void pilihGambar(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, IMG_REQUEST);

    }
}
