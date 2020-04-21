package com.sundaypark.factory.ndcl.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sundaypark.factory.ndcl.R
import com.sundaypark.factory.ndcl.retrofit.pojo.NewCitys
import com.sundaypark.factory.ndcl.viewmodel.DashboardViewModel

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var mNewCitys : List<NewCitys>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            mNewCitys = it
            for(city in mNewCitys){
                Log.i("test" , city.cityname)
            }
        })

        return root
    }

}