package com.sundaypark.factory.ndcl.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sundaypark.factory.ndcl.db.entitny.EntityCitys

@Database(entities = [EntityCitys::class] , version = 1)
abstract class  RoomDB(mContext : Context):RoomDatabase() {
    lateinit var DB : RoomDB
    fun getInstance(mContext : Context){
        DB  = Room.databaseBuilder(mContext , RoomDB::class.java , "NDCL.db").build()
    }



}