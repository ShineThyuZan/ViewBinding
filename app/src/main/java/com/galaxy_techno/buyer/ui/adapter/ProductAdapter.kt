package com.galaxy_techno.buyer.ui.adapter

import android.content.Context
import android.view.ViewGroup
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.Apps
import com.galaxy_techno.buyer.ui.delegate.DiscountProductDelegate
import com.galaxy_techno.buyer.ui.viewholder.BaseViewHolder
import com.galaxy_techno.buyer.ui.viewholder.ProductViewHolder

class ProductAdapter (
    context: Context,
    val mDelegate: DiscountProductDelegate
) : BaseRecyclerAdapter<ProductViewHolder, Apps>(context) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Apps> {
        val view = mLayoutInflator.inflate(R.layout.single_product_viewpod, parent, false)
        return ProductViewHolder(view, mDelegate)
    }
}