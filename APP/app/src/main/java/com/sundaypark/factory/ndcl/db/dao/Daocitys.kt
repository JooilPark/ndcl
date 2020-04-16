package com.sundaypark.factory.ndcl.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.sundaypark.factory.ndcl.db.entitny.EntityCitys


@Dao
interface Daocitys {
    @Delete
    fun DeleteAll(citys : EntityCitys)
    @Insert
    fun insertAll(vararg citys:EntityCitys)
}