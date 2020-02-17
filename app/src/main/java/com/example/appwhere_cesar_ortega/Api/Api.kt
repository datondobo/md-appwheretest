package com.example.appwhere_cesar_ortega.Api

import com.example.app_where.Models.ResponseLogin
import com.example.app_where.Models.ResponseSucursales
import com.example.appwhere_cesar_ortega.Models.ResponseAddSucursal
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface Api {

    //Metodo LOGIN
    @GET("api/session/login")
    fun respuestaLogin(
        @Query("email") email: String,
        @Query("password") password: String
    ):Call<ResponseLogin>



    //Metodo MAPA
    @GET("get-merchants")
    fun respuestaMapa():Call<ResponseSucursales>


    @Headers("{Content-Type: application/json}")
    //@Headers("Content-Type:application/json; charset=utf8")
    @FormUrlEncoded
    @POST("register-merchant")

    //fun addSucursal(@Body agregar: ModeloSucursales): Call<ResponseAddSucursal>
    //fun addSucursal(@Body body: JSONObject) : Call<ResponseAddSucursal>

    fun addSucursal(

        @Field("merchantName") merchantName: String,
        @Field("merchantAddress") merchantAddress: String,
        @Field("merchantTelephone") merchantTelephone: String,
        @Field("latitude") latitude: String,
        @Field("longitude") longitude: String

    ):Call<ResponseAddSucursal>

}