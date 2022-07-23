package com.galaxy_techno.buyer.ui.viewholder

import android.view.View
import android.widget.TextView
import androidx.core.app.NotificationCompat.getColor
import androidx.core.content.ContextCompat
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.Apps
import com.galaxy_techno.buyer.ui.adapter.FilterCategoryAdapter.Companion.checkedPosition
import com.galaxy_techno.buyer.ui.delegate.DiscountProductDelegate

class FilterCategoryViewHolder(itemView: View, val delegate: DiscountProductDelegate) :
    BaseViewHolder<Apps>(itemView) {
    var menuTeXT: TextView? = null

    override fun setData(data: Apps) {
        mData = data
        menuTeXT = itemView.findViewById<View>(R.id.tv) as TextView
        menuTeXT!!.text = mData.name
        if (checkedPosition == -1) {
            itemView.setBackgroundResource(R.drawable.bg_cornor)
            menuTeXT!!.setTextColor(ContextCompat.getColor(itemView.context, R.color.buyer_yellow_600))

        } else {
            if (checkedPosition == adapterPosition) {
                itemView.setBackgroundResource(R.drawable.color_gradient_yellow)
                menuTeXT!!.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))

            } else {
                itemView.setBackgroundResource(R.drawable.bg_cornor)
                menuTeXT!!.setTextColor(ContextCompat.getColor(itemView.context, R.color.buyer_yellow_600))

            }
        }

    }

    override fun onClick(v: View?) {
      //  delegate.onProductOnClick(mData)
    }

}