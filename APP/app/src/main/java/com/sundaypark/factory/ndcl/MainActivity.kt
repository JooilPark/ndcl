package com.sundaypark.factory.ndcl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sundaypark.factory.ndcl.databinding.ActivityMainBinding
import com.sundaypark.factory.ndcl.utils.SharedPreferencesManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferencesManager.init(this)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

    }


}
