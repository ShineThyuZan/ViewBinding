package com.galaxy_techno.buyer.ui.adapter

import android.content.Context
import android.view.ViewGroup
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.Apps
import com.galaxy_techno.buyer.model.dto.Info
import com.galaxy_techno.buyer.model.dto.ItemDTO
import com.galaxy_techno.buyer.model.dto.State
import com.galaxy_techno.buyer.ui.delegate.DetailProductDelegate
import com.galaxy_techno.buyer.ui.delegate.DiscountProductDelegate
import com.galaxy_techno.buyer.ui.delegate.delegate
import com.galaxy_techno.buyer.ui.viewholder.BaseViewHolder
import com.galaxy_techno.buyer.ui.viewholder.ProductDetailViewHolder

class DetailProductAdapter(context: Context, val delegate: DetailProductDelegate) :
    BaseRecyclerAdapter<ProductDetailViewHolder, ItemDTO.App>(context) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ItemDTO.App> {
        val view = mLayoutInflator.inflate(R.layout.fragment_item_detail, parent, false)
        return ProductDetailViewHolder(view, delegate)
    }
}