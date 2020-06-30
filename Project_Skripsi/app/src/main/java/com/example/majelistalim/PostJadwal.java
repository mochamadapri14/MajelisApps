package com.example.majelistalim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
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
import androidx.core.app.NotificationCompat;

import com.example.majelistalim.Api.ApiInterface;
import com.example.majelistalim.Api.Retroserver;
import com.example.majelistalim.Model.ResponseModel;
import com.example.majelistalim.Model.ResponseModelJadwal;
import com.example.majelistalim.Session.SessionManager;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
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

public class PostJadwal extends AppCompatActivity {
    EditText txtNama, txtTanggal, txtJam, txtLokasi, txtKet, id,txtLat, txtLong, txtLengkap;
    Button ambil,ganti;
    TextView warning,btnLokasi,btnTanggal,btnJam;
    ImageView gambar;
    ProgressDialog progressDialog;
    int IMG_REQUEST = 1;
    int AUTOCOMPLETE_REQUEST_CODE = 2;

    String API_KEY = "AIzaSyCdleWRh80PRCHRWspYqq46d8GSKxlspnY";
    Bitmap bitmap;
    SessionManager sessionManager;
    private String alamat ="";
    String latitude ="";
    String longitude = "";
    CardView cdimage;
    Toolbar toolbar;
    ImageView save;
    private int hari, bulan , tahun, jam, menit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_jadwal);
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
        ambil = findViewById(R.id.btnAmbil);
        gambar = findViewById(R.id.imgView);
        id = findViewById(R.id.etId);
        txtLat = findViewById(R.id.etLatitude);
        txtLong = findViewById(R.id.etLongitude);
        txtLengkap = findViewById(R.id.etLengkap);
        cdimage = findViewById(R.id.cdImage);
        progressDialog = new ProgressDialog(PostJadwal.this);
        sessionManager = new SessionManager(PostJadwal.this);

        ambil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, IMG_REQUEST);
        }
        });

        final List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS,
                Place.Field.LAT_LNG);
        Places.initialize(getApplicationContext(), API_KEY);

        txtLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(PostJadwal.this);
                startActivityForResult(i, AUTOCOMPLETE_REQUEST_CODE);
            }
        });
        btnLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(PostJadwal.this);
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
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                int userid = sessionManager.getId();
                String nama_maj = txtNama.getText().toString();
                String latit = txtLat.getText().toString();
                if(alamat.equals("")) {
                    Toast.makeText(PostJadwal.this, "Pilih gambar!", Toast.LENGTH_SHORT).show();
                }

                else {

                    File file = templateFile(bitmap);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), file);
                    MultipartBody.Part partImage = MultipartBody.Part.createFormData("foto", file.getName(), requestBody);
                    RequestBody nama_majelis = RequestBody.create(MediaType.parse("multipart/form-file"), nama_maj);
                    RequestBody tanggal = RequestBody.create(MediaType.parse("multipart/form-file"), txtTanggal.getText().toString());
                    RequestBody jam_mulai = RequestBody.create(MediaType.parse("multipart/form-file"), txtJam.getText().toString());
                    RequestBody nama_alamat = RequestBody.create(MediaType.parse("multipart/form-file"), txtLokasi.getText().toString());
                    RequestBody lokasi = RequestBody.create(MediaType.parse("multipart/form-file"), txtLengkap.getText().toString());
                    RequestBody keterangan = RequestBody.create(MediaType.parse("multipart/form-file"), txtKet.getText().toString());
                    RequestBody latitude = RequestBody.create(MediaType.parse("multipart/form-file"), txtLat.getText().toString());
                    RequestBody longitude = RequestBody.create(MediaType.parse("multipart/form-file"), txtLong.getText().toString());

                    if (hari == 0 && bulan == 0 && tahun == 0){
                        Toast.makeText(PostJadwal.this,"Pilih tanggal, Klik icon tanggal",Toast.LENGTH_LONG).show();
                    }else if (latit.equals("")) {
                        Toast.makeText(PostJadwal.this,"Pilih lokasi dari MAPS, Klik icon lokasi",Toast.LENGTH_LONG).show();
                    }



                    else {
                        progressDialog.setMessage("Menunggu..");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        ApiInterface api = Retroserver.getClient().create(ApiInterface.class);
                        Call<ResponseModelJadwal> postData = api.sendJadwal(userid, partImage, nama_majelis, tanggal, jam_mulai, nama_alamat, lokasi, keterangan, latitude, longitude);
                        postData.enqueue(new Callback<ResponseModelJadwal>() {
                            @Override
                            public void onResponse(@NonNull Call<ResponseModelJadwal> call, @NonNull Response<ResponseModelJadwal> response) {
                                progressDialog.dismiss();
                                if (response.isSuccessful() && response.body() != null) {
                                    String kode = response.body().getKode();
                                    String pesan = response.body().getPesan();
                                    if (kode.equals("1")) {

                                        kosongkan();
                                        Toast.makeText(PostJadwal.this, pesan, Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(PostJadwal.this, HalamanUtama.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);

                                    } else {
                                        Toast.makeText(PostJadwal.this, pesan, Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<ResponseModelJadwal> call, @NonNull Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(PostJadwal.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
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
                gambar.setVisibility(View.VISIBLE);
                cdimage.setVisibility(View.VISIBLE);
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
                latitude = String.valueOf(place.getLatLng().latitude);
                longitude = String.valueOf(place.getLatLng().longitude);
                String address = String.valueOf(place.getAddress());
                String loc = place.getName().toString();
                txtLat.setText(latitude);
                txtLong.setText(longitude);
                txtLengkap.setText(address);
                txtLokasi.setText(loc);
            }
        }


    }
    private void inputTanggal(){
        final Calendar calendar = Calendar.getInstance();
        hari = calendar.get(Calendar.DAY_OF_MONTH);
        bulan = calendar.get(Calendar.MONTH);
        tahun = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(PostJadwal.this, new DatePickerDialog.OnDateSetListener() {
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
        jam = calendar.get(Calendar.HOUR_OF_DAY);
        menit = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(PostJadwal.this, new TimePickerDialog.OnTimeSetListener() {
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
        },jam,menit,android.text.format.DateFormat.is24HourFormat(PostJadwal.this));
        timePickerDialog.show();
    }

    private void kosongkan(){
        txtNama.setText("");
        txtTanggal.setText("");
        txtJam.setText("");
        txtLokasi.setText("");
        txtKet.setText("");
        txtLengkap.setText("");
        txtLong.setText("");
        txtLat.setText("");
//

    }


}
