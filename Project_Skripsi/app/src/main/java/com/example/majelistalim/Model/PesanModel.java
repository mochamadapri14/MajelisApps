package com.example.majelistalim.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PesanModel {
    @Expose
    @SerializedName("id_pesan") int id_pesan;
    @Expose
    @SerializedName("majelis_id") int majelis_id;
    @Expose
    @SerializedName("userid") int userid;
    @Expose
    @SerializedName("pesan") String pesan;
    @Expose
    @SerializedName("waktuy") String waktuy;
    @Expose
    @SerializedName("nama_user") String nama_user;
    @Expose
    @SerializedName("level") String level;
    @Expose
    @SerializedName("foto") String foto;
    @Expose
    @SerializedName("id_jadwal") String id_jadwal;
    @Expose
    @SerializedName("nama_majelis") String nama_majelis;
    @Expose
    @SerializedName("nama_alamat") String nama_alamat;
    @Expose
    @SerializedName("tanggal") String tanggal;
    @Expose
    @SerializedName("jam_mulai") String jam_mulai;
    public  PesanModel(){}
    public PesanModel(int id_pesan, int majelis_id, int userid, String pesan,String waktuy, String nama_user,String level, String foto,String id_jadwal, String nama_majelis, String nama_alamat, String tanggal,String jam_mulai) {
        this.id_pesan = id_pesan;
        this.majelis_id = majelis_id;
        this.userid = userid;
        this.pesan = pesan;
        this.waktuy = waktuy;
       this.nama_user = nama_user;
       this.level = level;
       this.foto = foto;
       this.id_jadwal = id_jadwal;
       this.nama_majelis = nama_majelis;
       this.nama_alamat = nama_alamat;
       this.tanggal = tanggal;
       this.jam_mulai = jam_mulai;
    }
    public PesanModel(int id_pesan, int majelis_id, int userid, String pesan,String waktuy, String nama_user,String level, String foto) {
        this.id_pesan = id_pesan;
        this.majelis_id = majelis_id;
        this.userid = userid;
        this.pesan = pesan;
        this.waktuy = waktuy;
        this.nama_user = nama_user;
        this.level = level;
        this.foto = foto;

    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getWaktuy() {
        return waktuy;
    }

    public void setWaktuy(String waktuy) {
        this.waktuy = waktuy;
    }

    public String getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(String id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public String getJam_mulai() {
        return jam_mulai;
    }

    public void setJam_mulai(String jam_mulai) {
        this.jam_mulai = jam_mulai;
    }

    public String getNama_alamat() {
        return nama_alamat;
    }

    public void setNama_alamat(String nama_alamat) {
        this.nama_alamat = nama_alamat;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNama_majelis() {
        return nama_majelis;
    }

    public void setNama_majelis(String nama_majelis) {
        this.nama_majelis = nama_majelis;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }


    public int getId_pesan() {
        return id_pesan;
    }

    public void setId_pesan(int id_pesan) {
        this.id_pesan = id_pesan;
    }

    public int getMajelis_id() {
        return majelis_id;
    }

    public void setMajelis_id(int majelis_id) {
        this.majelis_id = majelis_id;
    }


    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }
}
