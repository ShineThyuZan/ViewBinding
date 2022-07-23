package com.galaxy_techno.buyer.ui.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.CountryVos
import com.galaxy_techno.buyer.model.dto.ProductData
import com.galaxy_techno.buyer.ui.delegate.SelectedProductDelegate
import com.google.android.material.imageview.ShapeableImageView

class SelectedProductViewHolder(itemView: View, val delegate: SelectedProductDelegate) :
    BaseViewHolder<CountryVos>(itemView) {
    var productName: TextView? = null
    var selectedImageView: ShapeableImageView? = null

    override fun setData(data: CountryVos) {
        mData = data
        productName = itemView.findViewById(R.id.tv_product_name) as TextView
        selectedImageView = itemView.findViewById<View>(R.id.iv_selected_product) as ShapeableImageView
        productName!!.text = mData.name
        Glide.with(itemView.context)
            .load(mData.photo)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(selectedImageView!!)
    }

    override fun onClick(v: View?) {
        delegate.onClickVariants()
    }
}