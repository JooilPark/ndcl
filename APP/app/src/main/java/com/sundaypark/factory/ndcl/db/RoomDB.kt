package com.sundaypark.factory.ndcl.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sundaypark.factory.ndcl.db.dao.DaoCourse
import com.sundaypark.factory.ndcl.db.dao.Daocitys
import com.sundaypark.factory.ndcl.db.entitny.EntityCitys
import com.sundaypark.factory.ndcl.db.entitny.EntityCourses

@Database(entities = [EntityCitys::class, EntityCourses::class], version = 2, exportSchema = false)
abstract class RoomDB : RoomDatabase() {
    abstract fun daocitys(): Daocitys
    abstract fun daocourses(): DaoCourse

    companion object {
        private val mig1to2  = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {

            }

        }

        @Volatile
        private var instance: RoomDB? = null
        fun getInstanc(mContext: Context): RoomDB {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(mContext, RoomDB::class.java, "ndcl.db").addMigrations(mig1to2)
                    .build()
            }
        }
    }

}