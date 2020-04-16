package com.sundaypark.factory.ndcl.retrofit.pojo

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

class adapterNewCitys (private val mContext : Context,private val viewLifeCycleOwener : LifecycleOwner) : RecyclerView.Adapter<adapterNewCitys.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class ViewHolder : RecyclerView.ViewHolder{
        constructor(itemView: View) : super(itemView)
    }

}