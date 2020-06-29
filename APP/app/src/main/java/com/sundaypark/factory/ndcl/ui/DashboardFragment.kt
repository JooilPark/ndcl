package com.sundaypark.factory.ndcl.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.sundaypark.factory.ndcl.R
import com.sundaypark.factory.ndcl.databinding.FragmentDashboardBinding
import com.sundaypark.factory.ndcl.db.RoomDB
import com.sundaypark.factory.ndcl.db.entitny.EntityCitys
import com.sundaypark.factory.ndcl.ui.adapters.AdapterSpinnerCitys
import com.sundaypark.factory.ndcl.ui.adapters.AdapterSpinnerCourses
import com.sundaypark.factory.ndcl.ui.viewmodel.CourseViewModel

class DashboardFragment : Fragment() {
    val TAG: String = "[DashboardFragment]"

    lateinit var mDashboardBinding: FragmentDashboardBinding
    private val Viewmodel: CourseViewModel by viewModels {
        CourseViewModel.CourseVMFactory(RoomDB.getInstanc(requireContext()))
    }
    private lateinit var mNewCitys: List<EntityCitys>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDashboardBinding = DataBindingUtil.inflate<FragmentDashboardBinding>(
            inflater,
            R.layout.fragment_dashboard,
            container,
            false
        ).apply {
            context ?: mDashboardBinding.root
            // 메인 도시
            val adapterMainCitys =
                AdapterSpinnerCitys(requireContext(), R.layout.item_spinner_citys)
            MainCitys.adapter = adapterMainCitys
            MainCitys.onItemSelectedListener = adapterMainCitys
            SubScriptMainCity(adapterMainCitys)
            adapterMainCitys.SelectItem.observe(viewLifecycleOwner, Observer {
                Log.i(TAG, "SELECT ITEM " + it)
                Viewmodel.getCourses(it)
            })

            //코스 선택
            val adapterCourses = AdapterSpinnerCourses(requireContext(), R.layout.item_spinner_course)
            Courses.adapter = adapterCourses
            Courses.onItemSelectedListener = adapterCourses
            subscriptCourses(_adapterCourse = adapterCourses)

        }
        mDashboardBinding.lifecycleOwner = viewLifecycleOwner
        mDashboardBinding.data = Viewmodel
        return mDashboardBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val RDB = RoomDB.getInstanc(requireContext())


    }

    private fun SubScriptMainCity(_adapterMainCitys: AdapterSpinnerCitys) {
        Viewmodel.maincitys.observe(viewLifecycleOwner, Observer {
            mNewCitys = it
            for (city in mNewCitys) {
                Log.i("test", city.cityname)
            }
            _adapterMainCitys.clear()
            _adapterMainCitys.addAll(mNewCitys)
            _adapterMainCitys.notifyDataSetChanged()
        })
    }
    private fun subscriptCourses(_adapterCourse : AdapterSpinnerCourses){
        _adapterCourse.SelectItem.observe(viewLifecycleOwner , Observer {

        })
        Viewmodel.SelectCourses.observe(viewLifecycleOwner , Observer {
            Log.i("test", "subscriptCourses + " + it.size)
            _adapterCourse.clear();
            _adapterCourse.addAll(it)
            _adapterCourse.notifyDataSetChanged()
        })
    }


}