package com.galaxy_techno.buyer.ui.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.CountryVos
import com.galaxy_techno.buyer.model.dto.ProductData
import com.galaxy_techno.buyer.ui.delegate.ProductVariantDelegate
import com.galaxy_techno.buyer.ui.delegate.SelectedProductDelegate
import com.google.android.material.imageview.ShapeableImageView

class ProductVariantViewHolder(itemView: View, val delegate: SelectedProductDelegate) :
    BaseViewHolder<CountryVos>(itemView) {
    var imageView: ShapeableImageView? = null

    override fun setData(data: CountryVos) {
        mData = data
        imageView = itemView.findViewById<View>(R.id.iv_variant_img) as ShapeableImageView
        Glide.with(itemView.context)
            .load(mData.photo)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(imageView!!)
    }

    override fun onClick(v: View?) {
        delegate.onClickVariants()
    }
}