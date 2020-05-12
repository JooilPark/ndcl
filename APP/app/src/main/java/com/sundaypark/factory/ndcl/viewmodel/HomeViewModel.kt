package com.sundaypark.factory.ndcl.viewmodel

import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sundaypark.factory.ndcl.db.RoomDB
import com.sundaypark.factory.ndcl.db.entitny.EntityCitys
import com.sundaypark.factory.ndcl.domain.CODE_FAILURE
import com.sundaypark.factory.ndcl.domain.CODE_SERVERERROR
import com.sundaypark.factory.ndcl.domain.NewError
import com.sundaypark.factory.ndcl.retrofit.RetrofitBuilder
import com.sundaypark.factory.ndcl.retrofit.pojo.NewCitys
import com.sundaypark.factory.ndcl.retrofit.pojo.Newversion
import retrofit2.Call
import retrofit2.Response

class HomeViewModel (private val mRoom : RoomDB) : ViewModel() {

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
                    mRoom.daocitys().DeleteAll()
                    // db 저장
                    if(response.body()?.size == 0){
                        _error.value = NewError(CODE_FAILURE , "DB FAIL")
                    }else{
                        response.body()?.forEach { newCitys: NewCitys -> mRoom.daocitys().insertAll(EntityCitys(null , newCitys.id , newCitys.cityname , newCitys.parentid)) }
                    }
                }else{
                    _error.value = NewError(CODE_FAILURE , "onFailure")
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