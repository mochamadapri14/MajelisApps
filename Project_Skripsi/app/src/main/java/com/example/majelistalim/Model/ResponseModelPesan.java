package com.example.majelistalim.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModelPesan {
    @Expose
    @SerializedName("kode") String kode;
    @Expose
    @SerializedName("message") String message;
    @Expose
    @SerializedName("jumlah") String jumlah;
    @Expose
    @SerializedName("resultPesan")
    List<PesanModel> resultPesan;


    public ResponseModelPesan(){}
    public ResponseModelPesan(String kode, String message) {
        this.kode = kode;
        this.message = message;
    }

    public ResponseModelPesan(String kode, String message, List<PesanModel> resultPesan) {
        this.kode = kode;
        this.message = message;
        this.resultPesan = resultPesan;
    }

    public ResponseModelPesan(String kode, String message,String jumlah, List<PesanModel> resultPesan) {
        this.kode = kode;
        this.message = message;
        this.jumlah = jumlah;
        this.resultPesan = resultPesan;
    }


    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PesanModel> getResultPesan() {
        return resultPesan;
    }

    public void setResultPesan(List<PesanModel> resultPesan) {
        this.resultPesan = resultPesan;
    }
}
