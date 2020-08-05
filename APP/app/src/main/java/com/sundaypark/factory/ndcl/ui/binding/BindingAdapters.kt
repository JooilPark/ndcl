package com.sundaypark.factory.ndcl.ui.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData



object BindingAdapters {
    @JvmStatic
    @BindingAdapter("viewShoHide")
    fun showHide(view : View, show : MutableLiveData<Boolean>){
        view.visibility = if(show.value!!)View.VISIBLE else View.GONE
    }

}