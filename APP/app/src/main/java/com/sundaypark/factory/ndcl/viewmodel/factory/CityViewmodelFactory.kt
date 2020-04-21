package com.sundaypark.factory.ndcl.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sundaypark.factory.ndcl.db.RoomDB
import com.sundaypark.factory.ndcl.viewmodel.HomeViewModel

class CityViewmodelFactory(private val mRoomDb: RoomDB) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(mRoomDb) as T
    }
}