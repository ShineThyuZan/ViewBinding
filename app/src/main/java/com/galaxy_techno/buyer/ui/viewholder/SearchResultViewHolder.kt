package com.galaxy_techno.buyer.ui.viewholder

import android.view.View
import android.widget.TextView
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.SearchProductResponse
import com.galaxy_techno.buyer.ui.delegate.SearchResultDelegate

class SearchResultViewHolder(itemView: View, private val mDelegate: SearchResultDelegate) :
    BaseViewHolder<SearchProductResponse.SearchResult>(itemView) {
    var menuTeXT: TextView? = null
    override fun setData(data: SearchProductResponse.SearchResult) {
        mData = data
        menuTeXT = itemView.findViewById<View>(R.id.tv_result) as TextView
        menuTeXT!!.text = mData.userName
    }

    override fun onClick(v: View?) {
        mDelegate.onClickSearchProductResult(mData)
    }


}