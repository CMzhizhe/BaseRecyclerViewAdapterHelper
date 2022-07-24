package com.chad.library.adapter.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class SingleItemAdapter<T, VH : RecyclerView.ViewHolder>(private var mItem: T? = null) : RecyclerView.Adapter<VH>() {

    private var mOnItemClickListener: OnItemClickListener<T>? = null
    private var mOnItemLongClickListener: OnItemLongClickListener<T>? = null

    protected abstract fun onCreateViewHolder(
        context: Context, parent: ViewGroup, viewType: Int
    ): VH

    protected  abstract fun onBindViewHolder(holder: VH, item: T?)

    open fun onBindViewHolder(holder: VH, item: T?, payloads: MutableList<Any>) {
        onBindViewHolder(holder, item)
    }

    final override fun getItemCount(): Int {
        return 1
    }

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
       return onCreateViewHolder(parent.context, parent, viewType).apply {
           mOnItemClickListener?.let {
               itemView.setOnClickListener { v ->
                   val position = bindingAdapterPosition
                   if (position == RecyclerView.NO_POSITION) {
                       return@setOnClickListener
                   }
                   it.onItemClick(this@SingleItemAdapter, v, position)
               }
           }
           mOnItemLongClickListener?.let {
               itemView.setOnLongClickListener { v ->
                   val position = bindingAdapterPosition
                   if (position == RecyclerView.NO_POSITION) {
                       return@setOnLongClickListener false
                   }
                   it.onItemLongClick(this@SingleItemAdapter, v, position)
               }
           }
       }
    }

    final override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(holder, mItem)
    }

    final override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, mItem)
            return
        }
        onBindViewHolder(holder, mItem, payloads)
    }

    fun setItem(t: T?, payload: Any?) {
        mItem = t
        notifyItemChanged(0, payload)
    }

    var item: T?
        get() = mItem
        set(value) {
            mItem = value
            notifyItemChanged(0)
        }

    fun setOnItemClickListener(listener: OnItemClickListener<T>?) {
        this.mOnItemClickListener = listener
    }

    fun getOnItemClickListener(): OnItemClickListener<T>? = mOnItemClickListener

    fun setOnItemLongClickListener(listener: OnItemLongClickListener<T>?) {
        this.mOnItemLongClickListener = listener
    }

    fun getOnItemLongClickListener(): OnItemLongClickListener<T>? = mOnItemLongClickListener


    fun interface OnItemClickListener<T> {
        fun onItemClick(adapter: SingleItemAdapter<T, *>, view: View, position: Int)
    }

    fun interface OnItemLongClickListener<T> {
        fun onItemLongClick(adapter: SingleItemAdapter<T, *>, view: View, position: Int): Boolean
    }
}