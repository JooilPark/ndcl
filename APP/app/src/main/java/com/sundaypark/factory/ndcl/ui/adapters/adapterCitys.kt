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
import com.sundaypark.factory.ndcl.databinding.ItemSpinnerCourseBinding
import com.sundaypark.factory.ndcl.db.entitny.EntityCitys
import com.sundaypark.factory.ndcl.retrofit.pojo.NewCourses


class AdapterSpinnerCitys(context: Context, resource: Int) :
    ArrayAdapter<EntityCitys>(context, resource), AdapterView.OnItemSelectedListener {

    var mLayoutInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val ContentView: ItemSpinnerCitysBinding =
            ItemSpinnerCitysBinding.inflate(mLayoutInflater, null, false)
        ContentView.item = getItem(position)
        return ContentView.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val ContentView: ItemSpinnerCitysBinding =
            ItemSpinnerCitysBinding.inflate(mLayoutInflater, null, false)
        ContentView.item = getItem(position)
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

class AdapterSpinnerCourses(context: Context, LayoutId: Int) :
    ArrayAdapter<NewCourses>(context, LayoutId), AdapterView.OnItemSelectedListener {

    private var mLayoutInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val ContentView: ItemSpinnerCourseBinding = ItemSpinnerCourseBinding.inflate(mLayoutInflater, null, false)

        ContentView.item = getItem(position)
        return ContentView.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val ContentView: ItemSpinnerCourseBinding =
            ItemSpinnerCourseBinding.inflate(mLayoutInflater, null, false)
        ContentView.item = getItem(position)
        return ContentView.root
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.i("SELECT", "onNothingSelected")
    }

    var SelectItem = MutableLiveData<Int>()
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        SelectItem.value = position
        Log.i("SELECT", "onItemSelected [" + position)
    }

}



