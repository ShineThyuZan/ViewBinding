package com.galaxy_techno.buyer.ui.viewholder


import android.view.View
import android.widget.TextView
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.State
import com.galaxy_techno.buyer.ui.delegate.StateDelegate


class StateViewHolder(
    itemView: View,
    private val delegate: StateDelegate
) :
    BaseViewHolder<State>(itemView) {
    var menuTeXT: TextView? = null

    override fun setData(data: State) {
        mData = data
        menuTeXT = itemView.findViewById<View>(R.id.tv) as TextView
        menuTeXT!!.text = mData.state
//        itemView.title.text = mData.stateId
    }

    override fun onClick(v: View?) {
        delegate.onClick(mData)
    }

}