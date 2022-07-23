package com.galaxy_techno.buyer.presentation.top_lvl_dest.cart

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.CountryVos
import com.galaxy_techno.buyer.presentation.extension.displayToast
import com.galaxy_techno.buyer.ui.viewholder.BaseViewHolder
import com.google.android.material.imageview.ShapeableImageView

class CardProductViewHolder(itemView: View, private val delegate: CartProductClickDelegate) :
    BaseViewHolder<CountryVos>(itemView) {
    var productNameTextView: TextView? = null
    var priceTextView: TextView? = null
    var productImageView: ShapeableImageView? = null
    var moreImageView: ImageView? = null
    var addButton: ImageView? = null
    var decreaseButton: ImageView? = null
    var countNoTextView: TextView? = null
    var count = 0

    override fun setData(data: CountryVos) {
        mData = data
        productNameTextView = itemView.findViewById<View>(R.id.tvCartProductName) as TextView
        productNameTextView!!.text = mData.name

        priceTextView = itemView.findViewById<View>(R.id.tvCartProductPrice) as TextView
        priceTextView!!.text = mData.name

        moreImageView = itemView.findViewById<View>(R.id.ivMore) as ImageView

        productImageView = itemView.findViewById<View>(R.id.ivProduct) as ShapeableImageView
        Glide.with(itemView.context)
            .load(mData.photo)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(productImageView!!)


        /** add , decrease and more click event */
        addButton = itemView.findViewById<View>(R.id.btnCountAdd) as ImageView
        countNoTextView = itemView.findViewById<View>(R.id.tvCountShowing) as TextView

        addButton!!.setOnClickListener {
            count = countNoTextView!!.text.trim().toString().toInt()
            count++
            countNoTextView!!.text = count.toString()
            delegate.onClickAddButton(mData, count)
        }

        decreaseButton = itemView.findViewById<View>(R.id.btnDecrease) as ImageView
        decreaseButton!!.setOnClickListener {
            count = countNoTextView!!.text.trim().toString().toInt()

            if (count <= 0) {
                Toast.makeText(itemView.context, "Unavialble", Toast.LENGTH_SHORT).show()

            } else count--

            countNoTextView!!.text = count.toString()
            delegate.onClickMinusButton(mData, count)
        }

        moreImageView?.setOnClickListener {
            delegate.onClickMore(mData)
        }
    }

    /** onClick the whole product Item */
    override fun onClick(v: View?) {
        delegate.onClickCartView(mData)
    }


}