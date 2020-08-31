package com.sundaypark.factory.ndcl.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sundaypark.factory.ndcl.R
import com.sundaypark.factory.ndcl.databinding.FragmentFragDetailInfoBinding
import com.sundaypark.factory.ndcl.retrofit.pojo.NewCourses

class FragDetailInfo : Fragment() {
    companion object{
        lateinit var detailInfo : NewCourses
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return DataBindingUtil.inflate<FragmentFragDetailInfoBinding>(inflater , R.layout.fragment_frag_detail_info , container , false).apply {
            info = detailInfo

        }.root
    }
}