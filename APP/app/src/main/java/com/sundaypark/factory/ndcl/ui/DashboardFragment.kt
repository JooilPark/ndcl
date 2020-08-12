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
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sundaypark.factory.ndcl.R
import com.sundaypark.factory.ndcl.databinding.FragmentDashboardBinding
import com.sundaypark.factory.ndcl.db.RoomDB
import com.sundaypark.factory.ndcl.db.entitny.EntityCitys
import com.sundaypark.factory.ndcl.retrofit.pojo.NewCourses
import com.sundaypark.factory.ndcl.ui.adapters.AdapterSpinnerCitys
import com.sundaypark.factory.ndcl.ui.adapters.AdapterSpinnersubCitys
import com.sundaypark.factory.ndcl.ui.adapters.adapterCoursesList
import com.sundaypark.factory.ndcl.ui.viewmodel.CourseViewModel
import com.sundaypark.factory.ndcl.vo.ListType

class DashboardFragment : Fragment() {
    val TAG: String = "[DashboardFragment]"

    lateinit var mDashboardBinding: FragmentDashboardBinding
    var adapterCourses = adapterCoursesList(config = object : DiffUtil.ItemCallback<NewCourses>() {
        override fun areContentsTheSame(oldItem: NewCourses, newItem: NewCourses): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: NewCourses, newItem: NewCourses): Boolean {
            return oldItem == newItem
        }

    }, itemClickCallback = {})

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
            val adapterMainCitys = AdapterSpinnerCitys(
                requireContext(),
                R.layout.item_spinner_citys,
                OnItemClickListiner = {
                    Viewmodel.Coursereset()

                    Viewmodel.getSubCitys(it)
                })
            MainCitys.adapter = adapterMainCitys
            MainCitys.onItemSelectedListener = adapterMainCitys
            SubScriptMainCity(adapterMainCitys)


            //서브 시티
            val adaptersubcitys = AdapterSpinnersubCitys(
                requireContext(),
                R.layout.item_spinner_citys,
                OnItemClickListiner = {
                    Log.i(TAG, "SelectSubCity = $it")
                    Viewmodel.Coursereset()

                    Viewmodel.subCitys.value?.get(it)?.let { it1 -> Viewmodel.CoursesGet(it1, 0) }
                })
            Subcitys.adapter = adaptersubcitys
            Subcitys.onItemSelectedListener = adaptersubcitys

            subscriptCourses(_adapterCourse = adaptersubcitys)

            // 목록 조회
            Courses.adapter = adapterCourses

            Viewmodel.SelectCourses.observe(viewLifecycleOwner, Observer {
                adapterCourses.submitList(it)

            })
            TopMenu.setTransitionListener(object : MotionLayout.TransitionListener {
                override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
                override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
                override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}
                override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {

                    if (p1 == R.id.start) {
                        Log.i(TAG, "onTransitionCompleted START")
                        mDashboardBinding.ChangeLayout.setImageResource(android.R.drawable.ic_menu_search)
                        Viewmodel.listType = ListType.COUSE
                    } else {
                        Log.i(TAG, "onTransitionCompleted END")
                        mDashboardBinding.ChangeLayout.setImageResource(android.R.drawable.ic_menu_revert)
                        Viewmodel.listType = ListType.SEARCH
                    }
                }
            })
            buttonSearch.setOnClickListener {
                Viewmodel.Coursereset()
                searchStart()

            }

            loading = Viewmodel.ShowLoading


        }
        mDashboardBinding.lifecycleOwner = this
        mDashboardBinding.data = Viewmodel
        initCoursesList()


        return mDashboardBinding.root
    }

    private fun SubScriptMainCity(_adapterMainCitys: AdapterSpinnerCitys) {
        Viewmodel.maincitys.observe(viewLifecycleOwner, Observer {
            mNewCitys = it
            Log.i("test", "${it}")
            _adapterMainCitys.clear()
            _adapterMainCitys.addAll(mNewCitys)
            _adapterMainCitys.notifyDataSetChanged()
        })
    }

    private fun subscriptCourses(_adapterCourse: AdapterSpinnersubCitys) {
        Viewmodel.Coursereset()
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
                Viewmodel.CoursesGet(it[0], 0)
            }
        })


    }

    private fun initCoursesList() {
        mDashboardBinding.Courses.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == adapterCourses.itemCount - 1) {
                    // 로드 시작 리프래시 될때도  로드 된다 .
                    Log.i(TAG, "onScrolled  ${Viewmodel.listType}  ")
                    Viewmodel.lastCitys.value?.let {
                        if (Viewmodel.listType == ListType.COUSE) {
                            Viewmodel.CoursesGet(it, Viewmodel.listPageCount)
                        } else {
                            searchStart()
                        }
                    }


                }
            }
        })
    }

    fun searchStart() {
        mDashboardBinding.editTextTextSearch.text?.toString().let {

            Viewmodel.getSearch(it, Viewmodel.listPageCount)
        }
    }


}