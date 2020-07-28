package com.sundaypark.factory.ndcl.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.sundaypark.factory.ndcl.R
import com.sundaypark.factory.ndcl.databinding.FragmentDashboardBinding
import com.sundaypark.factory.ndcl.db.RoomDB
import com.sundaypark.factory.ndcl.db.entitny.EntityCitys
import com.sundaypark.factory.ndcl.ui.adapters.AdapterSpinnerCitys
import com.sundaypark.factory.ndcl.ui.adapters.AdapterSpinnersubCitys
import com.sundaypark.factory.ndcl.ui.adapters.adapterCoursesList
import com.sundaypark.factory.ndcl.ui.viewmodel.CourseViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

class DashboardFragment : Fragment() {
    val TAG: String = "[DashboardFragment]"

    lateinit var mDashboardBinding: FragmentDashboardBinding
    val adapterCourses = adapterCoursesList()


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
            val adapterMainCitys = AdapterSpinnerCitys(requireContext(), R.layout.item_spinner_citys)
            MainCitys.adapter = adapterMainCitys
            MainCitys.onItemSelectedListener = adapterMainCitys
            SubScriptMainCity(adapterMainCitys)
            adapterMainCitys.SelectItem.observe(viewLifecycleOwner, Observer {
                Log.i(TAG, "SELECT ITEM " + it)
                Viewmodel.getSubCitys(it)
            })


            //서브 시티
            val adaptersubcitys = AdapterSpinnersubCitys(requireContext(), R.layout.item_spinner_citys)
            Subcitys.adapter = adaptersubcitys
            Subcitys.onItemSelectedListener = adaptersubcitys

            subscriptCourses(_adapterCourse = adaptersubcitys)

            // 목록 조회
            Courses.adapter = adapterCourses
            Viewmodel.SelectCourses.observe(viewLifecycleOwner, Observer {
                adapterCourses.addClearAll(it)
                adapterCourses.notifyDataSetChanged()
            })
            TopMenu.setTransitionListener(object : MotionLayout.TransitionListener {
                override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
                override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
                override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}
                override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                       Log.i(TAG , "onTransitionCompleted ${p1}");
                        if(p1 == R.id.start){
                            mDashboardBinding.ChangeLayout.setImageResource(android.R.drawable.ic_menu_search)
                        }else{
                            mDashboardBinding.ChangeLayout.setImageResource(android.R.drawable.ic_menu_revert)
                        }
                }
            })
            buttonSearch.setOnClickListener {

            }
        }
        mDashboardBinding.lifecycleOwner = viewLifecycleOwner
        mDashboardBinding.data = Viewmodel
        return mDashboardBinding.root
    }

    private fun SubScriptMainCity(_adapterMainCitys: AdapterSpinnerCitys) {
        Viewmodel.maincitys.observe(viewLifecycleOwner, Observer {
            mNewCitys = it
            Log.i("test", "${it}")

            _adapterMainCitys.clear()
            _adapterMainCitys.addAll(mNewCitys)
            _adapterMainCitys.notifyDataSetChanged()
            if (!it.isEmpty()) {
                _adapterMainCitys.SelectItem.value = 0
            }

        })
    }

    private fun subscriptCourses(_adapterCourse: AdapterSpinnersubCitys) {


        Viewmodel.subCitys.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                Log.i(TAG, "subCitys = null")
                _adapterCourse.clear()
                _adapterCourse.notifyDataSetChanged()

            } else {

                Log.i(TAG, "subCitys = ${it.size}")
                _adapterCourse.clear()
                _adapterCourse.addAll(it)

                _adapterCourse.notifyDataSetChanged()
                Viewmodel.maincitys.value?.get(0)?.let { it1 ->
                    Viewmodel.getCourses(it1, 0)
                    //mDashboardBinding.Subcitys.setSelection(0)
                }
            }
        })
        _adapterCourse.SelectItem.observe(viewLifecycleOwner, Observer {
            Log.i(TAG, "SelectSubCity = $it")
            Viewmodel.subCitys.value?.get(it)?.let { it1 -> Viewmodel.getCourses(it1, 0) }
        })
    }


}