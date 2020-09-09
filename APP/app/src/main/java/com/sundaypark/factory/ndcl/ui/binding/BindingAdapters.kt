package com.sundaypark.factory.ndcl.ui.binding

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData


object BindingAdapters {
    @JvmStatic
    @BindingAdapter("viewShoHide")
    fun showHide(view: View, show: MutableLiveData<Boolean>) {
        view.visibility = if (show.value!!) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("toTextYesNo")
    fun toTextYesNo(view: TextView, YesNo: Int) {
        view.text = if (YesNo == 1) "Y" else "N"
    }
    @JvmStatic
    @BindingAdapter("toPayString")
    fun toPayString(view: TextView, fee: Int) {
        view.text = "${fee} 원"
    }
    @JvmStatic
    @BindingAdapter("app:StartDay","app:EndDay")
    fun toStartEndDay(view: TextView, start: String? , end : String?) {

        if(start.isNullOrBlank().and(end.isNullOrBlank())){
            view.text = "기간 정보 없음"
            return
        }
        if(!start.isNullOrBlank()){
            view.text = " ${start}  ~  ?"
            return
        }
        if(!end.isNullOrBlank()){
            view.text = " ?  ~ ${end}"
            return
        }





    }



}