package com.sundaypark.factory.ndcl.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.sundaypark.factory.ndcl.R
import com.sundaypark.factory.ndcl.databinding.FragmentHomeBinding
import com.sundaypark.factory.ndcl.domain.CODE_DB_FAIL
import com.sundaypark.factory.ndcl.domain.CODE_FAILURE
import com.sundaypark.factory.ndcl.domain.CODE_SERVERERROR
import com.sundaypark.factory.ndcl.utils.InjectorUtils
import com.sundaypark.factory.ndcl.utils.SharedPreferencesManager
import com.sundaypark.factory.ndcl.viewmodel.HomeViewModel

// 위치 정보 확인
// 버전확인
// 주소 받기
//
class HomeFragment : Fragment() {
    private val TAG: String = "HomeFragment"
    lateinit var BindingView : FragmentHomeBinding

    private val Viewmodel : HomeViewModel by viewModels {
            InjectorUtils.provideAddressViewModelFactory(requireContext())
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        BindingView = DataBindingUtil.inflate<FragmentHomeBinding>(inflater , R.layout.fragment_home , container , false).apply {

            Viewmodel.Version.observe(viewLifecycleOwner, Observer {
                if (SharedPreferencesManager.getVersionDate() != it.date.time ) {
                    // 지역 업데이트 필요
                    Log.i(TAG, "주소 업데이트 필요 ")
                    BindingView.textHome.setText("주소 업데이트 중 ")
                    val Version = it
                    Viewmodel.Address.observe(viewLifecycleOwner, Observer {
                        BindingView.textHome.setText("주소 업데이트 완료")
                        SharedPreferencesManager.setVersionDate(Version)// 저장
                        gotoListPage()
                    })
                } else {
                    BindingView.textHome.setText("주소 DB 변동 없음 ")
                    gotoListPage()

                }
            })
            Viewmodel.onError.observe(viewLifecycleOwner, Observer {
                var text = ""
                when (it.code) {
                    CODE_FAILURE -> {
                        text =  "서버가 동작하는가 ? ";
                        endDialog()
                    }
                    CODE_SERVERERROR -> {
                        text = "서버에서 데이터가 안왔어 ?! "
                        endDialog()
                    }
                    CODE_DB_FAIL ->{
                        text = "디비 접속 오류 "
                        endDialog()
                    }
                }
                Log.i(TAG , text)
                BindingView.textHome.setText(text)
            })

        }



        return BindingView.root
    }
    fun endDialog(){
        val endPopup = AlertDialog.Builder(requireContext())
        endPopup.setTitle("통신 오류 안내")
        endPopup.setMessage("서버에 문제가 발생 했습니다.\n조속히 해결하겠습니다.\n다음에 다시 접속해주세요.")
        endPopup.setPositiveButton("종료"){ dialog, which ->
            dialog.dismiss()
            activity?.finish()
        }
        endPopup.create().show()
    }
    fun gotoListPage(){
        Log.i(TAG, "화면으로 이동 ")
        Navigation.findNavController(BindingView.root).navigate(R.id.navigation_dashboard)
    }
}