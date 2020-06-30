package com.example.majelistalim.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {
   @Expose
   @SerializedName("id_user") int id_user;
    @Expose
    @SerializedName("foto") String foto;
   @Expose
   @SerializedName("nama_user") String nama_user;
   @Expose
   @SerializedName("username") String username;
   @Expose
   @SerializedName("password") String password;
   @Expose
   @SerializedName("level") String level;
   public UserModel(){}

    public UserModel(int id_user,String foto, String nama_user, String username, String password, String level) {
        this.id_user = id_user;
        this.foto = foto;
        this.nama_user = nama_user;
        this.username = username;
        this.password = password;
        this.level = level;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
