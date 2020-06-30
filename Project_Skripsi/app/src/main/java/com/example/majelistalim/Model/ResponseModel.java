package com.example.majelistalim.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModel {
    @Expose
    @SerializedName("kode") String kode;
    @Expose
    @SerializedName("pesan") String pesan;
    @Expose
    @SerializedName("result")
    List<UserModel> result;


    public ResponseModel(){}

    public ResponseModel(String kode, String pesan) {
        this.kode = kode;
        this.pesan = pesan;
    }

    public ResponseModel(String kode, String pesan, List<UserModel> result) {
        this.kode = kode;
        this.pesan = pesan;
        this.result = result;
    }

    public List<UserModel> getResult() {
        return result;
    }

    public void setResult(List<UserModel> result) {
        this.result = result;
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
}
