package com.galaxy_techno.buyer.ui.viewholder

import android.view.View
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.Apps
import com.galaxy_techno.buyer.model.dto.DetailItemDTO
import com.galaxy_techno.buyer.model.dto.Info
import com.galaxy_techno.buyer.model.dto.ItemDTO
import com.galaxy_techno.buyer.ui.delegate.DetailProductDelegate
import com.galaxy_techno.buyer.ui.delegate.delegate
import timber.log.Timber

class ProductDetailViewHolder(
    itemView: View,
    val delegate: DetailProductDelegate
) :
    BaseViewHolder<ItemDTO.App>(itemView) {
    var menuTeXT: TextView? = null
    var menuRating: RatingBar? = null
    var checkoutBtn: Button? = null


    override fun setData(data: ItemDTO.App) {
        mData = data
        menuRating = itemView.findViewById<View>(R.id.rb_item_detail) as RatingBar
        menuTeXT = itemView.findViewById<View>(R.id.tv_item_name) as TextView
        checkoutBtn = itemView.findViewById(R.id.btn_checkout) as Button
        menuTeXT!!.text = mData.category
        menuRating!!.stepSize = 0.01f
        menuRating!!.rating = 3.25f

        checkoutBtn?.setOnClickListener {
            delegate.onClickCheckout(mData)
        }

    }


    override fun onClick(v: View?) {
        delegate.onClickProduct(mData)
    }

}