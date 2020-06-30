package com.example.majelistalim.Session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionManager {
   public SharedPreferences sp;
   public SharedPreferences.Editor editor;

   public SessionManager(Context context){
       sp = PreferenceManager.getDefaultSharedPreferences(context);
   }

   public void setId(int id){
       sp.edit().putInt("id_user",id).apply();
   }
   public void setFoto(String foto){
       sp.edit().putString("foto",foto).apply();
   }
   public void setNama(String nama){
       sp.edit().putString("nama_user",nama).apply();
   }
   public void setUsername(String user){
       sp.edit().putString("username",user).apply();
   }
    public void setPassword(String password){
        sp.edit().putString("password",password).apply();
    }
   public void setLevel(String  level){
       sp.edit().putString("level", level).apply();
   }
    public int getId(){
       return sp.getInt("id_user",0);

   }
   public String getFoto(){
       return sp.getString("foto",null);
   }
   public String getNama(){
       return sp.getString("nama_user",null);

   }
   public String getUsername(){
       return sp.getString("username",null);
   }
    public String getPassword(){
        return sp.getString("password",null);
    }

    public String getLevel(){
       return sp.getString("level",null);
   }

   public void logout(){
       editor = sp.edit();
       editor.clear();
       editor.commit();
   }
}
