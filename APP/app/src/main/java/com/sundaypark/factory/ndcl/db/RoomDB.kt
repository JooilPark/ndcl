package com.sundaypark.factory.ndcl.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sundaypark.factory.ndcl.db.dao.Daocitys
import com.sundaypark.factory.ndcl.db.entitny.EntityCitys

@Database(entities = [EntityCitys::class], version = 1 , exportSchema = false )
abstract class RoomDB() : RoomDatabase() {
    abstract fun daocitys(): Daocitys

    companion object {
        @Volatile
        private var instance: RoomDB? = null
        fun getInstanc(mContext: Context): RoomDB {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(mContext, RoomDB::class.java, "ndcl.db").allowMainThreadQueries().build()
            }
        }
    }

}