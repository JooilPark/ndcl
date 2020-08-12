package com.sundaypark.factory.ndcl.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sundaypark.factory.ndcl.R
import com.sundaypark.factory.ndcl.databinding.ItemSpinnerCitysBinding
import com.sundaypark.factory.ndcl.databinding.ItemSpinnerDropdownBinding
import com.sundaypark.factory.ndcl.databinding.RecyclerCourseItemBinding
import com.sundaypark.factory.ndcl.db.entitny.EntityCitys
import com.sundaypark.factory.ndcl.retrofit.pojo.NewCourses
import kotlinx.android.synthetic.main.item_spinner_dropdown.view.*


class AdapterSpinnerCitys(
    context: Context,
    resource: Int,
    private val OnItemClickListiner: ((Int) -> Unit?)
) :
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


        return contentView.root


    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.i("SELECT", "onNothingSelected")
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.i("SELECT", "onItemSelected [" + position)
        OnItemClickListiner.invoke(position)
    }
}

class AdapterSpinnersubCitys(
    context: Context,
    resource: Int,
    private val OnItemClickListiner: ((Int) -> Unit?)
) :
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
        return ContentView.root


    }


    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.i("SELECT", "onNothingSelected")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        OnItemClickListiner.invoke(position)
        Log.i("SELECT", "onItemSelected [" + position)

    }
}

class adapterCoursesList(
    config: DiffUtil.ItemCallback<NewCourses>,
    private val itemClickCallback: ((NewCourses) -> Unit)?
) :
    DataBindingListAdapter<NewCourses, RecyclerCourseItemBinding>(config) {
    override fun onCreateBinding(VG: ViewGroup): RecyclerCourseItemBinding {
        val binding = DataBindingUtil.inflate<RecyclerCourseItemBinding>(
            LayoutInflater.from(VG.context),
            R.layout.recycler_course_item,
            VG,
            false
        )
        binding.root.setOnClickListener {
            binding.item?.let {
                itemClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: RecyclerCourseItemBinding, item: NewCourses) {
        binding.item = item
    }

}

abstract class DataBindingListAdapter<T, VDB : ViewDataBinding>(config: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, DataBindingListAdapter.DataBindingViewholde<VDB>>(config) {

    protected abstract fun onCreateBinding(VG: ViewGroup): VDB

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewholde<VDB> {
        val binding = onCreateBinding(parent)
        return DataBindingViewholde(binding)
    }

    protected abstract fun bind(binding: VDB, item: T)

    override fun onBindViewHolder(holder: DataBindingViewholde<VDB>, position: Int) {
        bind(holder.binding, getItem(position))
        holder.binding.executePendingBindings()
    }

    // ViewHolder 패턴
    class DataBindingViewholde<out VDB : ViewDataBinding> constructor(val binding: VDB) :
        RecyclerView.ViewHolder(binding.root)
}


