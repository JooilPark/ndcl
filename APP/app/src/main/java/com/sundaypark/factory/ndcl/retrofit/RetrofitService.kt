package com.sundaypark.factory.ndcl.retrofit

import com.sundaypark.factory.ndcl.retrofit.pojo.NewCitys
import com.sundaypark.factory.ndcl.retrofit.pojo.Newversion
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET("/getCitys")
    fun getCitys() : Call<List<NewCitys>>

    @GET("/getVersion")
    fun getVersion() : Call<Newversion>





}