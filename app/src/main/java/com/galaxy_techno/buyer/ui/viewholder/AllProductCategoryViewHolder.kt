package com.galaxy_techno.buyer.ui.viewholder

import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.CountryVos
import com.galaxy_techno.buyer.ui.delegate.CountrySelectedDelegate
import com.google.android.material.imageview.ShapeableImageView

class AllProductCategoryViewHolder( itemView: View,
private val delegate: CountrySelectedDelegate
) :
BaseViewHolder<CountryVos>(itemView) {
    var productTextView: TextView? = null
    var productImageView: ShapeableImageView? = null

    override fun setData(data: CountryVos) {
        mData = data
        productTextView = itemView.findViewById<View>(R.id.tvAllCategoryProduct) as TextView
        productTextView!!.text = mData.name

        productImageView = itemView.findViewById<View>(R.id.ivAllCategoryProduct) as ShapeableImageView
        Glide.with(itemView.context)
            .load(mData.photo)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(productImageView!!)
    }

    override fun onClick(v: View?) {
        delegate.onClickSelectedCounty(mData)
    }
}