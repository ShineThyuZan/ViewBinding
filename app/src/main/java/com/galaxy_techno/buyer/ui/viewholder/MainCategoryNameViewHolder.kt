package com.galaxy_techno.buyer.ui.viewholder

import android.view.View
import android.widget.TextView
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.ItemDTO

import com.galaxy_techno.buyer.ui.delegate.CategoryNameDelegate

class MainCategoryNameViewHolder(itemView: View, val mDelegate: CategoryNameDelegate) :
    BaseViewHolder<ItemDTO.App>(itemView) {
    var menuTeXT: TextView? = null
    override fun setData(data: ItemDTO.App) {
        mData = data
        menuTeXT = itemView.findViewById<View>(R.id.tvCategoryName) as TextView
        menuTeXT!!.text = mData.category
    }

    override fun onClick(v: View?) {
        mDelegate.onClickedName(mData)
    }


}