package com.galaxy_techno.buyer.presentation.top_lvl_dest.favourite

import android.content.Context
import android.view.ViewGroup
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.CountryVos
import com.galaxy_techno.buyer.ui.adapter.BaseRecyclerAdapter
import com.galaxy_techno.buyer.ui.viewholder.BaseViewHolder

class TopFavProductAdapter(context: Context, private val delegate: FavItemClickedDelegate) :
    BaseRecyclerAdapter<TopFavProductViewHolder, CountryVos>(context) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<CountryVos> {
        val view = mLayoutInflator.inflate(R.layout.fav_product_view_pod, parent, false)
        return TopFavProductViewHolder(view, delegate)
    }
}