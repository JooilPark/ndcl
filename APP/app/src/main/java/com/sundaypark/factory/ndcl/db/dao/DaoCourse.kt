package com.sundaypark.factory.ndcl.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sundaypark.factory.ndcl.db.entitny.EntityCourses

@Dao
interface DaoCourse {
    @Insert
    fun insertAll(vararg citys: EntityCourses)

    @Query("SELECT * FROM course")
    fun getCourses(): LiveData<List<EntityCourses>>
}