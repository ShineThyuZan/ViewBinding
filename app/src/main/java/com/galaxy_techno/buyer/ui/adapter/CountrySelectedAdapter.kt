package com.galaxy_techno.buyer.ui.adapter

import android.content.Context
import android.view.ViewGroup
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.CountryVos
import com.galaxy_techno.buyer.ui.delegate.CountrySelectedDelegate
import com.galaxy_techno.buyer.ui.viewholder.BaseViewHolder
import com.galaxy_techno.buyer.ui.viewholder.CountrySelectedViewHolder
import com.galaxy_techno.buyer.ui.viewholder.StateViewHolder

class CountrySelectedAdapter(context: Context, private val delegate: CountrySelectedDelegate) :
    BaseRecyclerAdapter<CountrySelectedViewHolder, CountryVos>(context) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<CountryVos> {
        val view = mLayoutInflator.inflate(R.layout.country_items_view_pod, parent, false)
        return CountrySelectedViewHolder(view, delegate)
    }
}