package com.galaxy_techno.buyer.ui.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.CountryVos
import com.galaxy_techno.buyer.model.dto.ProductData
import com.galaxy_techno.buyer.ui.delegate.SelectedProductDelegate
import com.galaxy_techno.buyer.ui.viewholder.BaseViewHolder
import com.galaxy_techno.buyer.ui.viewholder.SelectedProductViewHolder

class SelectedProductAdapter (context: Context, val mDelegate: SelectedProductDelegate) : BaseRecyclerAdapter
<SelectedProductViewHolder, CountryVos> (context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CountryVos> {
    val view = mLayoutInflator.inflate(R.layout.selected_product_view_pod, parent, false)
        return SelectedProductViewHolder(view, mDelegate)
    }
}