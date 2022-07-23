package com.galaxy_techno.buyer.ui.adapter

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.galaxy_techno.buyer.ui.viewholder.BaseViewHolder
import kotlin.collections.ArrayList

abstract class BaseRecyclerAdapter<T, W : Any>(context: Context) :
    RecyclerView.Adapter<BaseViewHolder<W>>() {

    protected  var mData: MutableList<W>? = null
    protected var mLayoutInflator: LayoutInflater

    val items: List<W>
        get() {
            val data = mData
            return data ?: ArrayList()
        }

    init {
        mData = ArrayList()
        mLayoutInflator = LayoutInflater.from(context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<W>, position: Int) {
        holder.setData(mData!![position])
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    open fun setNewData(newData: MutableList<W>) {
        mData = ArrayList()
        mData = newData
        notifyDataSetChanged()
    }

    open fun setNewDataList(newData: MutableList<W>) {
        mData = ArrayList()
        mData = newData
        notifyDataSetChanged()
    }

    open fun appendNewData(newData: List<W>) {
        mData!!.addAll(newData)
        Handler().postDelayed({
            notifyDataSetChanged()
        }, 300)
    }

    fun getItemAt(position: Int): W? {
        return if (position < mData!!.size - 1) mData!![position] else null
    }

    fun removeData(data: W) {
        mData!!.remove(data)
        notifyDataSetChanged()
    }

    open fun addNewData(data: W) {
        mData!!.add(data)
        notifyDataSetChanged()
    }

    fun clearData() {
        mData = ArrayList()
        notifyDataSetChanged()
    }
}