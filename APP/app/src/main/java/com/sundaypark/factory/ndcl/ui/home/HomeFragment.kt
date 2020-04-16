package com.sundaypark.factory.ndcl.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sundaypark.factory.ndcl.R
import com.sundaypark.factory.ndcl.domain.CODE_FAILURE
import com.sundaypark.factory.ndcl.domain.CODE_SERVERERROR
import com.sundaypark.factory.ndcl.utils.SharedPreferencesManager

// 위치 정보 확인
// 버전확인
// 주소 받기
//
class HomeFragment : Fragment() {
    private val TAG: String = "HomeFragment"
    private lateinit var homeViewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)

        homeViewModel.Version.observe(viewLifecycleOwner, Observer {
            if (SharedPreferencesManager.getVersionDate() != it.date.time) {
                // 지역 업데이트 필요
                Log.i(TAG, "주소 업데이트 필요 ")
                val Version = it
                homeViewModel.Address.observe(viewLifecycleOwner, Observer {
                    Log.i(TAG, "주소 업데이트 완료")
                    SharedPreferencesManager.setVersionDate(Version)// 저장
                    gotoListPage()
                })
            } else {

                gotoListPage()

            }
        })
        homeViewModel.onError.observe(viewLifecycleOwner, Observer {
            when (it.code) {
                CODE_FAILURE -> {
                    Log.i(TAG, "서버가 동작하는가 ? ")
                }
                CODE_SERVERERROR -> {
                    Log.i(TAG, "서버에서 데이터가 안왔어 ?! ")

                }
            }
        })
        return root
    }
    fun gotoListPage(){
        Log.i(TAG, "화면으로 이동 ")
    }
}