package com.sundaypark.factory.ndcl.utils

import android.content.Context
import android.content.SharedPreferences
import com.sundaypark.factory.ndcl.retrofit.pojo.Newversion

object SharedPreferencesManager {
    lateinit var sharedPre : SharedPreferences
    fun init(mContext : Context){
        sharedPre =  mContext.getSharedPreferences("NDCL" , Context.MODE_PRIVATE);
    }

    fun setVersionDate(Version : Newversion){
        val edit = sharedPre.edit()
        edit.putLong("VersionDate" , Version.date.time)
        edit.commit()
    }
    fun getVersionDate(): Long {
        return  sharedPre.getLong("VersionDate"  , 0)
    }




}