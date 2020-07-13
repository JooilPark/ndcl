package com.sundaypark.factory.ndcl.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.sundaypark.factory.ndcl.databinding.ItemSpinnerCitysBinding
import com.sundaypark.factory.ndcl.databinding.ItemSpinnerDropdownBinding
import com.sundaypark.factory.ndcl.databinding.RecyclerCourseItemBinding
import com.sundaypark.factory.ndcl.db.entitny.EntityCitys
import com.sundaypark.factory.ndcl.retrofit.pojo.NewCourses
import kotlinx.android.synthetic.main.item_spinner_dropdown.view.*


class AdapterSpinnerCitys(context: Context, resource: Int) :
    ArrayAdapter<EntityCitys>(context, resource), AdapterView.OnItemSelectedListener {

    var mLayoutInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            val contentView =
                ItemSpinnerCitysBinding.inflate(mLayoutInflater, parent, false).apply {
                    item = getItem(position)
                }

            return contentView.root

    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {


        val contentView = ItemSpinnerDropdownBinding.inflate(mLayoutInflater, null, false).apply {
            item = getItem(position)
            root.liner.isSelected = true

        }

        Log.i("SELECT", "onItemSelected2 [" + position + "][" + SelectItem.value)
        return contentView.root


    }

    var SelectItem = MutableLiveData<Int>()

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.i("SELECT", "onNothingSelected")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        Log.i("SELECT", "onItemSelected [" + position)
        SelectItem.value = position
        view!!.isSelected = true
        notifyDataSetChanged()
    }
}

class AdapterSpinnersubCitys(context: Context, resource: Int) :
    ArrayAdapter<EntityCitys>(context, resource), AdapterView.OnItemSelectedListener {

    var mLayoutInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val ContentView: ItemSpinnerCitysBinding =
            ItemSpinnerCitysBinding.inflate(mLayoutInflater, parent, false)
        ContentView.item = getItem(position)
        return ContentView.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val ContentView: ItemSpinnerDropdownBinding =
            ItemSpinnerDropdownBinding.inflate(mLayoutInflater, null, false)
        ContentView.item = getItem(position)
        ContentView.root.isSelected = SelectItem.value == position
        return ContentView.root


    }

    var SelectItem = MutableLiveData<Int>()

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.i("SELECT", "onNothingSelected")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        Log.i("SELECT", "onItemSelected [" + position)
        SelectItem.value = position
    }
}

class adapterCoursesList : RecyclerView.Adapter<adapterCoursesList.ViewHolder>() {
    var mCourses: ArrayList<NewCourses> = ArrayList()

    inner class ViewHolder(private val courseItem: RecyclerCourseItemBinding) :
        RecyclerView.ViewHolder(courseItem.root) {
        fun onBind(mcourse: NewCourses) {
            courseItem.item = mcourse
        }
    }

    fun addClearAll(c: List<NewCourses>) {
        mCourses.clear()
        mCourses.addAll(c)
    }

    fun ClearNoti() {
        mCourses.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = RecyclerCourseItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(itemView)
    }

    override fun getItemCount() = mCourses.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(mCourses[position])
    }
}


