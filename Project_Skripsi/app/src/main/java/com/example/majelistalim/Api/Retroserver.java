package com.example.majelistalim.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retroserver {
     private static final String base_url = "http://skripsikami.xyz/p_majelis/";
     public static final String url_foto = "http://skripsikami.xyz/p_majelis/upload/";
    public static final String url_foto_user = "http://skripsikami.xyz/p_majelis/uploadUser/";
    public static final String url_foto_awal = "http://skripsikami.xyz/p_majelis/uploadUser/foto_awal.png";
    private static Retrofit retrofit;


    public static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
