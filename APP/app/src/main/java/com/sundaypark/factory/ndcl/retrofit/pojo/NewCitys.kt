package com.sundaypark.factory.ndcl.retrofit.pojo

import com.sundaypark.factory.ndcl.db.entitny.EntityCitys

//{"cityname":"이서혁신도시-전주시","id":2009,"cityid":36,"parentid":0}
data class NewCitys  (
    val cityname : String,
    val id : Int,
    val parentid : Int

)