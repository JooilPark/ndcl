package com.sundaypark.factory.ndcl.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.sundaypark.factory.ndcl.R
import com.sundaypark.factory.ndcl.db.entitny.EntityCitys
import com.sundaypark.factory.ndcl.utils.InjectorUtils
import com.sundaypark.factory.ndcl.viewmodel.CourseViewModel

class DashboardFragment : Fragment() {


    private val Viewmodel: CourseViewModel by viewModels {
        InjectorUtils.InJectCourseViewModelFactory(requireContext())
    }


    private lateinit var mNewCitys: List<EntityCitys>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val root = inflater.inflate(R.layout.fragment_dashboard, container, false).apply {
            Viewmodel.maincitys.observe(viewLifecycleOwner, Observer {
                mNewCitys = it
                for (city in mNewCitys) {
                    Log.i("test", city.cityname)
                }
            })
        }


        return root
    }

}