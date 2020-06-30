package com.example.majelistalim.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModelJadwal {
    @Expose
    @SerializedName("kode") String kode;
    @Expose
    @SerializedName("pesan") String pesan;
    @Expose
    @SerializedName("jumlah") String jumlah;
    @Expose
    @SerializedName("resultJadwal")
    List<JadwalModel> resultJadwal;
    public ResponseModelJadwal(){}
    public ResponseModelJadwal(String kode, String pesan) {
        this.kode = kode;
        this.pesan = pesan;
    }

    public ResponseModelJadwal(String kode, String pesan, String jumlah, List<JadwalModel> resultJadwal) {
        this.kode = kode;
        this.pesan = pesan;
        this.jumlah = jumlah;
        this.resultJadwal = resultJadwal;
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

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public List<JadwalModel> getResultJadwal() {
        return resultJadwal;
    }

    public void setResultJadwal(List<JadwalModel> resultJadwal) {
        this.resultJadwal = resultJadwal;
    }
}
