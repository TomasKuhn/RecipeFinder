package com.tkuhn.recipefinder.utils.databinding

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tkuhn.recipefinder.utils.Identifiable

open class BindingRecyclerAdapter<DATA, BINDING : ViewDataBinding>(
    @LayoutRes private val itemLayoutRes: Int,
    private val binder: DataBinder<DATA, BINDING>,
    private val lifecycleOwner: LifecycleOwner? = null,
    private val onItemClick: ((DATA) -> Unit)? = null
) : RecyclerView.Adapter<DataViewHolder<BINDING>>() {

    var data: List<DATA> = listOf()

    private val bindingHolders = ArrayList<DataViewHolder<BINDING>>()

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: DataViewHolder<BINDING>, position: Int) {
        val item = data[position]
        binder.bind(item, holder.binding)
        if (onItemClick != null) {
            holder.itemView.setOnClickListener {
                onItemClick.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<BINDING> {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding: BINDING = DataBindingUtil.inflate(inflater, itemLayoutRes, parent, false)
        val dataViewHolder = DataViewHolder(itemBinding)
        if (lifecycleOwner != null) {
            itemBinding.lifecycleOwner = lifecycleOwner
            if (!bindingHolders.contains(dataViewHolder)) {
                bindingHolders.add(dataViewHolder)
            }
        }
        return dataViewHolder
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        if (lifecycleOwner != null) {
            bindingHolders.forEach {
                it.binding.lifecycleOwner = lifecycleOwner
            }
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)

        if (lifecycleOwner != null) {
            bindingHolders.forEach {
                it.binding.lifecycleOwner = null
            }
        }
    }

    fun setItems(data: List<DATA>) {
        this.data = data
        notifyDataSetChanged()
    }
}

open class IdBindingRecyclerAdapter<DATA : Identifiable, BINDING : ViewDataBinding>(
    @LayoutRes private val itemLayoutRes: Int,
    private var binder: DataBinder<DATA, BINDING>,
    private val lifecycleOwner: LifecycleOwner? = null,
    private val onItemClick: ((DATA) -> Unit)? = null
) : ListAdapter<DATA, DataViewHolder<BINDING>>(object : DiffUtil.ItemCallback<DATA>() {

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DATA, newItem: DATA): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: DATA, newItem: DATA): Boolean {
        return oldItem.id == newItem.id
    }
}) {

    private val bindingHolders = HashSet<DataViewHolder<BINDING>>()

    override fun onBindViewHolder(holder: DataViewHolder<BINDING>, position: Int) {
        val item = currentList[position]
        binder.bind(item, holder.binding)
        if (onItemClick != null) {
            holder.itemView.setOnClickListener {
                onItemClick.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<BINDING> {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding: BINDING = DataBindingUtil.inflate(inflater, itemLayoutRes, parent, false)
        val dataViewHolder = DataViewHolder(itemBinding)
        if (lifecycleOwner != null) {
            itemBinding.lifecycleOwner = lifecycleOwner
            bindingHolders.add(dataViewHolder)
        }
        return dataViewHolder
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        if (lifecycleOwner != null) {
            bindingHolders.forEach {
                it.binding.lifecycleOwner = lifecycleOwner
            }
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        if (lifecycleOwner != null) {
            bindingHolders.forEach {
                it.binding.lifecycleOwner = null
            }
        }
    }

    fun setItems(data: List<DATA>) {
        submitList(data)
    }
}

abstract class DataBinder<in DATA, BINDING : ViewDataBinding> {

    abstract fun bind(data: DATA, binding: BINDING)
}

class DataViewHolder<BINDING : ViewDataBinding>(val binding: BINDING) :
    RecyclerView.ViewHolder(binding.root)