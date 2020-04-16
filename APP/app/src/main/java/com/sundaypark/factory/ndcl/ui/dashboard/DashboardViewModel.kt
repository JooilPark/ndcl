package com.sundaypark.factory.ndcl.ui.dashboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sundaypark.factory.ndcl.retrofit.RetrofitBuilder
import com.sundaypark.factory.ndcl.retrofit.pojo.NewCitys
import retrofit2.Call
import retrofit2.Response

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<List<NewCitys>>().apply {
        RetrofitBuilder.getService.getCitys().enqueue(object : retrofit2.Callback<List<NewCitys>> {
            override fun onFailure(call: Call<List<NewCitys>>, t: Throwable) {
               Log.i("test" , t.printStackTrace().toString())
            }
            override fun onResponse(call: Call<List<NewCitys>>, response: Response<List<NewCitys>>) {
                if(response.isSuccessful){
                    text.value = response.body()
                    Log.i("test" , "text size:"+text.value?.size)
                }
            }
        })

    }
    var text: MutableLiveData<List<NewCitys>> = _text
}