package com.sundaypark.factory.ndcl.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sundaypark.factory.ndcl.db.entitny.EntityCitys


@Dao
interface Daocitys {
    @Delete
    fun Delete(citys: Array<out EntityCitys>)
    @Query("DELETE FROM city")
    fun DeleteAll()
    @Insert
    fun insertAll(vararg citys:EntityCitys)
    @Query("SELECT COUNT(cityname) FROM city" )
    fun getCitys() : Int
}