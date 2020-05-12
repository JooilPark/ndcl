package com.sundaypark.factory.ndcl.domain

class NewError (val code : Int , val message : String){
}
const val CODE_FAILURE : Int = 0;

const val CODE_SERVERERROR : Int = 1;

const val CODE_DB_FAIL : Int = 2;
