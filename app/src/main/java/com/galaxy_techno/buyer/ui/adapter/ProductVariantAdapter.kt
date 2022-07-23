package com.galaxy_techno.buyer.ui.adapter

import android.content.Context
import android.view.ViewGroup
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.CountryVos
import com.galaxy_techno.buyer.ui.delegate.SelectedProductDelegate
import com.galaxy_techno.buyer.ui.viewholder.BaseViewHolder
import com.galaxy_techno.buyer.ui.viewholder.ProductVariantViewHolder

class ProductVariantAdapter(context: Context, val mDelegate: SelectedProductDelegate) :
    BaseRecyclerAdapter
    <ProductVariantViewHolder, CountryVos>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CountryVos> {
        val view = mLayoutInflator.inflate(R.layout.item_product_variants, parent, false)
        return ProductVariantViewHolder(view, mDelegate)
    }
}