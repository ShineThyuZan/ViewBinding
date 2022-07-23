package com.galaxy_techno.buyer.presentation.top_lvl_dest.cart

import android.content.Context
import android.view.ViewGroup
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.CountryVos
import com.galaxy_techno.buyer.ui.adapter.BaseRecyclerAdapter
import com.galaxy_techno.buyer.ui.viewholder.BaseViewHolder

class TopCartAdapter(context: Context, private val delegate: CartProductClickDelegate) :
    BaseRecyclerAdapter<CardProductViewHolder, CountryVos>(context) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<CountryVos> {
        val view = mLayoutInflator.inflate(R.layout.cart_product_view_pod, parent, false)
        return CardProductViewHolder(view, delegate)
    }
}