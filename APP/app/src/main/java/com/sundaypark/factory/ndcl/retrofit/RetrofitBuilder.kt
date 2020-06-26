package com.sundaypark.factory.ndcl.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {
//    val IP = "http://192.168.0.75:8080"
    val IP = "http://192.168.0.15:8080"

    var gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .create()

    private val retrofit = Retrofit.Builder().baseUrl(IP).addConverterFactory(GsonConverterFactory.create(gson)).build();
    var getService : RetrofitService  = retrofit.create(RetrofitService::class.java)

}