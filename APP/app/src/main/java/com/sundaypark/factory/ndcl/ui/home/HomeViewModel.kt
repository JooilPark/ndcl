package com.sundaypark.factory.ndcl.ui.home

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sundaypark.factory.ndcl.domain.CODE_FAILURE
import com.sundaypark.factory.ndcl.domain.CODE_SERVERERROR
import com.sundaypark.factory.ndcl.domain.NewError
import com.sundaypark.factory.ndcl.retrofit.RetrofitBuilder
import com.sundaypark.factory.ndcl.retrofit.pojo.NewCitys
import com.sundaypark.factory.ndcl.retrofit.pojo.Newversion
import retrofit2.Call
import retrofit2.Response

class HomeViewModel : AndroidViewModel(Application()) {

    private val _citys = MutableLiveData<List<NewCitys>>().apply {
        RetrofitBuilder.getService.getCitys().enqueue(object : retrofit2.Callback<List<NewCitys>> {
            override fun onFailure(call: Call<List<NewCitys>>, t: Throwable) {
                Log.i("test" , t.printStackTrace().toString())
                _error.value = NewError(CODE_FAILURE , "onFailure")
            }
            override fun onResponse(call: Call<List<NewCitys>>, response: Response<List<NewCitys>>) {
                if(response.isSuccessful){
                    value = response.body()
                    // 기존 정보 날림
                    // db 저장


                }
            }
        })
    }
    var Address  : LiveData<List<NewCitys>> = _citys




    private var _error  = MutableLiveData<NewError>()
    var onError : LiveData<NewError> = _error


    private val _version = MutableLiveData<Newversion>().apply {
        RetrofitBuilder.getService.getVersion().enqueue(object : retrofit2.Callback<Newversion> {
            override fun onFailure(call: Call<Newversion>, t: Throwable) {
                _error.value = NewError(CODE_FAILURE , "onFailure")
            }
            override fun onResponse(call: Call<Newversion>, response: Response<Newversion>) {
                if (response.isSuccessful) {
                    value = response.body()
                } else {
                    _error.value = NewError(CODE_SERVERERROR, "서버문제")
                }
            }
        })
    }
    var Version: LiveData<Newversion> = _version
}