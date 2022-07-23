package com.galaxy_techno.buyer.presentation.top_lvl_dest.favourite

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.CountryVos
import com.galaxy_techno.buyer.ui.viewholder.BaseViewHolder
import com.google.android.material.imageview.ShapeableImageView

class TopFavProductViewHolder( itemView: View,
                               private val delegate: FavItemClickedDelegate) :
                    BaseViewHolder<CountryVos>(itemView) {
    var productNameTextView: TextView? = null
    var priceTextView: TextView? = null
    var productImageView: ShapeableImageView? = null
    var closeImageView : ImageView?= null
    var cartImageView : ImageView?= null

    override fun setData(data: CountryVos) {
        mData = data
        productNameTextView = itemView.findViewById<View>(R.id.tvCartProductName) as TextView
        productNameTextView!!.text = mData.name
        priceTextView = itemView.findViewById<View>(R.id.tvCartProductPrice) as TextView
        priceTextView!!.text = mData.name

        productImageView = itemView.findViewById<View>(R.id.ivProduct) as ShapeableImageView
        Glide.with(itemView.context)
            .load(mData.photo)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(productImageView!!)

        /** close or Delete  n*/
        closeImageView = itemView.findViewById<View>(R.id.ivClose) as ImageView
        closeImageView!!.setOnClickListener {
            delegate.onDeleteClicked(mData)
        }

        cartImageView = itemView.findViewById<View>(R.id.ivCart) as ImageView
        cartImageView!!.setOnClickListener {
            delegate.onCartViewClicked(mData)
        }
    }

    override fun onClick(v: View?) {
        delegate.onClick(mData)
    }
}