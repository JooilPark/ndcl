package com.sundaypark.factory.ndcl.db.dao

import androidx.lifecycle.LiveData
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
    @Query("SELECT * FROM city" )
    fun getCitys() : LiveData<List<EntityCitys>>

    @Query("SELECT * FROM city WHERE parentid = 0 ORDER BY cityid ASC" )
    fun getMainCitys() : LiveData<List<EntityCitys>>

    @Query("SELECT * FROM city WHERE parentid = :parentid" )
    fun getSubcitys(parentid : Int) : List<EntityCitys>
}