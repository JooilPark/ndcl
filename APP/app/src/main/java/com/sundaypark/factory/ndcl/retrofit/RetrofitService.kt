package com.sundaypark.factory.ndcl.retrofit

import com.sundaypark.factory.ndcl.retrofit.pojo.NewCitys
import com.sundaypark.factory.ndcl.retrofit.pojo.NewCourses
import com.sundaypark.factory.ndcl.retrofit.pojo.Newversion
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitService {
    @GET("/api/getCitys")
    fun getCitys(): Call<List<NewCitys>>

    @GET("/api/getVersion")
    fun getVersion(): Call<Newversion>

    @POST("/api/getList")
    fun getList(@Query("Query") Query : String ,@Query("page") page : Int): Call<List<NewCourses>>
    @POST("/api/getSearch")
    fun getSearch(@Query("Query") Query : String ,@Query("page") page : Int): Call<List<NewCourses>>

}