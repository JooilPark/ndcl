package com.sundaypark.factory.ndcl.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sundaypark.factory.ndcl.R
import com.sundaypark.factory.ndcl.databinding.FragmentFragDetailInfoBinding
import com.sundaypark.factory.ndcl.retrofit.pojo.NewCourses

class FragDetailInfo : Fragment() {
    private val TAG = "[FragDetailInfo]"

    companion object {
        lateinit var detailInfo: NewCourses
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return DataBindingUtil.inflate<FragmentFragDetailInfoBinding>(
            inflater,
            R.layout.fragment_frag_detail_info,
            container,
            false
        ).apply {
            Log.i(TAG, "" + detailInfo)
            info = detailInfo
            buttonback.setOnClickListener {
                findNavController().navigateUp()
            }
            Phone.setOnClickListener {
                detailInfo.opernumber?.let {
                    startActivity(
                        Intent(
                            Intent.ACTION_DIAL,
                            Uri.parse("tel:${detailInfo.opernumber}")
                        )
                    )
                }

            }
            HomePage.setOnClickListener {
                detailInfo.homepage?.let {
                    if (it.contains("http://") || (it.contains("https://"))) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(detailInfo.homepage)))
                    } else {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("http://${detailInfo.homepage}")
                            )
                        )
                    }

                }
            }
            addr.setOnClickListener {
                detailInfo.roaaddress?.let {
                    var addr = it.replace(" " , "+")
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=${addr}")))

                }
            }
            pleace.setOnClickListener {
                detailInfo.trainingplace?.let{
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=${it}")))
                }
            }
        }.root
    }
}