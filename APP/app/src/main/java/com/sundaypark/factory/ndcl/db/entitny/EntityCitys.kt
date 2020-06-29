package com.sundaypark.factory.ndcl.db.entitny

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "city")
data class EntityCitys (
    @PrimaryKey(autoGenerate = true) val index : Int?,
    @ColumnInfo val cityid : Int,
    @ColumnInfo val cityname : String,
    @ColumnInfo val parentid : Int
)