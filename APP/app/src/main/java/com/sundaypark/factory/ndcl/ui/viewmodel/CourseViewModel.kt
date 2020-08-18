package com.sundaypark.factory.ndcl.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.sundaypark.factory.ndcl.db.RoomDB
import com.sundaypark.factory.ndcl.db.entitny.EntityCitys
import com.sundaypark.factory.ndcl.retrofit.RetrofitBuilder
import com.sundaypark.factory.ndcl.retrofit.pojo.NewCourses
import com.sundaypark.factory.ndcl.vo.ListType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class CourseViewModel(private val db: RoomDB) : ViewModel() {
    val TAG = "[CourseViewModel]"
    var maincitys: LiveData<List<EntityCitys>> = db.daocitys().getMainCitys()
    var subCitys: MutableLiveData<List<EntityCitys>> = MutableLiveData()
    val selectCourses = MutableLiveData<MutableList<NewCourses>>(mutableListOf())
    fun getSubCitys(select: Int) {
        Log.i(TAG, "getSubCitys  ${maincitys.value!!.get(select)}")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                subCitys.postValue(db.daocitys().getSubcitys(maincitys.value!![select].cityid))
            }
        }
    }

    val ShowLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var SearchWord = ""
    fun getSearch(query: String?, page: Int) {
        GlobalScope.launch {
            query?.let {
                SearchWord = it
                Log.i(TAG, "Load ${it} + ${page}")
                if (!ShowLoading.value!!) {
                    ShowLoading.postValue(true)
                    RetrofitBuilder.getService.getSearch(query, page)
                        .enqueue(object : retrofit2.Callback<List<NewCourses>> {
                            override fun onFailure(call: Call<List<NewCourses>>, t: Throwable) {
                                ShowLoading.postValue(false)
                            }

                            override fun onResponse(
                                call: Call<List<NewCourses>>,
                                response: Response<List<NewCourses>>
                            ) {
                                ShowLoading.postValue(false)
                                if (response.isSuccessful) {
                                    Log.i(TAG, "response.body()[" + response.body()?.size)
                                    if (response.body()?.size != 0) {
                                        listPageCount++
                                        response.body()?.let {
                                            selectCourses.value!!.addAll(it)
                                        }

                                    }

                                }


                            }
                        })

                } else {
                    Log.i(TAG, "Loading")
                }

            }
        }


    }


    var listType: ListType = ListType.COUSE
    var listPageCount = 0
    var SearchPageCount = 0
    val _lastcitys: MutableLiveData<EntityCitys> = MutableLiveData()
    val lastCitys: LiveData<EntityCitys> = _lastcitys
    val Coursereset = {
        listPageCount = 0
        selectCourses.value = mutableListOf()

    }

    fun CoursesGet(select: EntityCitys, page: Int) {
        _lastcitys.value = select
        if (!ShowLoading.value!!) {
            Log.i(TAG, "Load ${select} + ${page}")
            ShowLoading.value = true
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
                                ShowLoading.postValue(false)
                            }

                            override fun onResponse(
                                call: Call<List<NewCourses>>,
                                response: Response<List<NewCourses>>
                            ) {
                                ShowLoading.postValue(false)
                                Log.i(TAG, "onResponse[" + response.isSuccessful)
                                if (response.isSuccessful) {
                                    Log.i(TAG, "response.body()[" + response.body()?.size)
                                    if (response.body()?.size != 0) {
                                        listPageCount++
                                        response.body()?.let {
                                            selectCourses.value!!.addAll(it)
                                        }

                                    }

                                }
                            }
                        })
                }
            }

        } else {
            Log.i(TAG, "Loading")
        }

    }


    class CourseVMFactory(private val DB: RoomDB) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CourseViewModel(DB) as T
        }

    }
}

