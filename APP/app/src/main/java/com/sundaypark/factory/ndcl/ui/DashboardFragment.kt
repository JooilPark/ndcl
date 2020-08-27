package com.sundaypark.factory.ndcl.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.activity.addCallback
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.MobileAds
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

/**
 * 해야할일
 * 검색 화면에서 백키 선택시 리스트로 돌아오기 (종료하면 안됨) : 완료
 * 목록 선택시 팝업으로  상세 정보 보여주기
 * 검색화면에서 키보드검색으로 검색 시작하기 : 완료
 * 검색완료하면 키보드 가리기 : 완료
 * 검색화면으로 진입시 키보드 띄우기 : 완료
 *
 *
 */
class DashboardFragment : Fragment() {
    val TAG: String = "[DashboardFragment]"

    companion object {
        var listType: ListType = ListType.COUSE
    }

    private val adSize: AdSize
        get() {
            val display = requireActivity().windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density

            var adWidthPixels = mDashboardBinding.adView.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                requireActivity(),
                adWidth
            )
        }
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

        val callBack = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (listType == ListType.COUSE) {
                Log.i(TAG, "COURSE BACK")
                requireActivity().moveTaskToBack(true)
                requireActivity().finish()
                android.os.Process.killProcess(android.os.Process.myPid())
            } else {
                mDashboardBinding.TopMenu.setTransition(R.id.end, R.id.start)
                mDashboardBinding.ChangeButton.isSelected = false
                Log.i(TAG, "onTransitionCompleted START")
                listType = ListType.COUSE
                Viewmodel.Coursereset()
                Viewmodel.CoursesGet(Viewmodel.lastCitys.value!!, 0)
            }
        }
        Viewmodel.selectCourses.observe(viewLifecycleOwner, Observer {
            Log.i(TAG, "SelectCourses ${it.size}")
            adapterCourses.submitList(it)
        })
        mDashboardBinding = DataBindingUtil.inflate<FragmentDashboardBinding>(
            inflater,
            R.layout.fragment_dashboard,
            container,
            false
        ).apply {
            // 검색 버턴
            editTextTextSearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Viewmodel.Coursereset()
                    dismissKeyboard(v.windowToken)
                    searchStart()
                    return@OnEditorActionListener true
                }
                false
            })
            buttonSearch.setOnClickListener {

                Viewmodel.Coursereset()
                searchStart()

            }
            // 목록 조회
            Courses.adapter = adapterCourses

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

                    Log.i(TAG, "SelectSubCity = ${Viewmodel.subCitys.value}")
                    Viewmodel.Coursereset()

                    Viewmodel.subCitys.value?.get(it)?.let { it1 -> Viewmodel.CoursesGet(it1, 0) }

                })
            Subcitys.adapter = adaptersubcitys
            Subcitys.onItemSelectedListener = adaptersubcitys

            subscriptCourses(_adapterCourse = adaptersubcitys)




            TopMenu.setTransitionListener(object : MotionLayout.TransitionListener {
                override fun onTransitionTrigger(
                    p0: MotionLayout?,
                    p1: Int,
                    p2: Boolean,
                    p3: Float
                ) {
                }

                override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
                override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}
                override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                    if (p1 == R.id.start) {
                        mDashboardBinding.ChangeButton.setImageResource(android.R.drawable.ic_menu_search)
                        if (ChangeButton.isSelected) {
                            ChangeButton.isSelected = false
                            Log.i(TAG, "onTransitionCompleted START")
                            listType = ListType.COUSE
                            Viewmodel.Coursereset()
                            Viewmodel.CoursesGet(Viewmodel.lastCitys.value!!, 0)
                        }
                    } else {
                        mDashboardBinding.ChangeButton.setImageResource(android.R.drawable.ic_menu_revert)
                        if (!ChangeButton.isSelected) {
                            Viewmodel.SearchWord = ""
                            editTextTextSearch.setText("")
                            Handler().postDelayed({
                                showKeyboard(mDashboardBinding.editTextTextSearch);

                            }, 500)
                            ChangeButton.isSelected = true
                            Log.i(TAG, "onTransitionCompleted END")
                            listType = ListType.SEARCH
                            Viewmodel.Coursereset()
                        }
                    }
                }
            })


            loading = Viewmodel.ShowLoading


        }

        mDashboardBinding.lifecycleOwner = this
        mDashboardBinding.data = Viewmodel
        initCoursesList()


        return mDashboardBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 광거
        MobileAds.initialize(requireActivity()) {}
        // mDashboardBinding.adView.adUnitId = "ca-app-pub-9999663550966576~7567990567"
        // mDashboardBinding.adView.adSize = adSize
        mDashboardBinding.adView.loadAd(AdRequest.Builder().build())
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

                Log.i(TAG, "subCitys = ${it}")
                Viewmodel.Coursereset()
                Viewmodel._lastcitys.value = it[0]
                _adapterCourse.clear()
                _adapterCourse.addAll(it)
                _adapterCourse.notifyDataSetChanged()

                mDashboardBinding.Subcitys.setSelection(0)


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
                    Log.i(TAG, "onScrolled  ${listType}  ")
                    Viewmodel.lastCitys.value?.let {
                        if (listType == ListType.COUSE) {
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
            if (!it.isNullOrEmpty()) {
                Viewmodel.getSearch(it, Viewmodel.listPageCount)
            }

        }
    }
    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }
    private fun showKeyboard(windowToken: EditText) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.toggleSoftInputFromWindow(windowToken.windowToken, InputMethodManager.SHOW_FORCED, 0)
        windowToken.requestFocus()
    }
}