package com.example.majelistalim.Api;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.example.majelistalim.Model.JadwalModel;
import com.example.majelistalim.Model.ResponseModel;
import com.example.majelistalim.Model.ResponseModelJadwal;
import com.example.majelistalim.Model.ResponseModelPesan;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("insert_user.php")
    Call<ResponseModel> sendRegis(
                                    @Field("nama_user") String nama_user,
                                    @Field("username") String username,
                                    @Field("password") String password
                                );
    @FormUrlEncoded
    @POST("update_user.php")
    Call<ResponseModel> ubahData(
            @Field("id_user") String id_user,
            @Field("fotolama") String fotolama,
            @Field("nama_user") String nama_user,
            @Field("username") String username
    );
    @Multipart
    @POST("update_user.php")
    Call<ResponseModel> ubahFoto(
            @Part("id_user") RequestBody id_user,
            @Part MultipartBody.Part foto,
            @Part("fotolama") RequestBody fotolama,
            @Part("nama_user") RequestBody nama_user,
            @Part("username") RequestBody username
    );
    @FormUrlEncoded
    @POST("update_password.php")
    Call<ResponseModel> ubahPass(
            @Field("id_user") String id_user,
            @Field("password") String password

    );

    @FormUrlEncoded
    @POST("insert_jamaah.php")
    Call<ResponseModel> sendRegisJamaah(
            @Field("nama_user") String nama_user,
            @Field("username") String username,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseModel> login(@Field("username") String username, @Field("password") String password);

    @GET("view_jadwal.php")
    Call<ResponseModelJadwal> getJadwal();

    @FormUrlEncoded
    @POST("view_jadwalbynama.php")
    Call<ResponseModelJadwal> getJadwalByNama(@Field("nama") String nama);

    @FormUrlEncoded
    @POST("view_detail.php")
    Call<ResponseModelJadwal> getDetail(@Field("id_jadwal") String id_jadwal);

    @FormUrlEncoded
    @POST("view_jadwalbyid.php")
    Call<ResponseModelJadwal> getJadwalById(@Field("user_id") int user_id);

    @FormUrlEncoded
    @POST("view_jadwalbyalamat.php")
    Call<ResponseModelJadwal> getJadwalByAlamat(@Field("lokasi") String lokasi);

    @FormUrlEncoded
    @POST("view_pesan.php")
    Call<ResponseModelPesan> lihatPesan(@Field("majelis_id") String majelis_id);

    @FormUrlEncoded
    @POST("lihat_forum.php")
    Call<ResponseModelPesan> lihatRiwayat(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("insert_komentar.php")
    Call<ResponseModelPesan> sendPesan(@Field("majelis_id") String majelis_id,
                                       @Field("userid") String userid,
                                       @Field("pesan") String pesan);
    @FormUrlEncoded
    @POST("delete.php")
    Call<ResponseModelJadwal> hapus(@Field("id_jadwal") String id_jadwal);

    @FormUrlEncoded
    @POST("delete_pesan.php")
    Call<ResponseModelPesan> hapusPesan(@Field("id_pesan") String id_pesan);

    @Multipart
    @POST("insert_jadwal.php")
    Call<ResponseModelJadwal> sendJadwal(
            @Part("user_id") int user_id,
            @Part MultipartBody.Part foto,
            @Part("nama_majelis") RequestBody nama_majelis,
            @Part("tanggal") RequestBody tanggal,
            @Part("jam_mulai") RequestBody jam_mulai,
            @Part("nama_alamat") RequestBody nama_alamat,
            @Part("lokasi") RequestBody lokasi,
            @Part("keterangan") RequestBody keterangan,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude

            );
    @Multipart
    @POST("update.php")
    Call<ResponseModelJadwal> updateJadwalFoto(
            @Part("id_jadwal") RequestBody id_jadwal,
            @Part MultipartBody.Part foto,
            @Part("nama_majelis") RequestBody nama_majelis,
            @Part("tanggal") RequestBody tanggal,
            @Part("jam_mulai") RequestBody jam_mulai,
            @Part("nama_alamat") RequestBody nama_alamat,
            @Part("lokasi") RequestBody lokasi,
            @Part("keterangan") RequestBody keterangan,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude,
            @Part("fotolama") RequestBody fotolama
    );
    @FormUrlEncoded
    @POST("update.php")
    Call<ResponseModelJadwal> updateJadwal(@Field("id_jadwal") String id_jadwal,
                                           @Field("fotolama") String fotolama,
                                           @Field("nama_majelis") String nama_majelis,
                                           @Field("tanggal") String tanggal,
                                           @Field("jam_mulai") String jam_mulai,
                                           @Field("nama_alamat") String nama_alamat,
                                           @Field("lokasi") String lokasi,
                                           @Field("keterangan") String keterangan,
                                           @Field("latitude") String latitude,
                                           @Field("longitude") String longitude

                                           );
}
