package com.galaxy_techno.buyer.ui.viewholder

import android.view.View
import android.widget.TextView
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.Apps
import com.galaxy_techno.buyer.ui.delegate.DiscountProductDelegate

class ProductViewHolder(itemView: View, val delegate: DiscountProductDelegate) :
    BaseViewHolder<Apps>(itemView) {
    var menuTeXT: TextView? = null


    override fun setData(data: Apps) {
        mData = data
        menuTeXT = itemView.findViewById<View>(R.id.tvCartProductName) as TextView
        menuTeXT!!.text = mData.name
    }

    override fun onClick(v: View?) {
        delegate.onProductOnClick(mData)
    }

}