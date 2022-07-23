package com.galaxy_techno.buyer.ui.viewholder

import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.Movie
import com.galaxy_techno.buyer.ui.delegate.MovieDelegate
import com.google.android.material.imageview.ShapeableImageView

class MovieViewHolder (itemView: View, val delegate: MovieDelegate) :
    BaseViewHolder<Movie>(itemView) {
    var imageView : ShapeableImageView? = null
    var productName : TextView ? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setData(data: Movie) {
        imageView = itemView.findViewById<View>(R.id.ivProduct) as ShapeableImageView
        productName = itemView.findViewById<View>(R.id.tvCartProductName) as TextView
        mData = data
        Glide.with(itemView.context)
            .load("https://image.tmdb.org/t/p/original/" + mData.imageUrl)
            .into(imageView!!)
       productName!!.text = mData.title

    }

    override fun onClick(v: View?) {
        delegate.onClickedMovieDelegate(mData)
    }

}