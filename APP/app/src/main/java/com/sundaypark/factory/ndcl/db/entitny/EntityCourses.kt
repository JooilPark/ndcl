package com.sundaypark.factory.ndcl.db.entitny

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "course")
data class EntityCourses(
    @PrimaryKey(autoGenerate = true) val index: Int?,
    @ColumnInfo val coursename: String?,
    @ColumnInfo val courseteachername: String?,

    @ColumnInfo val trainingstartdatetime: Long?,
    @ColumnInfo val trainingEnddatetime: Long?,

    //@ColumnInfo val trainingstarttime: Date?,
    //@ColumnInfo val trainingendtime: Date?,

    @ColumnInfo val content: String?,
    @ColumnInfo val edutarget: String?,
    @ColumnInfo val edumthod: String?,
    @ColumnInfo val operatingday: String?,
    @ColumnInfo val trainingplace: String?,
    @ColumnInfo val maxstudents: String?,
    @ColumnInfo val fee: Int?,
    @ColumnInfo val roaaddress: String?,
    @ColumnInfo val opernumber: String?,
    @ColumnInfo val receptionstart: Long?,
    @ColumnInfo val receptionend: Long?,
    @ColumnInfo val receptionmethod: String?,
    @ColumnInfo val receptionselection: String?,
    @ColumnInfo val homepage: String?,
    @ColumnInfo val developmenttraining: Boolean?,
    @ColumnInfo val bankingevaluation: Boolean?,
    @ColumnInfo val accountevaluation: Boolean?,
    @ColumnInfo val databasedate: Long?,
    @ColumnInfo val providerpode: String?,
    @ColumnInfo val providername: String?
)