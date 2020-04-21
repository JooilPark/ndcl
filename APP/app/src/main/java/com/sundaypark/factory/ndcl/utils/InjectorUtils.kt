package com.sundaypark.factory.ndcl.utils

import android.content.Context
import com.sundaypark.factory.ndcl.db.RoomDB
import com.sundaypark.factory.ndcl.viewmodel.factory.CityViewmodelFactory

object InjectorUtils {
    fun provideAddressViewModelFactory(context: Context): CityViewmodelFactory {
        return CityViewmodelFactory(RoomDB.getInstanc(context))
    }
}