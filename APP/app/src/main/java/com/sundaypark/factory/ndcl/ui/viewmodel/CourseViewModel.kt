package com.sundaypark.factory.ndcl.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.sundaypark.factory.ndcl.db.RoomDB
import com.sundaypark.factory.ndcl.db.entitny.EntityCitys
import com.sundaypark.factory.ndcl.retrofit.RetrofitBuilder
import com.sundaypark.factory.ndcl.retrofit.pojo.NewCourses
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class CourseViewModel(private val db: RoomDB) : ViewModel() {
    val TAG = "[CourseViewModel]"
    var maincitys: LiveData<List<EntityCitys>> = db.daocitys().getMainCitys()
    var subCitys: MutableLiveData<List<EntityCitys>> = MutableLiveData()

    fun getSubCitys(select: Int) {
        Log.i(TAG, "getSubCitys  ${maincitys.value!!.get(select)}")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                subCitys.postValue(db.daocitys().getSubcitys(maincitys.value!![select].cityid))
            }
        }


    }
    fun getSearch(query : String , page : Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                RetrofitBuilder.getService.getSearch(query , page).enqueue(object : retrofit2.Callback<List<NewCourses>>{
                    override fun onFailure(call: Call<List<NewCourses>>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                    override fun onResponse(
                        call: Call<List<NewCourses>>,
                        response: Response<List<NewCourses>>
                    ) {
                        TODO("Not yet implemented")
                    }
                })
            }
        }
    }

    val SelectCourses = MutableLiveData<List<NewCourses>>()


    fun getCourses(select: EntityCitys, page: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                RetrofitBuilder.getService.getList(
                    String.format(
                        " roaaddress LIKE \'%%%s%%\' ",
                        select.cityname
                    ), page
                )
                    .enqueue(object : retrofit2.Callback<List<NewCourses>> {
                        override fun onFailure(call: Call<List<NewCourses>>, t: Throwable) {
                            Log.i(TAG, "onFailure" + t.message)
                        }

                        override fun onResponse(
                            call: Call<List<NewCourses>>,
                            response: Response<List<NewCourses>>
                        ) {
                            Log.i(TAG, "onResponse[" + response.isSuccessful)
                            if (response.isSuccessful) {
                                Log.i(TAG, "response.body()[" + response.body()?.size)
                                SelectCourses.postValue(response.body())
                            }
                        }
                    })
            }
        }
    }


    class CourseVMFactory(private val DB: RoomDB) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CourseViewModel(DB) as T
        }

    }
}

