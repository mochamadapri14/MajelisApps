package com.example.majelistalim.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JadwalModel {
    @Expose
    @SerializedName("id_jadwal") String id_jadwal;
    @Expose
    @SerializedName("user_id") String user_id;
    @Expose
    @SerializedName("foto") String foto;
    @Expose
    @SerializedName("nama_majelis") String nama_majelis;
    @Expose
    @SerializedName("tanggal") String tanggal;
    @Expose
    @SerializedName("jam_mulai") String jam_mulai;
    @Expose
    @SerializedName("nama_alamat") String nama_alamat;
    @Expose
    @SerializedName("lokasi") String lokasi;
    @Expose
    @SerializedName("keterangan") String keterangan;
    @Expose
    @SerializedName("waktu") String waktu;
    @Expose
    @SerializedName("latitude") String latitude;
    @Expose
    @SerializedName("longitude") String longitude;
    @Expose
    @SerializedName("nama_user") String nama_user;
    @Expose
    @SerializedName("jarak_terdekat") String jarak_terdekat;
//    @Expose
//    @SerializedName("user")
//    List<UserModel> user;

    public JadwalModel(){

    }

    public JadwalModel(String id_jadwal, String user_id, String foto, String nama_majelis, String tanggal, String jam_mulai,String nama_alamat, String lokasi, String keterangan, String waktu, String latitude, String longitude,String nama_user) {
        this.id_jadwal = id_jadwal;
        this.user_id = user_id;
        this.foto = foto;
        this.nama_majelis = nama_majelis;
        this.tanggal = tanggal;
        this.jam_mulai = jam_mulai;
        this.nama_alamat = nama_alamat;
        this.lokasi = lokasi;
        this.keterangan = keterangan;
        this.waktu = waktu;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nama_user = nama_user;
    }

    public String getNama_alamat() {
        return nama_alamat;
    }

    public void setNama_alamat(String nama_alamat) {
        this.nama_alamat = nama_alamat;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(String id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNama_majelis() {
        return nama_majelis;
    }

    public void setNama_majelis(String nama_majelis) {
        this.nama_majelis = nama_majelis;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam_mulai() {
        return jam_mulai;
    }

    public void setJam_mulai(String jam_mulai) {
        this.jam_mulai = jam_mulai;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getJarak_terdekat() {
        return jarak_terdekat;
    }

    public void setJarak_terdekat(String jarak_terdekat) {
        this.jarak_terdekat = jarak_terdekat;
    }
}
