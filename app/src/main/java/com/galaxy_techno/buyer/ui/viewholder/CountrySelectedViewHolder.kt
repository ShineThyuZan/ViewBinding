package com.galaxy_techno.buyer.ui.viewholder

import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.CountryVos
import com.galaxy_techno.buyer.ui.delegate.CountrySelectedDelegate
import com.google.android.material.imageview.ShapeableImageView

class CountrySelectedViewHolder(
    itemView: View,
    private val delegate: CountrySelectedDelegate
) :
    BaseViewHolder<CountryVos>(itemView) {
    var countryNameTextView: TextView? = null
    var countryCodeTextView: TextView? = null
    var countryImageView: ShapeableImageView? = null

    override fun setData(data: CountryVos) {
        mData = data
        countryNameTextView = itemView.findViewById<View>(R.id.tvCountryName) as TextView
        countryNameTextView!!.text = mData.name

        countryCodeTextView = itemView.findViewById<View>(R.id.tvCountryCode) as TextView
        countryCodeTextView!!.text = mData.id

        countryImageView = itemView.findViewById<View>(R.id.ivCountry) as ShapeableImageView
        Glide.with(itemView.context)
            .load(mData.photo)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(countryImageView!!)
    }

    override fun onClick(v: View?) {
        delegate.onClickSelectedCounty(mData)
    }
}