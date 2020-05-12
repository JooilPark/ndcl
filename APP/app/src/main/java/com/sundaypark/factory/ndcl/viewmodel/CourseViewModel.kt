package com.sundaypark.factory.ndcl.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sundaypark.factory.ndcl.db.RoomDB
import com.sundaypark.factory.ndcl.db.entitny.EntityCitys
import com.sundaypark.factory.ndcl.db.entitny.EntityCourse
import com.sundaypark.factory.ndcl.retrofit.pojo.NewCitys

class CourseViewModel(private val db : RoomDB) : ViewModel() {
    val TAG = "[CourseViewModel]"
    private val _maincitys = MutableLiveData<List<EntityCitys>>().apply {
        maincitys = db.daocitys().getMainCitys()
        Log.i(TAG ,"Citys : " + maincitys.value?.size)
    }
    var maincitys: LiveData<List<EntityCitys>> = _maincitys







    private val _selectmaincity = MutableLiveData<EntityCitys>().apply {
            Log.i(TAG , "SelectCity:"+ value?.cityname)
    }
    var selectMainCity : MutableLiveData<EntityCitys> = _selectmaincity;


}