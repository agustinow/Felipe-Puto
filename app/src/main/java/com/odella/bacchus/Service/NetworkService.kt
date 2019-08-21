package com.odella.bacchus.Service

import android.net.Network
import com.google.gson.GsonBuilder
import com.odella.bacchus.Constants.BASE_URL
import com.odella.bacchus.Model.UserFeed
import com.odella.bacchus.Model.WineFeed
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface NetworkService {
    var token: String


    //LOGIN
    @Headers("Content-Type: application/json")
    @POST("login/request")
        fun login(@Body request: RequestBody): Call<Any>

    //GETS
    @GET("wines")
    fun getWines(@Header("Authorization") token: String): Call<List<WineFeed>>
    @GET("wines/{id}")
    fun getWine(@Path("id") path: String, @Header("Authorization") token: String): Call<WineFeed>

    @GET("wines/{id}")
    fun getWine(@Path("id") path: Int, @Header("Authorization") token: String): Call<WineFeed>

    @GET("users")
    fun getUsers(@Header("Authorization") token: String): Call<List<UserFeed>>
    @GET("users{id}")
    fun getUser(@Path("id") path: String, @Header("Authorization") token: String): Call<UserFeed>


    companion object Factory{

        var gson = GsonBuilder().setLenient().create()
        fun create(): NetworkService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(UnsafeOkHttpClient.getUnsafeOkHttpClient())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(NetworkService::class.java)
        }
    }
}